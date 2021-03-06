organization := "hr.element.duf"

name := "duf"

version := "0.0.0-SNAPSHOT"

crossScalaVersions := Seq("2.10.0-M6")

scalaVersion <<= (crossScalaVersions)(_.head)

scalacOptions := Seq(
  "-unchecked", "-deprecation", "-encoding", "UTF-8", "-optimise" // , "-Xno-uescape"
, "-feature", "-language:postfixOps", "-language:implicitConversions", "-language:existentials"
) 

unmanagedSourceDirectories in Compile <<= (scalaSource in Compile)(_ :: Nil)

unmanagedSourceDirectories in Test := Nil

libraryDependencies <<= scalaVersion(sV => Seq(
  "org.scala-lang" % "scala-actors" % sV
, "org.scala-lang" % "scala-swing" % sV
))

resolvers := Seq("Element Nexus" at "http://maven.element.hr/nexus/content/groups/public")

externalResolvers <<= resolvers map(rS =>
  Resolver.withDefaultResolvers(rS, mavenCentral = false)
)

publishTo := Some("Element Releases" at "http://maven.element.hr/nexus/content/repositories/releases/")

credentials += Credentials(Path.userHome / ".publish" / "element.credentials")

publishArtifact in (Compile, packageDoc) := false
