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

## running application

### command line

```sh
java -classpath ".;lib/*" com.sopra.agile.cardio.app.App
```

### command line parameters

If "cleardb" is present as argument, database is cleared at startup.
If "populatedb" is present, database is cleared at startup and a sample dataset is imported.

## changelog

### 0.2
* sprint controls during creation and update
* BDD integration tests

### 0.1
* sprints
* sprint burndown
* project burnup
* velocity statistics
