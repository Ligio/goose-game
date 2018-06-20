package com.lijoi.marco.goosegame

import spock.lang.Specification
import spock.lang.Subject

/*
 * Copyright 2018 - Schibsted.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
