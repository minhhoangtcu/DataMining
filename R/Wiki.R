# Load dataset
wiki = read.csv("dataset/wiki.csv", stringsAsFactors=FALSE)
wiki$Vandal = as.factor(wiki$Vandal)

# Inspect dataset
table(wiki$Vandal)
str(wiki)

# Create corpus and documents
#install.packages("tm")
#install.packages("SnowballC")
library(tm)
library(SnowballC)
corpusAdded = Corpus(VectorSource(wiki$Added))
corpusAdded = tm_map(corpusAdded, removeWords, c(stopwords("english")))
corpusAdded = tm_map(corpusAdded, stemDocument)
dtmAdded = DocumentTermMatrix(corpusAdded)
sparseAdded = removeSparseTerms(dtmAdded, 0.999)
wordsAdded = as.data.frame(as.matrix(sparseAdded))
colnames(wordsAdded) = paste("A", colnames(wordsAdded))

corpusRemoved = Corpus(VectorSource(wiki$Removed))
corpusRemoved = tm_map(corpusRemoved, removeWords, c(stopwords("english")))
corpusRemoved = tm_map(corpusRemoved, stemDocument)
dtmRemoved = DocumentTermMatrix(corpusRemoved)
sparseRemoved = removeSparseTerms(dtmRemoved, 0.999)
wordsRemoved = as.data.frame(as.matrix(sparseRemoved))
colnames(wordsRemoved) = paste("R", colnames(wordsRemoved))

# Prepare new dataframe
wikiWords = cbind(wordsAdded, wordsRemoved)
#colnames(wikiWords) = make.names(colnames(wikiWords))
wikiWords$Vandal = wiki$Vandal
library(caTools)
set.seed(123)
split = sample.split(wikiWords$Vandal, SplitRatio = 0.7)
wikiTrain = subset(wikiWords, split == TRUE)
wikiTest = subset(wikiWords, split == FALSE)

# Build CART Model
library(rpart)
library(rpart.plot)
wikiTree = rpart(Vandal ~ ., data=wikiTrain)
prp(wikiTree)
predict = predict(wikiTree, newdata=wikiTest)
table(wikiTest$Vandal, predict[,2] > 0.5)

predict = predict(wikiTree)
table(wikiTrain$Vandal, predict[,2] > 0.5)

# Use different method. Add a variable to check if contains link or not
wikiWords2 = wikiWords
wikiWords2$HTTP = ifelse(grepl("http",wiki$Added,fixed=TRUE), 1, 0)
wikiTrain2 = subset(wikiWords2, split ==TRUE)
wikiTest2 = subset(wikiWords2, split ==FALSE)
wikiTree2 = rpart(Vandal ~ ., data=wikiTrain2)
predict2 = predict(wikiTree2, newdata=wikiTest2)
table(wikiTest2$Vandal, predict2[,2] > 0.5)

wikiWords2$NumWordsAdded = rowSums(as.matrix(dtmAdded))
wikiWords2$NumWordsRemoved = rowSums(as.matrix(dtmRemoved))
wikiTrain3 = subset(wikiWords2, split ==TRUE)
wikiTest3 = subset(wikiWords2, split ==FALSE)
wikiTree3 = rpart(Vandal ~ ., data=wikiTrain3)
predict3 = predict(wikiTree3, newdata=wikiTest3)
table(wikiTest3$Vandal, predict3[,2] > 0.5)

wikiWords3 = wikiWords2
wikiWords3$Minor = wiki$Minor
wikiWords3$Loggedin = wiki$Loggedin
wikiTrain4 = subset(wikiWords3, split ==TRUE)
wikiTest4 = subset(wikiWords3, split ==FALSE)
wikiTree4 = rpart(Vandal ~ ., data=wikiTrain4)
prp(wikiTree4)
predict4 = predict(wikiTree4, newdata=wikiTest4)
table(wikiTest4$Vandal, predict4[,2] > 0.5)


