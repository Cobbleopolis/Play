import PlayKeys._

name := "Play"

version := "1.0"

lazy val `play` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
	jdbc,
	cache,
	ws,
	"mysql" % "mysql-connector-java" % "5.1.18",
	"com.typesafe.play" %% "anorm" % "2.4.0",
	"ws.securesocial" % "securesocial_2.11" % "3.0-M4"
)

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")

