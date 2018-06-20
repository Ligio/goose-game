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

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.lijoi.marco.goosegame.repository.PlayersRepoInterface;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.StringUtils;

import javax.inject.Inject;

@ShellComponent
public class AddPlayerCommand {
    private final PlayersRepoInterface playersRepo;

    @Inject
    public AddPlayerCommand(PlayersRepoInterface playersRepo) {
        this.playersRepo = playersRepo;
    }

    @ShellMethod(key = "add player", value = "add a new player to the game")
    public String addPlayer(String playerName) {
        Preconditions.checkArgument(!StringUtils.isEmpty(playerName), "player name must not be empty");

        if (playersRepo.isAlreadyPlaying(playerName)) {
            return String.format("%s: already existing player", playerName);
        }

        playersRepo.save(playerName);

        return "players: " + Joiner.on(", ")
                .skipNulls()
                .join(playersRepo.getPlayers());
    }
}
