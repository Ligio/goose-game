package com.lijoi.marco.goosegame;

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
import lombok.EqualsAndHashCode;
import org.springframework.util.StringUtils;

@EqualsAndHashCode
public class PlayerWithPosition {
    public static final String START_POSITION_NAME = "Start";
    public static final int START_POSITION_INDEX = 0;
    public static final int END_POSITION_INDEX = 63;

    private final String playerName;
    private int previousPosition;
    private int currentPosition;

    public static PlayerWithPosition startPlaying(String playerName) {
        return new PlayerWithPosition(playerName, START_POSITION_INDEX, START_POSITION_INDEX);
    }

    public PlayerWithPosition(String playerName, int currentPosition, int previousPosition) {
        Preconditions.checkArgument(!StringUtils.isEmpty(playerName), "player name must not be empty");
        Preconditions.checkArgument(currentPosition >= 0, "only numbers > 0 are allowed!");
        Preconditions.checkArgument(previousPosition >= 0, "only numbers > 0 are allowed!");

        this.playerName = playerName;
        this.currentPosition = currentPosition;
        this.previousPosition = previousPosition;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPreviousPosition() {
        return previousPosition;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public String getCurrentPositionName() {
        return getPositionName(currentPosition);
    }

    public String getPreviousPositionName() {
        return getPositionName(previousPosition);
    }

    private String getPositionName(int positionIndex) {
        return positionIndex == START_POSITION_INDEX
                ? START_POSITION_NAME
                : String.valueOf(positionIndex);
    }
}
