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


class PlayersRepoSpec extends Specification {

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
      repo.players = ["partecipant"]

    expect:
      repo.getPlayers() == ["partecipant"]
  }

  def "save a new partecipant"() {
    given:
      repo.players = []

    when:
      repo.save("Pippo")

    then:
      repo.getPlayers() == ["Pippo"]
  }

  def "save a empty or null player"() {
    given:
      repo.players = []

    when: "trying to save a null player"
      repo.save(null)
    then:
      repo.getPlayers() == []

    when: "trying to save an empty player"
      repo.save("")
    then:
      repo.getPlayers() == []
  }

  def "player is already playing check"() {
    given:
      repo.players = ["Pippo"]

    expect:
      repo.isAlreadyPlaying("Pippo")

    and:
      !repo.isAlreadyPlaying("Pluto")
  }
}
