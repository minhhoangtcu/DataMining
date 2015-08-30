# Load dataset and inspect them
setwd("D:/Computer Science/R/Dataset")
eBayTrain = read.csv("eBayiPadTrain.csv", stringsAsFactors=FALSE)
eBayTest = read.csv("eBayiPadTest.csv", stringsAsFactors=FALSE)
str(eBayTrain)
summary(eBayTrain)
str(eBayTest)

library("ggplot2")
# The cheaper the starting price, the higher chance the ipad are getting sold
ggplot(eBayTrain, aes(x=startprice, color=sold)) + geom_histogram()
# Biddable ipads are more likely to be sold
aggregate(sold ~ biddable, data=eBayTrain, FUN=function(x) { sum(x)/length(x)}) 
# Interesting enough, people actually buy broken ipad the most
aggregate(sold ~ condition, data=eBayTrain, FUN=function(x) { sum(x)/length(x)}) 

# Convert into factors
eBayTrain$biddable = as.factor(eBayTrain$biddable)
eBayTrain$condition = as.factor(eBayTrain$condition)
eBayTrain$cellular = as.factor(eBayTrain$cellular)
eBayTrain$carrier = as.factor(eBayTrain$carrier)
eBayTrain$color = as.factor(eBayTrain$color)
#eBayTrain$storage = as.numeric(eBayTrain$storage)
eBayTrain$storage = as.factor(eBayTrain$storage)
eBayTrain$sold = as.factor(eBayTrain$sold)

eBayTest$biddable = as.factor(eBayTest$biddable)
eBayTest$condition = as.factor(eBayTest$condition)
eBayTest$cellular = as.factor(eBayTest$cellular)
eBayTest$carrier = as.factor(eBayTest$carrier)
eBayTest$color = as.factor(eBayTest$color)
#eBayTest$storage = as.numeric(eBayTest$storage)
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

library(tm)
CorpusDescription = Corpus(VectorSource(c(eBayTrain$description, eBayTest$description)))
CorpusDescription = tm_map(CorpusDescription, content_transformer(tolower), lazy=TRUE)
CorpusDescription = tm_map(CorpusDescription, PlainTextDocument, lazy=TRUE)
CorpusDescription = tm_map(CorpusDescription, removePunctuation, lazy=TRUE)
CorpusDescription = tm_map(CorpusDescription, removeWords, stopwords("english"), lazy=TRUE)
CorpusDescription = tm_map(CorpusDescription, stemDocument, lazy=TRUE)
dtm = DocumentTermMatrix(CorpusDescription)
sparse = removeSparseTerms(dtm, 0.99)
DescriptionWords = as.data.frame(as.matrix(sparse))
str(DescriptionWords)

# Let's make sure our variable names are okay for R:
colnames(DescriptionWords) = make.names(colnames(DescriptionWords))

# Because we combined both the Train and the Test, we need to split them out again
DescriptionWordsTrain = head(DescriptionWords, nrow(eBayTrain))
DescriptionWordsTest = tail(DescriptionWords, nrow(eBayTest))
str(DescriptionWordsTrain)
str(DescriptionWordsTest)

# Added back variables
str(eBayTrain)
str(eBayTest)
DescriptionWordsTrain$sold = eBayTrain$sold
DescriptionWordsTrain$biddable = eBayTrain$biddable
DescriptionWordsTrain$startprice = eBayTrain$startprice
DescriptionWordsTrain$condition = eBayTrain$condition
DescriptionWordsTrain$cellular = eBayTrain$cellular
DescriptionWordsTrain$carrier = eBayTrain$carrier
DescriptionWordsTrain$color = eBayTrain$color
DescriptionWordsTrain$storage = eBayTrain$storage
DescriptionWordsTrain$productline = eBayTrain$productline
DescriptionWordsTrain$WordCount = eBayTrain$WordCount

DescriptionWordsTest$sold = eBayTest$sold
DescriptionWordsTest$biddable = eBayTest$biddable
DescriptionWordsTest$startprice = eBayTest$startprice
DescriptionWordsTest$condition = eBayTest$condition
DescriptionWordsTest$cellular = eBayTest$cellular
DescriptionWordsTest$carrier = eBayTest$carrier
DescriptionWordsTest$color = eBayTest$color
DescriptionWordsTest$storage = eBayTest$storage
DescriptionWordsTest$productline = eBayTest$productline
DescriptionWordsTest$WordCount = eBayTest$WordCount



#Test
split = sample.split(DescriptionWordsTrain$sold, SplitRatio = 0.8)
train = subset(DescriptionWordsTrain, split==TRUE)
test = subset(DescriptionWordsTrain, split==FALSE)
DescriptionWordsLogTest = glm(sold ~ . - productline, data=train, family=binomial)
PredTestTest = predict(DescriptionWordsLogTest, newdata=test, type="response")
cfmDWT = table(test$sold, PredTestTest > 0.5)
accuracyDWT = (cfmDWT[1,1] + cfmDWT[2,2])/nrow(test)
accuracyDWT

#Test2
summary(train)
forest = randomForest(sold ~ . - productline, data = train)
predictForest = predict(forest, newdata=test)
cfmForest = table(test$sold, predictForest)
accuracyForest = (cfmForest[1,1] + cfmForest[2,2])/nrow(test)
accuracyForest

# Submission
DescriptionWordsLog = glm(sold ~ ., data=DescriptionWordsTrain, family=binomial)
PredTest = predict(DescriptionWordsLog, newdata=DescriptionWordsTest, type="response")
MySubmission = data.frame(UniqueID = eBayTest$UniqueID, Probability1 = PredTest)
write.csv(MySubmission, "trash", row.names=FALSE)

# Submission
forestTest =randomForest(sold ~ . - productline, data = DescriptionWordsTrain)
PredTest = predict(forestTest, newdata=DescriptionWordsTest, type="response")
MySubmission = data.frame(UniqueID = eBayTest$UniqueID, Probability1 = PredTest)
write.csv(MySubmission, "ebay05.csv", row.names=FALSE)

# Print out submission
submitModel = randomForest(sold ~ biddable + startprice + condition + cellular + color + storage, data = eBayTrain)
submitPredict = predict(submitModel, newdata=eBayTest)
MySubmission = data.frame(UniqueID = eBayTest$UniqueID, Probability1 = submitPredict)
write.csv(MySubmission, "trash.csv", row.names=FALSE)


