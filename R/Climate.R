# Load datasets
climate = read.csv("dataset/climate_change.csv")
climateTrain = subset(climate, Year <= 2006)
climateTest = subset(climate, Year > 2006)

# Observe the structures of the datasets
str(climate)
str(climateTrain)
str(climateTest)

# Construct a linear regression model
tempModel0 = lm(Temp ~ MEI + CO2 + CH4 + N2O + CFC.11 + CFC.12 + TSI + Aerosols, data=climateTrain)
summary(tempModel0)
tempModel1 = lm(Temp ~ MEI + TSI + Aerosols + N2O, data=climateTrain)
summary(tempModel1)
tempModel2 = step(tempModel)
summary(tempModel2)

# Predict
predictTest = predict(tempModel2, newdata=climateTest)
SSE = sum((climateTest$Temp - predictTest )^2)
SSE
SST = sum(climateTest$Temp- (mean(climateTrain$Temp))^2)
SST
R2 = 1 - (SSE/SST)
R2