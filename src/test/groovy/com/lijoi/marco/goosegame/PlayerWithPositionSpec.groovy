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

class PlayerWithPositionSpec extends Specification {

  def "get position"() {
    given:
      @Subject
      def playerWithPosition = new PlayerWithPosition("Pippo", 2, 1)

    expect:
      with(playerWithPosition) {
        playerName == "Pippo"
        currentPosition == 2
        previousPosition == 1
        currentPositionName == "2"
      }
  }

  def "get starting position"() {
    given:
      @Subject
      def playerEnteringGame = PlayerWithPosition.startPlaying("Pippo")

    expect:
      with(playerEnteringGame) {
        playerName == "Pippo"
        currentPosition == 0
        previousPosition == 0
        currentPositionName == "Start"
      }
  }
}
