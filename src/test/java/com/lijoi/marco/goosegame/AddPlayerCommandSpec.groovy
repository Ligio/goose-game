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

package com.lijoi.marco.goosegame

import spock.lang.Specification
import spock.lang.Subject

class AddPlayerCommandSpec extends Specification {
  PlayersRepoInterface playersRepo = Mock()

  @Subject
  def command = new AddPlayerCommand(playersRepo)

  def "Add a new player when there is no partecipants"() {
    given: "there is no participant"
      playersRepo.players == []

    when: "the user writes: \"add player Pippo\""
      def response = command.addPlayer("Pippo")

    then: "the system responds: \"players: Pippo\""
      1 * playersRepo.save("Pippo")
      1 * playersRepo.players >> ["Pippo"]
      response == "players: Pippo"
  }

  def "Add a new player"() {
    given: "Pippo is already playing"
      playersRepo.players == ["Pippo"]

    when: "the user writes: \"add player Pluto\""
      def response = command.addPlayer("Pluto")

    then: "the system responds: \"players: Pippo, Pluto\""
      1 * playersRepo.save("Pluto")
      1 * playersRepo.players >> ["Pippo", "Pluto"]
      response == "players: Pippo, Pluto"
  }

  def "Trying to add a new empty player"() {
    when:
      command.addPlayer("")

    then:
      def e = thrown(IllegalArgumentException)
      e.message == "player name must not be empty"
  }
}
