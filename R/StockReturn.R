# Load dataset and inspect the dataset
stocks = read.csv("dataset/StocksCluster.csv")
str(stocks)
table(stocks$PositiveDec)[[2]]
portionOfReturn = table(stocks$PositiveDec)[[2]]/nrow(stocks)
sort(cor(stocks))
colMeans(stocks[0:11])
which.max(colMeans(stocks[0:11]))
which.min(colMeans(stocks[0:11]))

# Split the dataset
library(caTools)
set.seed(144)
spl = sample.split(stocks$PositiveDec, SplitRatio = 0.7)
stocksTrain = subset(stocks, spl == TRUE)
stocksTest = subset(stocks, spl == FALSE)
str(stocksTrain)

StocksModel = glm(PositiveDec ~ ., data=stocksTrain, family=binomial)
predictTrain = predict(StocksModel, type="response")
confusionTable = table(stocksTrain$PositiveDec, predictTrain > 0.5)
accuracy = (confusionTable[1,1] + confusionTable[2,2])/nrow(stocksTrain)

predictTest = predict(StocksModel, newdata=stocksTest, type="response")
confusionTable = table(stocksTest$PositiveDec, predictTest > 0.5)
accuracy = (confusionTable[1,1] + confusionTable[2,2])/nrow(stocksTest)
blConfusionTable = table(stocksTest$PositiveDec)
accracyBaseLine = blConfusionTable[[2]] / (blConfusionTable[[2]]+blConfusionTable[[1]])

# Remove dependant variables for clustering
limitedTrain = stocksTrain
limitedTrain$PositiveDec = NULL
limitedTest = stocksTest
limitedTest$PositiveDec = NULL

# Normalize variables
library(caret)
preproc = preProcess(limitedTrain)
normTrain = predict(preproc, limitedTrain)
normTest = predict(preproc, limitedTest)

summary(normTrain$ReturnJan)
summary(normTest$ReturnJan)

# Create k-means cluster
set.seed(144)
km = kmeans(normTrain, centers=3)
str(km)
table(km$cluster)

install.packages("flexclust")
library(flexclust)
km.kcca = as.kcca(km, normTrain)
clusterTrain = predict(km.kcca)
clusterTest = predict(km.kcca, newdata=normTest)

table(clusterTest)

stocksTrain1 = subset(stocksTrain, clusterTrain == 1)
stocksTrain2 = subset(stocksTrain, clusterTrain == 2)
stocksTrain3 = subset(stocksTrain, clusterTrain == 3)
stocksTest1 = subset(stocksTest, clusterTest == 1)
stocksTest2 = subset(stocksTest, clusterTest == 2)
stocksTest3 = subset(stocksTest, clusterTest == 3)
summary(stocksTrain1$PositiveDec)
summary(stocksTrain2$PositiveDec)
summary(stocksTrain3$PositiveDec)

StocksModel1 = glm(PositiveDec ~ ., data=stocksTrain1, family=binomial)
StocksModel2 = glm(PositiveDec ~ ., data=stocksTrain2, family=binomial)
StocksModel3 = glm(PositiveDec ~ ., data=stocksTrain3, family=binomial)
summary(StocksModel1)
summary(StocksModel2)
summary(StocksModel3)

predictTest1 = predict(StocksModel1, newdata=stocksTest1, type="response")
predictTest2 = predict(StocksModel2, newdata=stocksTest2, type="response")
predictTest3 = predict(StocksModel3, newdata=stocksTest3, type="response")

confusionTable1 = table(stocksTest1$PositiveDec, predictTest1 > 0.5)
accuracy1 = (confusionTable1[1,1] + confusionTable1[2,2])/nrow(stocksTest1)
confusionTable2 = table(stocksTest2$PositiveDec, predictTest2 > 0.5)
accuracy2 = (confusionTable2[1,1] + confusionTable2[2,2])/nrow(stocksTest2)
confusionTable3 = table(stocksTest3$PositiveDec, predictTest3 > 0.5)
accuracy3 = (confusionTable3[1,1] + confusionTable3[2,2])/nrow(stocksTest3)

AllPredictions = c(predictTest1, predictTest2, predictTest3)
AllOutcomes = c(stocksTest1$PositiveDec, stocksTest2$PositiveDec, stocksTest3$PositiveDec)
aConfutionTable = table(AllOutcomes, AllPredictions > 0.5)
aAccuracy = (aConfutionTable[1,1] + aConfutionTable[2,2])/nrow(stocksTest)






