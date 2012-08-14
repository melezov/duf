package hr.element.duf

import core._

import scala.math._

object Dirigent {

  private def wrap(x: Double) =
    x - x.toInt + (if (x < 0) 1 else 0)

  def process(car: Fahren) = {
    car.copy(pos = {
      val x = car.pos.x +
        (car.vel.speed / 100000) * sin(car.vel.fi.fi / 628)

      val y = car.pos.y +
        (car.vel.speed / 100000) * cos(car.vel.fi.fi / 628)

      Point(
        x = wrap(x)
      , y = wrap(y)
      )
    })
  }
}