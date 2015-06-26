# Load dataset
gerber = read.csv("dataset/gerber.csv")

# Inspect dataset
str(gerber)
table(gerber$voting)
proportionOfVotes = 108696/nrow(gerber)

table(gerber$voting, gerber$hawthorne)
proportionHawthorne = 12316/nrow(gerber)
table(gerber$voting, gerber$civicduty)
proportionCivicduty = 12021/nrow(gerber)
table(gerber$voting, gerber$neighbors)
proportionNeighbors = 14438/nrow(gerber)
table(gerber$voting, gerber$self)
proportionSelf = 13191/nrow(gerber)
table(gerber$voting, gerber$control)
proportionControl = 56730/nrow(gerber)
which.max(c(proportionHawthorne, proportionCivicduty, proportionNeighbors, proportionSelf))

# Constuct Logistic Regression
voteModel = glm(voting ~ hawthorne + civicduty + neighbors + self, data=gerber, family="binomial")
summary(voteModel)
predict = predict(voteModel, type="response")
table(gerber$voting, predict > 0.5)
(51966+134513)/nrow(gerber)
235388/nrow(gerber)
library(ROCR)
ROCRpred = prediction(predict, gerber$voting)
as.numeric(performance(ROCRpred, "auc")@y.values)

LogModelSex= glm(voting ~ control + sex, data=gerber, family="binomial")
summary(LogModelSex)
LogModel2 = glm(voting ~ sex + control + sex:control, data=gerber, family="binomial")
summary(LogModel2)


# Constuct CART
library(rpart)
library(rpart.plot)
CARTmodel = rpart(voting ~ civicduty + hawthorne + self + neighbors, data=gerber)
CARTmodel2 = rpart(voting ~ civicduty + hawthorne + self + neighbors, data=gerber, cp=0.0)
CARTmodel3 = rpart(voting ~ sex + civicduty + hawthorne + self + neighbors, data=gerber, cp=0.0)
CARTmodel4 = rpart(voting ~ control, data=gerber, cp=0.0)
CARTmodel5 = rpart(voting ~ control + sex, data=gerber, cp=0.0)
prp(CARTmodel)
prp(CARTmodel2)
prp(CARTmodel3)
prp(CARTmodel4, digits = 6)
prp(CARTmodel5, digits=6)


Possibilities = data.frame(sex=c(0,0,1,1),control=c(0,1,0,1))
predict(LogModelSex, newdata=Possibilities, type="response")
predict(LogModel2, newdata=Possibilities, type="response")