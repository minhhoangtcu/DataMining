# Load required dataset
install.packages("igraph")
library(igraph)

# Load dataset
setwd("D:/Computer Science/R/Scripts")
edges = read.csv("edges.csv")
users = read.csv("users.csv")

# Inspect the average number of friends for each person
freq = as.data.frame(table(edges$V1))
mean(freq$Freq)
table(users$locale, users$gender)

# Create network map
g = graph.data.frame(edges, FALSE, users)
plot(g, vertex.size=5, vertex.label=NA)
sort(degree(g))
V(g)$size = degree(g)/2+2
plot(g, vertex.label=NA)
sort(V(g)$size)
V(g)$color = "black"
V(g)$color[V(g)$locale == "A"] = "red"
V(g)$color[V(g)$locale == "B"] = "gray"
