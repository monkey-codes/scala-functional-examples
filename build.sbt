import Dependencies._

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.11.8",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "scala-functional-examples",
    //libraryDependencies += scalaTest % Test,
    libraryDependencies ++= Seq(
	  scalaTest % Test,
	  "junit" % "junit" % "4.10" % "test",
    "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4"
	)
  )
