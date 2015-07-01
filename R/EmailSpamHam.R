# Load dataset
emails = read.csv("dataset/emails.csv", stringsAsFactor=FALSE)

# Inspect dataset
str(emails)
table(emails$spam)
max(nchar(emails$text))
which.min(nchar(emails$text))

# Pre-Process
library(tm)
library(SnowballC)
corpus = Corpus(VectorSource(emails$text))
corpus = tm_map(corpus, tolower)
corpus = tm_map(corpus, PlainTextDocument)
corpus = tm_map(corpus, removePunctuation)
corpus = tm_map(corpus, removeWords, c(stopwords("english")))
corpus = tm_map(corpus, stemDocument)
dtm = DocumentTermMatrix(corpus)
spdtm = removeSparseTerms(dtm, 0.95)
emailsSparse = as.data.frame(as.matrix(spdtm))
colnames(emailsSparse) = make.names(colnames(emailsSparse))
str(emailsSparse)
sort(colSums(emailsSparse))

# Inspect words freq between ham and spam
emailsSparse$spam = emails$spam
emailsSpareHam = subset(emailsSparse, spam==0)
emailsSpareSpam = subset(emailsSparse, spam==1)
sort(colSums(emailsSpareHam))
sort(colSums(emailsSpareSpam))
table(colSums(emailsSpareSpam) > 1000)

# Construct Model
emailsSparse$spam = as.factor(emailsSparse$spam)
library(caTools)
library(rpart)
library(rpart.plot)
library(randomForest)
library(ROCR)
set.seed(123)
sql = sample.split(emailsSparse$spam, SplitRatio=0.7)
train = subset(emailsSparse, sql==TRUE)
test = subset(emailsSparse, sql==FALSE)

spamLog = glm(spam ~ ., data=train, family="binomial")
spamCART = rpart(spam ~ ., data=train, method="class")
set.seed(123)
spamRF = randomForest(spam ~ ., data=train)

# Train Log
summary(spamLog)
predictLog = predict(spamLog)
table(predictLog < 0.00001)
table(predictLog > 0.00001 & predictLog < 0.99999)
table(train$spam, predictLog > 0.5)
ROCRpredLog = prediction(predictLog, train$spam)
as.numeric(performance(ROCRpredLog, "auc")@y.values)

# Train CART
prp(spamCART)
predictCART = predict(spamCART)
table(train$spam, predictCART[,2] > 0.5)
ROCRpredCART = prediction(predictCART[,2], train$spam)
as.numeric(performance(ROCRpredCART, "auc")@y.values)

# Train RF
predictRF = predict(spamRF)
table(train$spam, predictRF > 0.5)
ROCRpredRF = prediction(predictRF, train$spam)
as.numeric(performance(ROCRpredRF , "auc")@y.values)

# Test Log
predictLog2 = predict(spamLog, newdata=test)
table(test$spam, predictLog2 > 0.5)
ROCRpredLog2 = prediction(predictLog2, test$spam)
as.numeric(performance(ROCRpredLog2, "auc")@y.values)

# Test CART
predictCART2 = predict(spamCART, newdata=test)
table(test$spam, predictCART2[,2] > 0.5)
ROCRpredCART2 = prediction(predictCART2[,2], test$spam)
as.numeric(performance(ROCRpredCART2, "auc")@y.values)

# Test RF
predictRF2 = predict(spamRF, newdata=test)
table(test$spam, predictRF2 > 0.5)
ROCRpredRF2 = prediction(predictRF2, test$spam)
as.numeric(performance(ROCRpredRF2, "auc")@y.values)


