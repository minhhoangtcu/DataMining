# Unit 5 - Twitter
# VIDEO 5
# Read in the data
tweets = read.csv("dataset/tweets.csv", stringsAsFactors=FALSE)
str(tweets)

# Create dependent variable
tweets$Negative = as.factor(tweets$Avg <= -1)
table(tweets$Negative)

# Install new packages
install.packages("tm")
library(tm)
install.packages("SnowballC")
library(SnowballC)

# Create corpus
corpus = Corpus(VectorSource(tweets$Tweet))
# Look at corpus
corpus
# Convert to lower-case
corpus = tm_map(corpus, tolower)
corpus = tm_map(corpus, PlainTextDocument)
# Remove punctuation
corpus = tm_map(corpus, removePunctuation)
# Look at stop words 
stopwords("english")[1:10]
# Remove stopwords and apple
corpus = tm_map(corpus, removeWords, c("apple", stopwords("english")))
# Stem document 
corpus = tm_map(corpus, stemDocument)

# Video 6
# Create matrix
frequencies = DocumentTermMatrix(corpus)
frequencies

# Look at matrix 
inspect(frequencies[1000:1005,505:515])

# Check for sparsity
findFreqTerms(frequencies, lowfreq=20)
findFreqTerms(frequencies, lowfreq=100)

# Remove sparse terms
sparse = removeSparseTerms(frequencies, 0.995)
sparse

# Convert to a data frame
tweetsSparse = as.data.frame(as.matrix(sparse))
# Make all variable names R-friendly
colnames(tweetsSparse) = make.names(colnames(tweetsSparse))
# Add dependent variable
tweetsSparse$Negative = tweets$Negative

# Split the data
install.packages("caTools")
library(caTools)
set.seed(123)
split = sample.split(tweetsSparse$Negative, SplitRatio = 0.7)
trainSparse = subset(tweetsSparse, split==TRUE)
testSparse = subset(tweetsSparse, split==FALSE)

# Video 7
# Build a CART model
install.packages("rpart")
install.packages("rpart.plot")
library(rpart)
library(rpart.plot)

tweetCART = rpart(Negative ~ ., data=trainSparse, method="class")
prp(tweetCART)

# Evaluate the performance of the model
predictCART = predict(tweetCART, newdata=testSparse, type="class")
table(testSparse$Negative, predictCART)

# Compute accuracy
(294+18)/(294+6+37+18)

# Baseline accuracy 
table(testSparse$Negative)
300/(300+55)

# Random forest model
library(randomForest)
set.seed(123)
tweetRF = randomForest(Negative ~ ., data=trainSparse)

# Make predictions:
predictRF = predict(tweetRF, newdata=testSparse)
table(testSparse$Negative, predictRF)

# Accuracy:
(293+21)/(293+7+34+21)

# Logistic Regression Model
tweetLogReg = glm(Negative ~ ., data=trainSparse, family="binomial")
predictions = predict(tweetLogReg, newdata=testSparse, type="response")
table(testSparse$Negative, predictions > 0.5)
(253+33)/nrow(testSparse)