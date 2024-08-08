//import scala.scalajs.js
//import scala.scalajs.js.annotation._
//import org.scalajs.dom
//import slinky.core._
//import slinky.core.annotations.react
//import slinky.web.html._
//import slinky.web.ReactDOM
//import slinky.core.FunctionalComponent
//
//
//@react class TextEditor extends Component {
//  type Props = Unit
//  type State = (String, Option[dom.Window]) // State now holds (editor content, reference to the preview window)
//
//  private var intervalId: js.UndefOr[js.timers.SetIntervalHandle] = js.undefined
//
//  def initialState: State = ("", None) // Initialize with empty content and no preview window
//
//  override def componentDidMount(): Unit = {
//    intervalId = js.timers.setInterval(3000)(refreshPreview()) // Set interval to refresh every 3 seconds
//  }
//
//  override def componentWillUnmount(): Unit = {
//    intervalId.foreach(js.timers.clearInterval) // Clear the interval when the component is unmounted
//  }
//
//  def render() = {
//    div(className := "container")(
//      main(className := "editor-area")(
//        div(className := "toolbar")(
//          button(onClick := (_ => execCommand("undo")))("Undo"),
//          button(onClick := (_ => execCommand("redo")))("Redo"),
//          button(onClick := (_ => execCommand("bold")))("Bold"),
//          button(onClick := (_ => execCommand("italic")))("Italic"),
//          button(onClick := (_ => execCommand("underline")))("Underline"),
//          button(onClick := (_ => execCommand("insertOrderedList")))("Ordered List"),
//          button(onClick := (_ => execCommand("insertUnorderedList")))("Unordered List"),
//          button(onClick := (_ => openPreviewInNewWindow()))("Open Preview in New Window")
//        ),
//        div(className := "aligner")(
//          div(
//            className := "editor",
//            contentEditable := true,
//            onInput := (e => handleInput(e)),
//            style := js.Dynamic.literal(
//              height = "100vh" // Full height for the editor
//            )
//          ),
//          div(className := "preview")(
//            h3("Preview"),
//            div(className := "preview-content", dangerouslySetInnerHTML := new js.Object {
//              val __html = state._1
//            })
//          )
//        )
//      )
//    )
//  }
//
//  def execCommand(command: String, value: String = ""): Unit = {
//    dom.document.execCommand(command, false, value)
//  }
//
//  def handleInput(e: SyntheticEvent[dom.html.Div, org.scalajs.dom.Event]): Unit = {
//    setState((e.target.asInstanceOf[dom.html.Div].innerHTML, state._2)) // Update content while keeping the reference to the preview window
//  }
//
//  def openPreviewInNewWindow(): Unit = {
//    val previewWindow = dom.window.open("", "_blank", "width=800,height=600") // Open a new window
//    previewWindow.document.write(
//      s"""
//         |<!DOCTYPE html>
//         |<html lang="en">
//         |<head>
//         |    <meta charset="UTF-8">
//         |    <meta name="viewport" content="width=device-width, initial-scale=1.0">
//         |    <title>Preview</title>
//         |    <style>
//         |        body { font-family: Arial, sans-serif; padding: 20px; }
//         |        .preview-content { border: 1px solid #ddd; padding: 10px; background-color: #f9f9f9; min-height: 500px; width: 100%; }
//         |    </style>
//         |</head>
//         |<body>
//         |    <div class="preview-content">${state._1}</div> <!-- Use the current state for the preview -->
//         |    <script>
//         |        window.addEventListener('message', function(event) {
//         |            document.querySelector('.preview-content').innerHTML = event.data; // Update preview content
//         |        });
//         |    </script>
//         |</body>
//         |</html>
//         |""".stripMargin
//    )
//    previewWindow.document.close()
//
//    // Store the reference to the preview window
//    setState((state._1, Some(previewWindow))) // Set the preview window reference
//  }
//
//  def refreshPreview(): Unit = {
//    state._2.foreach { window =>
//      window.postMessage(state._1, "*") // Send the current editor content to the preview window
//    }
//  }
//}
import scala.scalajs.js
import scala.scalajs.js.annotation._
import org.scalajs.dom
import slinky.core._
import slinky.core.facade.ReactElement
import slinky.web.html._
import slinky.core.FunctionalComponent

class TextEditor extends Component {
  case class Props()
  case class State(content: String, previewWindow: Option[dom.Window])

  private var intervalId: js.UndefOr[js.timers.SetIntervalHandle] = js.undefined

  def initialState: State = State("", None)

  override def componentDidMount(): Unit = {
    intervalId = js.timers.setInterval(3000)(refreshPreview()) // Set interval to refresh every 3 seconds
  }

  override def componentWillUnmount(): Unit = {
    intervalId.foreach(js.timers.clearInterval) // Clear the interval when the component is unmounted
  }

  def render(): ReactElement = {
    div(className := "container")(
      main(className := "editor-area")(
        div(className := "toolbar")(
          button(onClick := (_ => execCommand("undo")))("Undo"),
          button(onClick := (_ => execCommand("redo")))("Redo"),
          button(onClick := (_ => execCommand("bold")))("Bold"),
          button(onClick := (_ => execCommand("italic")))("Italic"),
          button(onClick := (_ => execCommand("underline")))("Underline"),
          button(onClick := (_ => execCommand("insertOrderedList")))("Ordered List"),
          button(onClick := (_ => execCommand("insertUnorderedList")))("Unordered List"),
          button(onClick := (_ => openPreviewInNewWindow()))("Open Preview in New Window")
        ),
        div(className := "aligner")(
          div(
            className := "editor",
            contentEditable := true,
            onInput := (e => handleInput(e)),
            style := js.Dynamic.literal(
              height = "100vh" // Full height for the editor
            )
          ),
          div(className := "preview")(
            h3("Preview"),
            div(className := "preview-content", dangerouslySetInnerHTML := new js.Object {
              val __html = state.content
            })
          )
        )
      )
    )
  }

  def execCommand(command: String, value: String = ""): Unit = {
    dom.document.execCommand(command, false, value)
  }

  def handleInput(e: SyntheticEvent[dom.html.Div, org.scalajs.dom.Event]): Unit = {
    setState(State(e.target.asInstanceOf[dom.html.Div].innerHTML, state.previewWindow))
  }

  def openPreviewInNewWindow(): Unit = {
    val previewWindow = dom.window.open("", "_blank", "width=800,height=600") // Open a new window
    previewWindow.document.write(
      s"""
         |<!DOCTYPE html>
         |<html lang="en">
         |<head>
         |    <meta charset="UTF-8">
         |    <meta name="viewport" content="width=device-width, initial-scale=1.0">
         |    <title>Preview</title>
         |    <style>
         |        body { font-family: Arial, sans-serif; padding: 20px; }
         |        .preview-content { border: 1px solid #ddd; padding: 10px; background-color: #f9f9f9; min-height: 500px; width: 100%; }
         |    </style>
         |</head>
         |<body>
         |    <div class="preview-content">${state.content}</div> <!-- Use the current state for the preview -->
         |    <script>
         |        window.addEventListener('message', function(event) {
         |            document.querySelector('.preview-content').innerHTML = event.data; // Update preview content
         |        });
         |    </script>
         |</body>
         |</html>
         |""".stripMargin
    )
    previewWindow.document.close()

    // Store the reference to the preview window
    setState(State(state.content, Some(previewWindow)))
  }

  def refreshPreview(): Unit = {
    state.previewWindow.foreach { window =>
      window.postMessage(state.content, "*") // Send the current editor content to the preview window
    }
  }
}
