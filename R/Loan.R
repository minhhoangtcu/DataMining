# Load dataset
loans = read.csv("dataset/loans.csv")
#loans$naLogAnnualInc = is.na(loans$log.annual.inc)
#loans$nadays.with.cr.line= is.na(loans$days.with.cr.line)
#loans$narevol.util= is.na(loans$revol.util)
#loans$nainq.last.6mths = is.na(loans$inq.last.6mths)
#loans$nadelinq.2yrs = is.na(loans$delinq.2yrs)
#loans$napub.rec = is.na(loans$pub.rec)
#loansOfNA = subset(loans, naLogAnnualInc==TRUE | nadays.with.cr.line==TRUE | narevol.util==TRUE | nainq.last.6mths==TRUE | nadelinq.2yrs==TRUE | napub.rec == TRUE)

# Fill NA values
install.packages("mice")
library(mice)
set.seed(144)
vars.for.imputation = setdiff(names(loans), "not.fully.paid")
imputed = complete(mice(loans[vars.for.imputation]))
loans[vars.for.imputation] = imputed

# Splitting dataset
library(caTools)
set.seed(144)
split = sample.split(loans$not.fully.paid, SplitRatio = 0.70)
train = subset(loans, split==TRUE)
test = subset(loans, split==FALSE)

# Inspect dataset
str(loans)
str(test)
#str(loansOfNA)
summary(loans)
summary(test)
#summary(loansOfNA)
table(loans$not.fully.paid)
notPaid = table(loans$not.fully.paid)[2]
paid = table(loans$not.fully.paid)[1]
portionNotPaid = notPaid/(notPaid + paid)
portionNotPaid 
is.na(loans$pub.rec)

# Build Linear Reg
model = glm(not.fully.paid ~ ., data=train, family="binomial")
summary(model)
predict = predict(model, newdata=test, type="response")
test$predicted.risk = predict
table(test$not.fully.paid, predict > 0.5)
accuracy = 2403/(2403+13+457)
accuracyBase = (2400+13)/(2403+13+457)
library(ROCR)
ROCRpred = prediction(predict, test$not.fully.paid)
as.numeric(performance(ROCRpred, "auc")@y.values)

# Build Smart Baseline Model
modelBiv = glm(not.fully.paid ~ int.rate, data=train, family="binomial")
summary(modelBiv)
predictBiv = predict(modelBiv, newdata=test, type="response")
summary(predictBiv)
table(test$not.fully.paid, predictBiv > 0.5)
ROCRpredBiv = prediction(predictBiv, test$not.fully.paid)
as.numeric(performance(ROCRpredBiv , "auc")@y.values)

# Computing profit
test$profit = exp(test$int.rate*3) - 1
test$profit[test$not.fully.paid == 1] = -1
highInterest = subset(test, int.rate>0.15)
summary(highInterest)
table(highInterest$not.fully.paid)
cutoff = sort(highInterest$predicted.risk, decreasing=FALSE)[100]
selectedLoans = subset(highInterest, predicted.risk <= cutoff)
totalProfit = sum(selectedLoans$profit)