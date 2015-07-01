# Load dataset
trials = read.csv("dataset/clinical_trial.csv", stringsAsFactors=FALSE)

# Inspect dataset
str(trials)
summary(trials)
numberOfChars = nchar(trials$abstract)
numberOfChars2 = nchar(trials$title)
which.min(numberOfChars2)
trials$title[1258]
summary(numberOfChars)
summary(numberOfChars2)
table(numberOfChars == 0)

# Create documents
library(tm)
library(SnowballC)
corpusTitle = Corpus(VectorSource(trials$title))
corpusAbstract = Corpus(VectorSource(trials$abstract))
corpusTitle = tm_map(corpusTitle, tolower)
corpusAbstract = tm_map(corpusAbstract, tolower)
corpusTitle = tm_map(corpusTitle, PlainTextDocument)
corpusAbstract = tm_map(corpusAbstract, PlainTextDocument)
corpusTitle = tm_map(corpusTitle, removePunctuation)
corpusAbstract = tm_map(corpusAbstract, removePunctuation)
corpusTitle = tm_map(corpusTitle, removeWords, c(stopwords("english")))
corpusAbstract = tm_map(corpusAbstract, removeWords, c(stopwords("english")))
corpusTitle = tm_map(corpusTitle, stemDocument)
corpusAbstract = tm_map(corpusAbstract, stemDocument)
dtmTitle = DocumentTermMatrix(corpusTitle)
dtmAbstract = DocumentTermMatrix(corpusAbstract)
dtmTitle = removeSparseTerms(dtmTitle, 0.95)
dtmAbstract = removeSparseTerms(dtmAbstract, 0.95)

# Create new dataframe
title = as.data.frame(as.matrix(dtmTitle))
abstract = as.data.frame(as.matrix(dtmAbstract))
sort(colSums(abstract))
colnames(title) = paste0("T", colnames(title))
colnames(abstract) = paste0("A", colnames(abstract))
dtm = cbind(title, abstract)
dtm$trial = trials$trial
str(dtm)

# Create Model
library(caTools)
set.seed(144)
sql = sample.split(dtm$trial, SplitRatio=0.7)
train = subset(dtm, sql==TRUE)
test = subset(dtm, sql==FALSE)
library(rpart)
library(rpart.plot)
tree = rpart(trial ~ ., data=train, method="class")
prp(tree)
predictTrain = predict(tree)
table(train$trial, predictTrain[,2] > 0.5)
max(predictTrain[,2])
predictTest = predict(tree, newdata=test)
table(test$trial, predictTest[,2] > 0.5)

library(ROCR)
ROCRpred = prediction(predictTest[,2], test$trial)
as.numeric(performance(ROCRpred, "auc")@y.values)