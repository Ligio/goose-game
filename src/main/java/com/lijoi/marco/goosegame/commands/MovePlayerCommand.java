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
import com.lijoi.marco.goosegame.DiceValueGenerator;
import com.lijoi.marco.goosegame.PlayerWithPosition;
import com.lijoi.marco.goosegame.formatters.MoveReplyFormatter;
import com.lijoi.marco.goosegame.repository.PlayersRepoInterface;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.util.StringUtils;

import javax.inject.Inject;

@ShellComponent
public class MovePlayerCommand {
    private final PlayersRepoInterface playersRepo;
    private final MoveReplyFormatter moveReplyFormatter;
    private final DiceValueGenerator diceValueGenerator;

    @Inject
    public MovePlayerCommand(PlayersRepoInterface playersRepo, MoveReplyFormatter moveReplyFormatter, DiceValueGenerator diceValueGenerator) {
        this.playersRepo = playersRepo;
        this.moveReplyFormatter = moveReplyFormatter;
        this.diceValueGenerator = diceValueGenerator;
    }

    @ShellMethod(key = "move", value = "move player to another position")
    public String movePlayer(String playerName,
                             @ShellOption(defaultValue = "") String diceValueString,
                             @ShellOption(defaultValue = "") String otherDiceValueString) {

        String[] diceValues = concatCliArgumentsToExtractValues(diceValueString, otherDiceValueString);

        int diceValue = convertToInt(diceValues[0]);
        int otherDiceValue = convertToInt(diceValues[1]);

        Preconditions.checkArgument(playersRepo.isAlreadyPlaying(playerName), String.format("%s is not playing!", playerName));
        Preconditions.checkArgument(areDiceValuesGreatherThanZero(diceValue, otherDiceValue), "dice values should be > 0");

        // I know player exists because passed Preconditions
        PlayerWithPosition playerAfterMove = playersRepo.move(playerName, diceValue, otherDiceValue);
        playersRepo.saveNewPosition(playerAfterMove);

        return moveReplyFormatter.reply(playerAfterMove, diceValue, otherDiceValue);
    }

    private int convertToInt(String diceValue) {
        return Integer.parseInt(diceValue.replaceAll("[^0-9]+", ""));
    }

    private String[] concatCliArgumentsToExtractValues(String diceValueString, String otherDiceValueString) {

        if (StringUtils.isEmpty(diceValueString.trim())) {
            return randomDiceValues();
        }

        return (diceValueString + otherDiceValueString).split(",");
    }

    private String[] randomDiceValues() {
        return new String[] {
                String.valueOf(diceValueGenerator.throwsTheDice()),
                String.valueOf(diceValueGenerator.throwsTheDice())
        };
    }

    private boolean areDiceValuesGreatherThanZero(int diceValue, int otherDiceValue) {
        return diceValue > 0 && otherDiceValue > 0;
    }
}
