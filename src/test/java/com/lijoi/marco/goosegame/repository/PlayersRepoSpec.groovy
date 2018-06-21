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

package com.lijoi.marco.goosegame.repository

import com.lijoi.marco.goosegame.PlayerWithPosition
import spock.lang.Specification
import spock.lang.Subject

class PlayersRepoSpec extends Specification {
  PlayerWithPosition genericPlayerWithPosition = Mock()

  @Subject
  def repo = new PlayersRepo()

  def "no partecipants registered"() {
    given:
      repo.players = []

    expect:
      repo.isEmpty()

    and:
      repo.getPlayers() == []
  }

  def "get partecipants list"() {
    given:
      repo.players = [genericPlayerWithPosition]

    expect:
      repo.getPlayers() == [genericPlayerWithPosition]
  }

  def "register a new partecipant"() {
    given:
      repo.players = []

    when:
      repo.registerNewPlayer("Pippo")

    then:
      repo.getPlayers() == [PlayerWithPosition.startPlaying("Pippo")]
  }

  def "register an empty or null player"() {
    given:
      repo.players = []

    when: "trying to save a null player"
      repo.registerNewPlayer(null)
    then:
      repo.getPlayers() == []

    when: "trying to save an empty player"
      repo.registerNewPlayer("")
    then:
      repo.getPlayers() == []
  }

  def "player is already playing"() {
    given:
      repo.players = [new PlayerWithPosition("Pippo", 3, 2)]

    expect:
      repo.isAlreadyPlaying("Pippo")

    and:
      !repo.isAlreadyPlaying("Pluto")
  }

  def "move player"() {
    given:
      def startingPlayer = PlayerWithPosition.startPlaying("Pluto")
      def player = new PlayerWithPosition("Pippo", 3, 2)
      repo.players = [player, startingPlayer]

    expect:
      repo.move("Pippo", 4, 2) == new PlayerWithPosition("Pippo", 9, 3)

    and:
      repo.move("Pluto", 4, 6) == new PlayerWithPosition("Pluto", 10, 0)
  }

  def "bounce player"() {
    given:
      def player = new PlayerWithPosition("Pippo", 60, 57)
      repo.players = [player]

    expect:
      repo.move("Pippo", 3, 2) == new PlayerWithPosition("Pippo", 61, 60)
  }

  def "update player position"() {
    given:
      def player = new PlayerWithPosition("Pippo", 3, 2)
      repo.players = [player]

    when:
      repo.saveNewPosition(new PlayerWithPosition("Pippo", 5, 3))

    then:
      repo.findByName("Pippo").get().currentPosition == 5
  }
}
