# Load dataset
census = read.csv("dataset/census.csv")
str(census)

# Split the dataset
library(caTools)
set.seed(2000)
split = sample.split(census$over50k, SplitRatio = 0.6)
train = subset(census, split==TRUE)
test = subset(census, split==FALSE)

# Construct logistic regression
model = glm(over50k ~ ., data=train, family="binomial")
summary(model)
predict = predict(model, newdata=test, type="response")
table(test$over50k, predict > 0.5)
(9051 + 1888)/nrow(test)
table(test$over50k)
9713/nrow(test)
library(ROCR)
ROCRprediction = prediction(predict, test$over50k)
as.numeric(performance(ROCRprediction, "auc")@y.values)
ROCRperf = performance(ROCRprediction, "tpr", "fpr")
plot(ROCRperf, colorize=TRUE, print.cutoffs.at=seq(0,1,0.1), text.adj = c(-0.2,1.7))

# Construct a classification tree using classification
library(rpart)
library(rpart.plot)
tree = rpart(over50k ~ ., data=train, method="class")
prp(tree)
prediction = predict(tree, newdata=test, type="class")
table(test$over50k, prediction)
(9243 + 1596)/nrow(test)

# Construct a classification tree using probability
library(rpart)
library(rpart.plot)
tree = rpart(over50k ~ ., data=train)
prp(tree)
prediction = predict(tree, newdata=test)
table(test$over50k, prediction[,2] > 0.5)
(9243 + 1596)/nrow(test)
ROCRprediction = prediction(prediction[,2], test$over50k)
ROCRperf = performance(ROCRprediction, "tpr", "fpr")
plot(ROCRperf, colorize=TRUE, print.cutoffs.at=seq(0,1,0.1), text.adj = c(-0.2,1.7))
as.numeric(performance(ROCRprediction, "auc")@y.values)

# Construction random forest
set.seed(1)
trainSmall = train[sample(nrow(train), 2000), ]
library(randomForest)
set.seed(1)
forest = randomForest(over50k ~ ., data=trainSmall)
predict = predict(forest, newdata=test)
table(test$over50k, predict)
(9584+1090)/nrow(test)
vu = varUsed(forest, count=TRUE)
vusorted = sort(vu, decreasing = FALSE, index.return = TRUE)
dotchart(vusorted$x, names(forest$forest$xlevels[vusorted$ix]))
varImpPlot(forest)

# Cross-Validate to compute a good cp
install.packages("class")
library(class)
library(caret)
library(e1071)
numFolds = trainControl(method="cv", number=10)
cartGrid = expand.grid( .cp = seq(0.002,0.1,0.002))
train(over50k ~ ., data=train, method="rpart", trControl=numFolds, tuneGrid=cartGrid)
tree = rpart(over50k ~ ., data=train, cp=0.002)
prp(tree)
prediction = predict(tree, newdata=test, type="class")
table(test$over50k, prediction)
(9178+1838)/nrow(test)