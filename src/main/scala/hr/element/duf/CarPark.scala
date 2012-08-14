package hr.element.duf

import core._
import java.awt.Color
import java.awt.Graphics2D
import java.awt.geom.Ellipse2D.{ Double => Ellipse }
import java.awt.geom.Line2D.{ Double => Line }

import scala.math._

object CarPark extends CarPark(IndexedSeq(
  Fahren(
    name  = "Red"
  , color = Color.RED.getRGB()
  , pos   = Point(0.5, 0.5)
  , or    = Orientation(0.0)
  , vel   = Velocity(Orientation(0.0), 100)
  )
, Fahren(
    name  = "Green"
  , color = Color.GREEN.getRGB()
  , pos   = Point(0.3, 0.3)
  , or    = Orientation(471.0)
  , vel   = Velocity(Orientation(471.0), 150)
  )
))

case class CarPark(var cars: IndexedSeq[Fahren]) {
  def move(name: String, dir: String) = {
    CarPark.synchronized {
      cars = cars.map{ c =>
        if (c.name == name) {
          println("MICEM: " + c + " za " + dir)

          dir match {
            case "right" => c.right()
            case "left" => c.left()
            case "acc" => c.accelerate()
            case "brake" => c.brake()
            case _ => sys.error("OH NOES!")
          }
        } else {
          c
        }
      }
    }
  }

  def draw(g: Graphics2D, dims: Dim) {
    CarPark.synchronized {
      cars.foreach(Drawing.draw(g, _, dims))
    }
  }

  def moveIt() {
    CarPark.synchronized {
      cars = cars.map(c => Dirigent.process(c))
    }
  }
}

object Drawing {
  def draw(g: Graphics2D, car: Fahren, dims: Dim) {
    val body = new Ellipse(
      car.pos.x * dims.w - 5
    , car.pos.y * dims.h - 5
    , 10.0
    , 10.0)

    g.setColor(new Color(car.color))
    g.fill(body)

    g.setColor(new Color(car.color).brighter())

    {
      val cursor = new Line(
        car.pos.x * dims.w
      , car.pos.y * dims.h
      , car.pos.x * dims.w + car.vel.speed * sin(car.or.fi / 628)
      , car.pos.y * dims.h + car.vel.speed * cos(car.or.fi / 628)
      )

      g.draw(cursor)
    }
    {
      val cursor = new Line(
        car.pos.x * dims.w + 5
      , car.pos.y * dims.h
      , car.pos.x * dims.w + car.vel.speed * sin(car.or.fi / 628)
      , car.pos.y * dims.h + car.vel.speed * cos(car.or.fi / 628)
      )

      g.draw(cursor)
    }
    {
      val cursor = new Line(
        car.pos.x * dims.w
      , car.pos.y * dims.h + 5
      , car.pos.x * dims.w + car.vel.speed * sin(car.or.fi / 628)
      , car.pos.y * dims.h + car.vel.speed * cos(car.or.fi / 628)
      )

      g.draw(cursor)
    }
    {
      val cursor = new Line(
        car.pos.x * dims.w - 5
      , car.pos.y * dims.h
      , car.pos.x * dims.w + car.vel.speed * sin(car.or.fi / 628)
      , car.pos.y * dims.h + car.vel.speed * cos(car.or.fi / 628)
      )

      g.draw(cursor)
    }
    {
      val cursor = new Line(
        car.pos.x * dims.w
      , car.pos.y * dims.h - 5
      , car.pos.x * dims.w + car.vel.speed * sin(car.or.fi / 628)
      , car.pos.y * dims.h + car.vel.speed * cos(car.or.fi / 628)
      )

      g.draw(cursor)
    }
  }
}