package hr.element.duf

import java.awt.Toolkit
import scala.actors.Actor.actor
import scala.swing.Component
import scala.swing.Graphics2D
import scala.swing.MainFrame
import scala.swing.SimpleSwingApplication
import scala.swing.event.UIElementResized
import core._
import javax.swing.JDialog
import javax.swing.JFrame
import java.awt.Color
import scala.swing.event.KeyPressed
import scala.swing.event.Key

object MainGUI extends SimpleSwingApplication {
  private def setLookAndFeel(dlf: Boolean) {
    Toolkit.getDefaultToolkit.setDynamicLayout(dlf)
    System.setProperty("sun.awt.noerasebackground", dlf.toString)

    JFrame.setDefaultLookAndFeelDecorated(dlf)
    JDialog.setDefaultLookAndFeelDecorated(dlf)
  }

  var running = true

  override def startup(args: Array[String]) {
    setLookAndFeel(true)
    top.visible = true

    actor {
      while (running) {
        try {
          CarPark.moveIt()
          vista.repaint()
          Thread.sleep(30)
        }
        catch {
          case e: InterruptedException =>
        }
      }
    }
  }

  val vista = new Component {
    listenTo(this.keys)

    reactions += {
      case KeyPressed(source, key, modifiers, location) =>
        key match {
          case Key.Right =>
            CarPark.move("Red", "right")

          case Key.Left =>
            CarPark.move("Red", "left")

          case Key.Up =>
            CarPark.move("Red", "acc")

          case Key.Down =>
            CarPark.move("Red", "brake")
        }
    }

    focusable = true
    requestFocus

    override def paint(g: Graphics2D) {
      super.paint(g)

      g.setColor(Color.BLACK)
      g.fillRect(0, 0, size.width, size.height)

      val dims = Dim(size.width, size.height)

      CarPark.draw(g, dims)
    }
  }

  def top = new MainFrame {
    title = "Das Uber Fahren - 0.0 alpha"
    contents = vista
  }

  override def shutdown() {
    running = false
  }
}