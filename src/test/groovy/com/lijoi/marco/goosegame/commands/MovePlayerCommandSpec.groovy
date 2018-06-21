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
import com.lijoi.marco.goosegame.formatters.MoveReplyFormatter
import com.lijoi.marco.goosegame.repository.PlayersRepoInterface
import spock.lang.Specification
import spock.lang.Subject

class MovePlayerCommandSpec extends Specification {
  PlayersRepoInterface playersRepo = Mock()
  MoveReplyFormatter moveReplyFormatter = Mock()

  @Subject
  def command = new MovePlayerCommand(playersRepo, moveReplyFormatter)

  def "Move player when rolling dices"() {
    given:
      def playerAfterMove = new PlayerWithPosition("Pippo", 16, 12)
      playersRepo.isAlreadyPlaying("Pippo") >> true
      playersRepo.move("Pippo", 4, 2) >> playerAfterMove
      moveReplyFormatter.reply(playerAfterMove, 4, 2) >> "pippo is moving!"

    when:
      def response = command.movePlayer("Pippo", "4,", "2")
    then:
      response == "pippo is moving!"
  }

}
