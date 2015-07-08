# Load dataset
daily = read.csv("dataset/dailykos.csv")
str(daily)

# Hierachical Cluster
distances = dist(daily, method="euclidean")
cluster = hclust(distances, method="ward.D")
plot(cluster)
clusterGroups = cutree(cluster, k=7)
clusterDataset = split(daily, clusterGroups)

tail(sort(colMeans(clusterDataset[[1]])))
tail(sort(colMeans(clusterDataset[[2]])))
tail(sort(colMeans(clusterDataset[[3]])))
tail(sort(colMeans(clusterDataset[[4]])))
tail(sort(colMeans(clusterDataset[[5]])))
tail(sort(colMeans(clusterDataset[[6]])))
tail(sort(colMeans(clusterDataset[[7]])))

# K-Means Cluster
set.seed(1000)
kmCluster = kmeans(daily, centers=7)
str(kmCluster)
table(kmCluster$cluster)
kmclusterDataset = split(daily, kmCluster$cluster)

tail(sort(colMeans(kmclusterDataset[[1]])))
tail(sort(colMeans(kmclusterDataset[[2]])))
tail(sort(colMeans(kmclusterDataset[[3]])))
tail(sort(colMeans(kmclusterDataset[[4]])))
tail(sort(colMeans(kmclusterDataset[[5]])))
tail(sort(colMeans(kmclusterDataset[[6]])))
tail(sort(colMeans(kmclusterDataset[[7]])))

# Compare 2 clusters
table(clusterGroups, kmCluster$cluster)




