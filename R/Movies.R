# Load dataset
movies = read.table("dataset/movies.txt", header=FALSE, sep="|", quote ="\"")
str(movies)

# Rename variables
colnames(movies) = c("ID", "Title", "ReleaseDate", "VideoReleaseDate", "IMDB", "Unknown", "Action", "Adventure", "Animation", "Childrens", "Comedy", "Crime", "Documentary", "Drama", "Fantasy", "FilmNoir", "Horror", "Musical", "Mystery", "Romance", "SciFi", "Thriller", "War", "Western")

# Remove unnecessary variables
movies$ID = NULL
movies$ReleaseDate = NULL
movies$VideoReleaseDate = NULL
movies$IMDB = NULL

# Remove duplicates
movies = unique(movies)

# Inspect dataset
table(movies$Comedy)
table(movies$Western)
table(movies$Romance, movies$Drama)

# Compute distances
distances = dist(movies[2:20], method = "euclidean")

# Hierarchical clustering
clusterMovies = hclust(distances, method = "ward.D") 

# Plot the dendrogram
plot(clusterMovies)

# Assign points to clusters
clusterGroups = cutree(clusterMovies, k = 2)
str(clusterGroups)
summary(clusterGroups)
table(clusterGroups)

tapply(movies$Action, clusterGroups, mean)
tapply(movies$Adventure, clusterGroups, mean)
tapply(movies$Animation, clusterGroups, mean)
tapply(movies$Childrens, clusterGroups, mean)
tapply(movies$Comedy, clusterGroups, mean)
tapply(movies$Crime, clusterGroups, mean)
tapply(movies$Documentary, clusterGroups, mean)
tapply(movies$Drama, clusterGroups, mean)
tapply(movies$Fantasy, clusterGroups, mean)
tapply(movies$FilmNoir, clusterGroups, mean)
tapply(movies$Horror, clusterGroups, mean)


