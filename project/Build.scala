import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "EntWebPrj"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(

	
    // Add your project dependencies here,
	"mysql" % "mysql-connector-java" % "5.1.18",
	"org.apache.tika" % "tika-parsers" % "1.4",
	"edu.stanford.nlp" % "stanford-corenlp" % "3.2.0",
	"commons-io" % "commons-io" % "2.4",
    javaCore,
    javaJdbc,
    javaEbean,
    javaJpa
	
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
