#!/bin/bash
# This script will start the game.

./gradlew clean bootJar
java -jar build/libs/goose-game-0.0.1-SNAPSHOT.jar
