cd %~dp0

echo Starting cardio...
start "cardioApp" java -classpath "lib/*" com.sopra.agile.cardio.app.App
