
name := "Play"

version := "1.0"

lazy val `play` = (project in file(".")).enablePlugins(PlayScala, DockerPlugin, DockerComposePlugin)

scalaVersion := "2.11.7"

val defaultDBDumpPath = baseDirectory( _ /"db"/"dump.sql" )


libraryDependencies ++= Seq(
	jdbc,
	cache,
	ws,
    anorm,
	"mysql" % "mysql-connector-java" % "5.1.18",
    "com.typesafe.play" %% "play-mailer" % "2.4.1",
	"org.mindrot" % "jbcrypt" % "0.3m"
)

//dockerfile in docker := {
//	// The assembly task generates a fat JAR file
//	val artifact: File = assembly.value
//	val artifactTargetPath = s"/app/${artifact.name}"
//
//	new Dockerfile {
//		from("mysql")
//		add(artifact, artifactTargetPath)
//		copyRaw(defaultDBDumpPath.toString, "/app/dump.sql")
//		env("MYSQL_ROOT_PASSWORD" -> "leblanc")
//		run("mysql --user=root --password=$MYSQL_ROOT_PASSWORD < /app/dump.sql")
//		run("apt-get install -y oracle-java8-installer")
//		entryPoint("java", "-jar", artifactTargetPath)
//	}
//}

//dockerfile in docker := {
//	val jarFile: File = sbt.Keys.`package`.in(Compile, packageBin).value
//	val classpath = (managedClasspath in Compile).value
//	val mainclass = mainClass.in(Compile, packageBin).value.getOrElse(sys.error("Expected exactly one main class"))
//	val jarTarget = s"/app/${jarFile.getName}"
//	// Make a colon separated classpath with the JAR file
//	val classpathString = classpath.files.map("/app/" + _.getName)
//		.mkString(":") + ":" + jarTarget
//	new Dockerfile {
//		// Base image
//		from("mysql")
////		add(file("db/dump.sql"), file("/home/dump.sql"))
//		env("MYSQL_ROOT_PASSWORD" -> "leblanc")
//		env("JAVA_DEBIAN_VERSION" -> "7u95-2.6.4-1~deb8u1")
////		run("mysql -u root -p $MYSQL_ROOT_PASSWORD < /home/dump.sql")
//		run("apt-get install -y openjdk-7-jre-headless=\"$JAVA_DEBIAN_VERSION\"")
//		// Add all files on the classpath
//		add(classpath.files, "/app/")
//		// Add the JAR file
//		add(jarFile, jarTarget)
//		// On launch run Java with the classpath and the main class
//		entryPoint("java", "-cp", classpathString, mainclass)
//	}
//}

dockerfile in docker := {
	val jarFile: File = sbt.Keys.`package`.in(Compile, packageBin).value
	val classpath = (managedClasspath in Compile).value
	val mainclass = mainClass.in(Compile, packageBin).value.getOrElse(sys.error("Expected exactly one main class"))
	val jarTarget = s"/app/${jarFile.getName}"
	// Make a colon separated classpath with the JAR file
	val classpathString = classpath.files.map("/app/" + _.getName)
		.mkString(":") + ":" + jarTarget
	new Dockerfile {
		// Base image
		from("java")
		// Add all files on the classpath
		add(classpath.files, "/app/")
		// Add the JAR file
		add(jarFile, jarTarget)
		// On launch run Java with the classpath and the main class
		entryPoint("java", "-cp", classpathString, mainclass)
	}
}

dockerImageCreationTask := docker.value

//maintainer := "CobbleStone Studios"
//
//// exposing the play ports
//dockerExposedPorts in Docker := Seq(9000, 9443)


unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")

