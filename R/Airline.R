# Load dataset and modify dataset
airlines = read.csv("dataset/AirlinesCluster.csv")
str(airlines)
summary(airlines)
library(caret)
preproc = preProcess(airlines)
airlinesNorm = predict(preproc, airlines)
summary(airlinesNorm)

# Hierachical Cluster
hDistances = dist(airlinesNorm, method="euclidean")
hclusterAirline = hclust(hDistances, method="ward.D")
plot(hclusterAirline)
hClusterGroup = cutree(hclusterAirline, k=5)
table(hClusterGroup)
tapply(airlines$Balance, hClusterGroup, mean)
tapply(airlines$QualMiles, hClusterGroup, mean)
tapply(airlines$BonusMiles, hClusterGroup, mean)
tapply(airlines$BonusTrans, hClusterGroup, mean)
tapply(airlines$FlightMiles, hClusterGroup, mean)
tapply(airlines$FlightTrans, hClusterGroup, mean)
tapply(airlines$DaysSinceEnroll, hClusterGroup, mean)

which.max(tapply(airlines$Balance, hClusterGroup, mean))
which.max(tapply(airlines$QualMiles, hClusterGroup, mean))
which.max(tapply(airlines$BonusMiles, hClusterGroup, mean))
which.max(tapply(airlines$BonusTrans, hClusterGroup, mean))
which.max(tapply(airlines$FlightMiles, hClusterGroup, mean))
which.max(tapply(airlines$FlightTrans, hClusterGroup, mean))
which.max(tapply(airlines$DaysSinceEnroll, hClusterGroup, mean))

lapply(split(airlinesNorm, hClusterGroup), colMeans)

# K-Means Cluster
set.seed(88)
kmCluster = kmeans(airlinesNorm, centers=5, iter.max=1000)
str(kmCluster)
table(kmCluster$cluster)