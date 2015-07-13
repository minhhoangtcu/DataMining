# Set working directory and import datafiles
setwd("D:/Computer Science/R/Dataset")
TitanicTrain = read.csv("D:/Computer Science/R/Dataset/TitanicTrain.csv")
TitanicTest = read.csv("D:/Computer Science/R/Dataset/TitanicTest.csv")
str(TitanicTrain)

# Inspect the train set, make baseline model and output an csv file of people' survival predicted using the model.
prop.table(table(TitanicTrain$Survived)) # We see that most people died
TitanicTest$Survived = 0
submit = data.frame(PassengerId = TitanicTest$PassengerId, Survived = TitanicTest$Survived)
write.csv(submit, file = "theyallperish.csv", row.names = FALSE)

