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
	"jp.t2v" %% "play2-auth"        % "0.14.1",
	"jp.t2v" %% "play2-auth-social" % "0.14.1", // for social login
	"jp.t2v" %% "play2-auth-test"   % "0.14.1" % "test"
)

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")

