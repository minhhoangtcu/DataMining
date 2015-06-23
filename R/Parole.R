# Load dataset and convert into factors
parole = read.csv("dataset/parole.csv")
parole$crime = as.factor(parole$crime)
parole$state = as.factor(parole$state)

# Split the data set into training set and testing set
set.seed(144)
library(caTools)
split = sample.split(parole$violator, SplitRatio = 0.7)
train = subset(parole, split == TRUE)
test = subset(parole, split == FALSE)

# Inspect dataset
str(parole)
summary(parole)
table(parole$violator)
summary(as.factor(parole$crime))
summary(parole$crime)

# Construct Logistic Regression Model
violatorModel = glm(violator ~ ., data=train, family="binomial")
violatorModel 
summary(violatorModel)

# Validate a specific person
linearReg = -4.2411574 + 0.3869904 + 0.8867192 + -0.0001756*50 + -0.1238867*3 + 0.0802954*12 + 0.6837143*1
odds = exp(linearReg)
odds
probability = 1/(1+exp(-linearReg))
probability 

# Predict
predict = predict(violatorModel, newdata=test, type="response")
summary(predict)
table(test$violator, predict>0.5)
sensitivity = 12/(12+11)
sensitivity 
specificity = 167/(167+12)
specificity 
accuracy = (167+12)/(167+12+11+12)
accuracy 
accuracyBase = (167+12)/(167+12+11+12)
accuracyBase 

# Finding AUC
library(ROCR)
POCRpred = prediction(predict, test$violator)
as.numeric(performance(POCRpred , "auc")@y.values)
