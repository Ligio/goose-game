package com.lijoi.marco.goosegame.commands;

/*
 * Copyright (c) 2018 Marco Lijoi
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.lijoi.marco.goosegame.PlayerWithPosition;
import com.lijoi.marco.goosegame.repository.PlayersRepoInterface;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.inject.Inject;
import java.util.Map;

@ShellComponent
public class MovePlayerCommand {
    private final PlayersRepoInterface playersRepo;

    @Inject
    public MovePlayerCommand(PlayersRepoInterface playersRepo) {
        this.playersRepo = playersRepo;
    }

    @ShellMethod(key = "move", value = "move player to another position")
    public String movePlayer(String playerName, String diceValueString, @ShellOption(defaultValue = "") String otherDiceValueString) {
        String[] diceValues = concatCliArgumentsToExtractValues(diceValueString, otherDiceValueString);

        int diceValue = convertToInt(diceValues[0]);
        int otherDiceValue = convertToInt(diceValues[1]);

        Preconditions.checkArgument(playersRepo.isAlreadyPlaying(playerName), String.format("%s is not playing!", playerName));
        Preconditions.checkArgument(areDiceValuesGreatherThanZero(diceValue, otherDiceValue), "dice values should be > 0");

        // I know player exists because passed Preconditions
        PlayerWithPosition playerAfterMove = playersRepo.move(playerName, diceValue, otherDiceValue);
        playersRepo.saveNewPosition(playerAfterMove);

        Map<String, String> valuesMap = ImmutableMap.of(
                "playerName", playerName,
                "dice1", String.valueOf(diceValue),
                "dice2", String.valueOf(otherDiceValue),
                "previousPosition", playerAfterMove.getPreviousPositionName(),
                "nextPosition", playerAfterMove.getCurrentPositionName()
        );

        String templateString = "${playerName} rolls ${dice1}, ${dice2}. ${playerName} moves from ${previousPosition} to ${nextPosition}";
        StringSubstitutor sub = new StringSubstitutor(valuesMap);

        return sub.replace(templateString);
    }

    private int convertToInt(String diceValue) {
        return Integer.parseInt(diceValue.replaceAll("[^0-9]+", ""));
    }

    private String[] concatCliArgumentsToExtractValues(String diceValueString, String otherDiceValueString) {
        return (diceValueString + otherDiceValueString).split(",");
    }

    private boolean areDiceValuesGreatherThanZero(int diceValue, int otherDiceValue) {
        return diceValue > 0 && otherDiceValue > 0;
    }
}
