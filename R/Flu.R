FluTrain = read.csv("dataset/FluTrain.csv")
FluTest = read.csv("dataset/FluTest.csv")
install.packages("zoo")
library(zoo)

str(FluTrain)
str(FluTest)
summary(FluTrain)
summary(FluTest)

# Data Analyze
FluTrain$Week[which.max(FluTrain$ILI)]
FluTrain$Week[which.max(FluTrain$Queries)]
hist(FluTrain$ILI)
hist(FluTrain$Queries)
plot(log(FluTrain$ILI), FluTrain$Queries)
plot(log(FluTrain$ILI), log(FluTrain$Queries))

# Linear Regression
FluTrend1 = lm(log(ILI) ~ Queries, data=FluTrain)
summary(FluTrend1)
Correlation = cor(log(FluTrain$ILI), FluTrain$Queries)
log(1/Correlation)
Correlation^2

PredTest1 = exp(predict(FluTrend1, newdata=FluTest))
which(FluTest$Week == "2012-03-11 - 2012-03-17")

predictedILI = PredTest1[11]
observedILI = FluTest$ILI[11]
relativeError = (observedILI - predictedILI)/observedILI
relativeError

SSE = sum((FluTest$ILI - PredTest1)^2)
RMSE = sqrt(SSE/nrow(FluTest))
RMSE 

ILILag2Train = lag(zoo(FluTrain$ILI), -2, na.pad=TRUE)
FluTrain$ILILag2 = coredata(ILILag2Train)
ILILag2Test = lag(zoo(FluTest$ILI), -2, na.pad=TRUE)
FluTest$ILILag2 = coredata(ILILag2Test)
FluTest$ILILag2[1] = FluTrain$ILI[416]
FluTest$ILILag2[2] = FluTrain$ILI[417]
FluTest$ILILag2[1]
FluTest$ILILag2[2]

plot(log(FluTrain$ILI),log(FluTrain$ILILag2))

FluTrend2 = lm(log(ILI) ~ Queries + log(ILILag2), data=FluTrain)
summary(FluTrend2)

PredTest2 = exp(predict(FluTrend2, newdata=FluTest))
SSE = sum((FluTest$ILI - PredTest2 )^2)
RMSE = sqrt(SSE/nrow(FluTest))
RMSE

