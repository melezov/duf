package hr.element.duf.core

case class Dim(w: Double, h: Double)

case class Point(x: Double, y: Double)

case class Orientation(fi: Double)

case class Velocity(fi: Orientation, speed: Double)
