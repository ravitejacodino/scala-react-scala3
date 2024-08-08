import org.scalajs.dom
import org.scalajs.dom.document
import slinky.core.facade.ReactElement
import slinky.web.ReactDOM

object Main {
  def main(args: Array[String]): Unit = {
    ReactDOM.render(TextEditor().asInstanceOf[ReactElement], document.getElementById("root"))
  }
}
