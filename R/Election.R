# Load required library
library(ggmap)
library(ggplot2)
library(maps)

# Create map
statesMap = map_data("state")
str(statesMap)
sum(table(statesMap$group))
ggplot(statesMap, aes(x = long, y = lat, group = group)) + geom_polygon(fill = "white", color = "black")

# Load dataset
setwd("D:/Computer Science/R/Dataset")
polling = read.csv("PollingImputed.csv")
Train = subset(polling, (Year >= 2004) & (Year <= 2008))
Test = subset(polling, Year == 2012)

# Constructe Model
mod2 = glm(Republican~SurveyUSA+DiffCount, data=Train, family="binomial")
TestPrediction = predict(mod2, newdata=Test, type="response")
TestPredictionBinary = as.numeric(TestPrediction > 0.5)
predictionDataFrame = data.frame(TestPrediction, TestPredictionBinary, Test$State)
table(Test$Republican, TestPredictionBinary)

# Merge
predictionDataFrame$region = tolower(predictionDataFrame$Test.State)
predictionMap = merge(statesMap, predictionDataFrame, by = "region")
predictionMap = predictionMap[order(predictionMap$order),]
str(predictionMap)
subset(predictionMap, region=="florida")

# Plot
ggplot(predictionMap, aes(x = long, y = lat, group = group, fill = TestPredictionBinary)) + geom_polygon(color = "black")
ggplot(predictionMap, aes(x = long, y = lat, group = group, fill = TestPredictionBinary))+ geom_polygon(color = "black") + scale_fill_gradient(low = "blue", high = "red", guide = "legend", breaks= c(0,1), labels = c("Democrat", "Republican"), name = "Prediction 2012")
ggplot(predictionMap, aes(x = long, y = lat, group = group, fill = TestPrediction))+ geom_polygon(color = "black") + scale_fill_gradient(low = "blue", high = "red", name = "Prediction 2012")
