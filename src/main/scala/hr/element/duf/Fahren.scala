package hr.element.duf

import core._

import scala.math._

case class Fahren(
    name: String
  , color: Int
  , pos: Point
  , or: Orientation
  , vel: Velocity) {

  def left() = {
    copy(or = or.copy(fi = or.fi + 14))
  }

  def right() = {
    copy(or = or.copy(fi = or.fi - 14))
  }

  def accelerate() = {
    copy(vel = {

      val p1 = Point(
        sin(vel.fi.fi) * vel.speed
      , cos(vel.fi.fi) * vel.speed
      )

      val p2 = Point(
        sin(or.fi) * 100
      , cos(or.fi) * 100
      )

      val x = p1.x + p2.x
      val y = p1.y + p2.y

      val fi = atan(y / x)
      val speed = sqrt(x*x + y*y)

      Velocity(Orientation(fi), speed)
    })
  }

  def brake() = {
    copy(vel = vel.copy(speed = vel.speed - 14))
  }
}
