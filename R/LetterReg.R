# Load dataset
letters =  read.csv("dataset/letters_ABPR.csv")
str(letters)

# Modify dataset and split into training and testing set
letters$isB = as.factor(letters$letter == "B")
library(caTools)
set.seed(1000)
split = sample.split(letters$isB, SplitRatio = 0.5)
train = subset(letters, split==TRUE)
test = subset(letters, split==FALSE)

# Modify data and split to predict a character
letters$letter = as.factor(letters$letter)
library(caTools)
set.seed(2000)
split = sample.split(letters$letter, SplitRatio = 0.5)
train = subset(letters, split==TRUE)
test = subset(letters, split==FALSE)

# Accuracy of baseline model where we predict that all letters are not B
table(test$isB)
1175/nrow(test)

# Accuracy of baseline model where we predict the most outcome
table(test$letter)
401/nrow(test)

# Create CART Model to predict isB
library(rpart)
library(rpart.plot)
CARTb = rpart(isB ~ . - letter, data=train, method="class")
prp(CARTb)
predict = predict(CARTb, newdata=test, type="class")
table(test$isB, predict)
accuracy = (1118+340)/nrow(test)

# Create CART Model to predict letter
library(rpart)
library(rpart.plot)
CARTLetter = rpart(letter ~ . - isB, data=train, method="class")
prp(CARTLetter)
predict = predict(CARTLetter, newdata=test, type="class")
table(test$letter, predict)
accuracy = (348+318+363+340)/nrow(test)

# Create Random Forest Model to predict isB
library(randomForest)
set.seed(1000)
forest = randomForest(isB ~ . - letter, data=train)
predict = predict(forest , newdata=test)
table(test$isB, predict)
accuracy = (1165+374)/nrow(test)

# Create Random Forest Model to predict letter
library(randomForest)
set.seed(1000)
forest = randomForest(letter ~ . - isB, data=train)
predict = predict(forest , newdata=test)
table(test$letter, predict)
accuracy = (390+380+393+364)/nrow(test)