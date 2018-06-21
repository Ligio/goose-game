#!/bin/bash
# This script will start the game.

if [ "$1" == "test" ]; then
    echo "I'm testing the Goose Game"
    ./gradlew clean test
else
    echo "Building the Goose Game"
    ./gradlew clean bootJar
    java -jar build/libs/goose-game-0.0.1-SNAPSHOT.jar
fi;
