package com.lijoi.marco.goosegame.formatters;

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

import com.google.common.collect.ImmutableMap;
import com.lijoi.marco.goosegame.PlayerWithPosition;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.lijoi.marco.goosegame.PlayerWithPosition.END_POSITION_INDEX;

@Component
public class MoveReplyFormatter {

    public String reply(PlayerWithPosition player, int firstDiceValue, int secondDiceValue) {
        Map<String, String> valuesMap = ImmutableMap.<String, String>builder()
                .put("playerName", player.getPlayerName())
                .put("dice1", String.valueOf(firstDiceValue))
                .put("dice2", String.valueOf(secondDiceValue))
                .put("previousPosition", player.getPreviousPositionName())
                .put("nextPosition", player.getCurrentPositionName())
                .put("endPosition", String.valueOf(END_POSITION_INDEX))
                .build();

        String templateString = "${playerName} rolls ${dice1}, ${dice2}. ${playerName} moves from ${previousPosition} to ${nextPosition}";

        // This is not the best way to do it! It'd be better to use the Strategy pattern here... I did it in that way for simplicity
        if (player.getCurrentPosition() == END_POSITION_INDEX) {
            templateString += String.format(". %s Wins!!", player.getPlayerName());
        }

        if (player.getCurrentPosition() < player.getPreviousPosition() + firstDiceValue + secondDiceValue) {
            templateString = "${playerName} rolls ${dice1}, ${dice2}. ${playerName} moves from ${previousPosition} to ${endPosition}. "
                    + "${playerName} bounces! ${playerName} returns to ${nextPosition}";
        }

        StringSubstitutor sub = new StringSubstitutor(valuesMap);
        return sub.replace(templateString);
    }
}
