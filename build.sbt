import PlayKeys._

name := "Play"

version := "1.0"

lazy val `play` = (project in file(".")).enablePlugins(PlayScala, DockerPlugin)

scalaVersion := "2.11.7"


libraryDependencies ++= Seq(
	jdbc,
	cache,
	ws,
    anorm,
	"mysql" % "mysql-connector-java" % "5.1.18",
    "com.typesafe.play" %% "play-mailer" % "2.4.1",
	"org.mindrot" % "jbcrypt" % "0.3m"
)

dockerfile in docker := {
	// The assembly task generates a fat JAR file
	val artifact: File = assembly.value
	val artifactTargetPath = s"/app/${artifact.name}"

	new Dockerfile {
		from("java")
		add(artifact, artifactTargetPath)
		entryPoint("java", "-jar", artifactTargetPath)
	}
}

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")

