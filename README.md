# cardio

## configuration (cardio.properties)

Database settings:
* `db.driver` :
* `db.url` :
* `db.username` :
* `db.password` :

If one of these previous parameters is missing, database is created "in memory".

Other settings:
* `project.name` : project name
* `statistic.sprints.sample` : number of sprints collected to compute statistics

## Install and run application

### installing application

1. Generate application
```sh
mvn clean package -DskipTests
```
A ZIP archive is generated in directory cardio-dist\target.

2. Unzip 'agile.cardio-dist-<version>-bin.zip' file in the directory of your choice
3. Create a 'cardio.properties' file in order to ovveride default configuration.

## running application

### command line

```sh
java -classpath ".;lib/*" com.sopra.agile.cardio.app.App
```

### command line parameters

If "reset" is present as argument, database is cleared at startup.
If "<sql file>" is present, <sql file> is executed at startup.

## changelog

### 0.3
* user stories
* application bundle

### 0.2
* sprint controls during creation and update
* BDD integration tests

### 0.1
* sprints
* sprint burndown
* project burnup
* velocity statistics
