# Set working directory and import datafiles
setwd("D:/Computer Science/R/Dataset")
train = read.csv("D:/Computer Science/R/Dataset/TitanicTrain.csv")
test = read.csv("D:/Computer Science/R/Dataset/TitanicTest.csv")
str(train)

# Inspect the train set, make baseline model and output an csv file of people' survival predicted using the model.
prop.table(table(train$Survived)) # We see that most people died
test$Survived = 0
submit = data.frame(PassengerId = test$PassengerId, Survived = test$Survived)
write.csv(submit, file = "theyallperish.csv", row.names = FALSE)

# Women and Children often go on the boat first. Thus, we inspect the survival rate for genders.
prop.table(table(train$Sex, train$Survived),1)
test$Survived[test$Sex == 'female'] = 1
summary(train$Age)
train$Child = 0
train$Child[train$Age < 18] = 1
aggregate(Survived ~ Child + Sex, data=train, FUN=function(x) {sum(x)/length(x)})

# Inspect the fare
train$Fare2 = '30+'
train$Fare2[train$Fare < 30 & train$Fare >= 20] = '20-30'
train$Fare2[train$Fare < 20 & train$Fare >= 10] = '10-20'
train$Fare2[train$Fare < 10] = '<10'
aggregate(Survived ~ Fare2 + Pclass + Sex, data=train, FUN=function(x) {sum(x)/length(x)})
test$Survived = 0
test$Survived[test$Sex == 'female'] = 1
test$Survived[test$Sex == 'female' & test$Pclass == 3 & test$Fare >= 20] = 0
submit = data.frame(PassengerId = test$PassengerId, Survived = test$Survived)
write.csv(submit, file = "Titanic01.csv", row.names = FALSE)

# Plan a tree and Predict survival rate based on CART model
library(rpart)
library(rpart.plot)
install.packages('rattle')
install.packages('RColorBrewer')
library(rattle)
library(RColorBrewer)
fit = rpart(Survived ~ Pclass + Sex + Age + SibSp + Parch + Fare + Embarked, data=train, method="class")
plot(fit)
text(fit)
fancyRpartPlot(fit)
Prediction = predict(fit, newdata=test, type = "class")
submit = data.frame(PassengerId = test$PassengerId, Survived = Prediction)
write.csv(submit, file = "Titanic02.csv", row.names = FALSE)

