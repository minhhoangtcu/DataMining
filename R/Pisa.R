# Load dataset
pisaTrain = read.csv("dataset/pisa2009train.csv")
pisaTest= read.csv("dataset/pisa2009test.csv")
pisaTestSmall= read.csv("dataset/pisa2009testSmall.csv")
pisaTrain = na.omit(pisaTrain)
pisaTest = na.omit(pisaTest)
pisaTrain$raceeth = relevel(pisaTrain$raceeth, "White")
pisaTest$raceeth = relevel(pisaTest$raceeth, "White")

# Inspect dataset
str(pisaTrain)
summary(pisaTrain)
str(pisaTest)
tapply(pisaTrain$readingScore, pisaTrain$male, mean)

# Build linear regression model
lmScore = lm(readingScore ~ ., data=pisaTrain)
summary(lmScore)
SSE = sum(lmScore$residuals^2)
SSE
RMSE = sqrt(SSE/nrow(pisaTrain))
RMSE

# Predict
predictSmall = predict(lmScore, newdata=pisaTestSmall)
predictSmall

predTest = predict(lmScore, newdata=pisaTest)
summary(predTest)
SSE = sum((pisaTest$readingScore - predTest)^2)
SSE
baseLinePredictedScore = mean(pisaTrain$readingScore)
baseLinePredictedScore
SST = sum((pisaTest$readingScore - baseLinePredictedScore)^2)
SST
RMSE = sqrt(SSE/nrow(pisaTest))
RMSE
R2 = 1-SSE/SST
R2
