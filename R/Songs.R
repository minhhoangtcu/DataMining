# Load dataset and initialize training and testing datasets
song = read.csv("dataset/songs.csv")
songTrain = subset(song, year < 2010)
songTest = subset(song, year == 2010)
nonvars = c("year", "songtitle", "artistname", "songID", "artistID")
songTrain = songTrain[ , !(names(songTrain) %in% nonvars) ]
songTest = songTest[ , !(names(songTest) %in% nonvars) ]

# Inspect dataset
str(song)
str(songTrain)
str(songTest)
summary(song)
table(song$year)
table(song$timesignature)
song$songtitle[which.max(song$tempo)]
cor(songTrain$loudness, songTrain$energy)

# Find songs by Michael Jackson
songByMichaelJackson = subset(song, artistname=="Michael Jackson")
songByMichaelJacksonReachTop10 = subset(song, artistname=="Michael Jackson" & Top10==TRUE)
str(songByMichaelJackson)
songByMichaelJacksonReachTop10$songtitle

# Constructing Linear Reggresion Model
SongsLog1 = glm(Top10 ~ ., data=songTrain, family=binomial)
summary(SongsLog1)
SongsLog2 = glm(Top10 ~ . - loudness, data=songTrain, family=binomial)
summary(SongsLog2)
SongsLog3 = glm(Top10 ~ . - energy, data=songTrain, family=binomial)
summary(SongsLog3)
predictTrain = predict(SongsLog3, newdata=songTest, type="response")
table(songTest$Top10, predictTrain >= 0.45)
(309+19)/((309+19+5+40))
(309+5)/((309+19+5+40))
19/(19+40)
309/(309+5)