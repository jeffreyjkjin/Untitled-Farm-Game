# Untitled Farm Game

## Build
After cloning the project folder, navigate to its base directory in the terminal. Then, enter
```
mvn clean compile assembly:single
```
to compile the game.

## Play
Once compiled, the game may be run by entering
```
java -cp target/project-1.0-SNAPSHOT-jar-with-dependencies.jar app.App
```
in the terminal while in the project's base directory.

## Test
To test our game, navigate to the source directory of the game in the terminal. Then, enter
```
mvn test
```
in order to run the games JUnit tests.