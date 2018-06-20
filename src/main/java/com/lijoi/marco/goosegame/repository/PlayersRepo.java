package com.lijoi.marco.goosegame.repository;

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

import com.google.common.collect.ImmutableList;
import com.lijoi.marco.goosegame.PlayerWithPosition;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PlayersRepo implements PlayersRepoInterface {

    // Map of playerName, position
    private List<PlayerWithPosition> players = new ArrayList<>();

    @Override
    public boolean isEmpty() {
        return players.isEmpty();
    }

    @Override
    public List<PlayerWithPosition> getPlayers() {
        return ImmutableList.copyOf(players);
    }

    @Override
    public void registerNewPlayer(String playerName) {
        if (!StringUtils.isEmpty(playerName)) {
            players.add(PlayerWithPosition.startPlaying(playerName));
        }
    }

    @Override
    public boolean isAlreadyPlaying(String playerName) {
        return players.stream()
                .anyMatch(playerWithPosition -> playerWithPosition.getPlayerName().equals(playerName));
    }

    private Optional<PlayerWithPosition> findByName(String playerName) {
        return players.stream()
                .filter(player -> player.getPlayerName().equals(playerName))
                .findAny();
    }

    @Override
    public PlayerWithPosition move(String playerName, int diceValue, int otherDiceValue) {
        PlayerWithPosition player = findByName(playerName)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Player %s not found!", playerName)));

        return new PlayerWithPosition(
                player.getPlayerName(),
                computeNextPosition(player, diceValue, otherDiceValue),
                player.getCurrentPosition()  // this will be the old position
        );
    }

    @Override
    public void saveNewPosition(PlayerWithPosition playerWithNewPosition) {
        findByName(playerWithNewPosition.getPlayerName()).ifPresent(
                playerWithOldPosition -> updatePlayerPosition(playerWithNewPosition, playerWithOldPosition)
        );
    }

    private PlayerWithPosition updatePlayerPosition(PlayerWithPosition playerWithNewPosition, PlayerWithPosition playerWithOldPosition) {
        return players.set(players.indexOf(playerWithOldPosition), playerWithNewPosition);
    }

    private int computeNextPosition(PlayerWithPosition player, int diceValue, int otherDiceValue) {
        return player.getCurrentPosition() + diceValue + otherDiceValue;
    }
}
