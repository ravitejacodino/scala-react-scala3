import scala.sys.process._

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.0"


lazy val root = (project in file("."))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    name := "scala-react-scala3",
  scalaJSUseMainModuleInitializer := true,
//    scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) },
//    jsEnv := new org.scalajs.jsenv.nodejs.NodeJSEnv(
//      org.scalajs.jsenv.nodejs.NodeJSEnv.Config()
//        .withArgs(List("--experimental-modules"))
//    ),
   scalacOptions += "-Ymacro-annotations",

    libraryDependencies ++= Seq(
      "org.scala-js" % "scalajs-dom_sjs1_3" % "2.8.0",
      "org.scalatest" %% "scalatest" % "3.2.18" % Test,
      "me.shadaj" %% "slinky-core_sjs1" % "0.7.4",
      "me.shadaj" %% "slinky-web_sjs1" % "0.7.4",
//      "com.github.japgolly.scalajs-react" % "core_sjs1_3" % "2.1.2",
//      "com.github.japgolly.scalajs-react" % "core_sjs1_3" % "2.1.2"
//
      )
  )
Compile/ run / mainClass := Some("Main")



