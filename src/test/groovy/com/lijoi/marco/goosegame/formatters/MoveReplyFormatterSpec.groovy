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

package com.lijoi.marco.goosegame.formatters

import com.lijoi.marco.goosegame.PlayerWithPosition
import spock.lang.Specification
import spock.lang.Subject

class MoveReplyFormatterSpec extends Specification {

  @Subject
  def formatter = new MoveReplyFormatter()

  def "give details on player move"() {
    given:
      def playerAtStart = new PlayerWithPosition("Pippo", 6, 0)
      def anotherPlayer = new PlayerWithPosition("Pippo", 16, 12)

    expect:
      formatter.reply(playerAtStart, 4, 2) == "Pippo rolls 4, 2. Pippo moves from Start to 6"

    and:
      formatter.reply(anotherPlayer, 3, 1) == "Pippo rolls 3, 1. Pippo moves from 12 to 16"
  }

  def "give details when player win"() {
    given:
      def player = new PlayerWithPosition("Pippo", 63, 60)

    expect:
      formatter.reply(player, 1, 2) == "Pippo rolls 1, 2. Pippo moves from 60 to 63. Pippo Wins!!"
  }

  def "give details when player bounce"() {
    given:
      def player = new PlayerWithPosition("Pippo", 61, 60)

    expect:
      formatter.reply(player, 3, 2) == "Pippo rolls 3, 2. Pippo moves from 60 to 63. Pippo bounces! Pippo returns to 61"
  }

}
