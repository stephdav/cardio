# cardio

## configuration (cardio.properties)

db.driver
db.url
db.username
db.password

If one og these parameters is missing, database is created "in memory".

## starting app

If "cleardb" is present as argument, database is cleared at startup.
If "populatedb" is present, database is cleared at startup and a sample dataset is imported.

## 

java -classpath ".;lib/*" com.sopra.agile.cardio.app.App