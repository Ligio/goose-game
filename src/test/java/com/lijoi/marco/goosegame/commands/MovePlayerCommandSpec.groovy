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

package com.lijoi.marco.goosegame.commands

import com.lijoi.marco.goosegame.PlayerWithPosition
import com.lijoi.marco.goosegame.repository.PlayersRepoInterface
import spock.lang.Specification
import spock.lang.Subject

class MovePlayerCommandSpec extends Specification {
  PlayersRepoInterface playersRepo = Mock()

  @Subject
  def command = new MovePlayerCommand(playersRepo)

  def "Move player when rolling dices"() {
    given:
      playersRepo.isAlreadyPlaying("Pippo") >> true
      playersRepo.move("Pippo", 4, 2) >> new PlayerWithPosition("Pippo", 6, 0)
      playersRepo.move("Pippo", 2, 3) >> new PlayerWithPosition("Pippo", 11, 6)

    when: "the user writes: \"move Pippo 4, 2\""
      def response = command.movePlayer("Pippo", "4,", "2")
    then: "the system responds: \"Pippo rolls 4, 2. Pippo moves from Start to 6\""
      response == "Pippo rolls 4, 2. Pippo moves from Start to 6"

    when:
      response = command.movePlayer("Pippo", "2,", "3")
    then:
      response == "Pippo rolls 2, 3. Pippo moves from 6 to 11"
  }

  def "Player wins when lands on 63 space"() {
    given:
      playersRepo.isAlreadyPlaying("Pippo") >> true
      playersRepo.move("Pippo", 1, 2) >> new PlayerWithPosition("Pippo", 63, 60)

    when:
      def response = command.movePlayer("Pippo", "1,", "2")
    then:
      response == "Pippo rolls 1, 2. Pippo moves from 60 to 63. Pippo Wins!!"
  }
}
