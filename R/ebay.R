# Load dataset
setwd("D:/Computer Science/R/Dataset")
eBayTrain = read.csv("eBayiPadTrain.csv", stringsAsFactors=FALSE)
eBayTest = read.csv("eBayiPadTest.csv", stringsAsFactors=FALSE)

# Construct simple model
SimpleMod = glm(sold ~ startprice, data=eBayTrain, family=binomial)
PredTest = predict(SimpleMod, newdata=eBayTest, type="response")

MySubmission = data.frame(UniqueID = eBayTest$UniqueID, Probability1 = PredTest)
write.csv(MySubmission, "ebay01.csv", row.names=FALSE)
