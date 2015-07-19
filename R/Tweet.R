# Load dataset
install.packages("wordcloud")
library(wordcloud)
library(RColorBrewer)
library("tm")
library("SnowballC")
setwd("D:/Computer Science/R/Dataset")
tweets = read.csv("tweets.csv", stringsAsFactors=FALSE)

# Pre-Process text
corpus = Corpus(VectorSource(tweets$Tweet))
corpus = tm_map(corpus, tolower)
corpus = tm_map(corpus, PlainTextDocument)
corpus = tm_map(corpus, removePunctuation)
corpus = tm_map(corpus, removeWords, c("apple", stopwords("english")))
dtm = DocumentTermMatrix(corpus)
allTweets = as.data.frame(as.matrix(dtm))

# Create a word cloud
allWords =  colnames(allTweets)
allFreq = colSums(allTweets)
wordcloud(allWords, allFreq, min.freq=5, random.order = FALSE, colors=brewer.pal(9, "Blues")[c(1, 2, 3, 4)])
brewer.pal()
display.brewer.all() 
