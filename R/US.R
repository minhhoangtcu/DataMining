CPS = read.csv("dataset/CPSData.csv")
MetroAreaMap = read.csv("dataset/MetroAreaCodes.csv")
CountryMap = read.csv("dataset/CountryCodes.csv")

str(CPS)
CPSstr(MetroAreaMap)
str(CountryMap)

is.element(TRUE, is.na(CPS$PeopleInHousehold))
is.element(TRUE, is.na(CPS$Region))
is.element(TRUE, is.na(CPS$State))
is.element(TRUE, is.na(CPS$MetroAreaCode))
is.element(TRUE, is.na(CPS$Age))
is.element(TRUE, is.na(CPS$Married))
is.element(TRUE, is.na(CPS$Sex))
is.element(TRUE, is.na(CPS$Education))
is.element(TRUE, is.na(CPS$Race))
is.element(TRUE, is.na(CPS$Hispanic))
is.element(TRUE, is.na(CPS$CountryOfBirthCode))
is.element(TRUE, is.na(CPS$Citizenship))
is.element(TRUE, is.na(CPS$EmploymentStatus))
is.element(TRUE, is.na(CPS$Industry))
#MetroArea
#Country

table(CPS$Region, is.na(CPS$Married))
table(CPS$Sex, is.na(CPS$Married))
table(CPS$Age, is.na(CPS$Married))
table(CPS$Citizenship, is.na(CPS$Married))

plot(table(CPS$Region, is.na(CPS$Married)))
plot(table(CPS$Sex, is.na(CPS$Married)))
plot(table(CPS$Age, is.na(CPS$Married)))
plot(table(CPS$Citizenship, is.na(CPS$Married)))

CPS = merge(CPS, CountryMap, by.x="CountryOfBirthCode", by.y="Code", all.x=TRUE)
sort(tapply(CPS$Country != "United States", CPS$MetroArea, mean, na.rm=TRUE))
sort(tapply(CPS$Country == "India", CPS$MetroArea, sum, na.rm=TRUE))
sort(tapply(CPS$Country == "Brazil", CPS$MetroArea, sum, na.rm=TRUE))
sort(tapply(CPS$Country == "Somalia", CPS$MetroArea, sum, na.rm=TRUE))
sort(table(CPS$Country == "India", CPS$MetroArea))
