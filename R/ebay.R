# Load dataset and inspect them
setwd("D:/Computer Science/R/Dataset")
eBayTrain = read.csv("eBayiPadTrain.csv", stringsAsFactors=FALSE)
eBayTest = read.csv("eBayiPadTest.csv", stringsAsFactors=FALSE)
str(eBayTrain)
summary(eBayTrain)
str(eBayTest)

# Convert into factors
eBayTrain$biddable = as.factor(eBayTrain$biddable)
eBayTrain$condition = as.factor(eBayTrain$condition)
eBayTrain$cellular = as.factor(eBayTrain$cellular)
eBayTrain$carrier = as.factor(eBayTrain$carrier)
eBayTrain$color = as.factor(eBayTrain$color)
eBayTrain$storage = as.factor(eBayTrain$storage)
eBayTrain$sold = as.factor(eBayTrain$sold)

eBayTest$biddable = as.factor(eBayTest$biddable)
eBayTest$condition = as.factor(eBayTest$condition)
eBayTest$cellular = as.factor(eBayTest$cellular)
eBayTest$carrier = as.factor(eBayTest$carrier)
eBayTest$color = as.factor(eBayTest$color)
eBayTest$storage = as.factor(eBayTest$storage)


# Splitting training data
library(caTools)
split = sample.split(eBayTrain$sold, SplitRatio = 0.8)
train = subset(eBayTrain, split==TRUE)
test = subset(eBayTrain, split==FALSE)

# Simple Model
SimpleMod = glm(sold ~ startprice, data=train, family=binomial)
PredTest = predict(SimpleMod, newdata=test, type="response")
cfmGLM = table(test$sold, PredTest > 0.5)
accuracyGLM = (cfmGLM[1,1] + cfmGLM[2,2])/nrow(test)
accuracyGLM

# Construct random forest
library(randomForest)
forest = randomForest(sold ~ biddable + startprice + condition + cellular + color + storage, data = train)
predictForest = predict(forest, newdata=test)
cfmForest = table(test$sold, predictForest)
accuracyForest = (cfmForest[1,1] + cfmForest[2,2])/nrow(test)
accuracyForest

# Print out submission
submitModel = randomForest(sold ~ biddable + startprice + condition + cellular + color + storage, data = eBayTrain)
submitPredict = predict(submitModel, newdata=eBayTest)
MySubmission = data.frame(UniqueID = eBayTest$UniqueID, Probability1 = submitPredict)
write.csv(MySubmission, "ebay02.csv", row.names=FALSE)
