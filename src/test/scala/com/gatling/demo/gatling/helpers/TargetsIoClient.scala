package com.gatling.demo.gatling.helpers

object TargetsIoClient {

  def sendEvent( host: String, what: String, buildId: String, baselineBuild: String, buildResultKey: String, dashboardName: String, productName: String ) {

    println( "sending "+what+"-loadtest call to rest service with data: buildId: "+ buildId + ", productName= " + productName + ", dashboardName: " + dashboardName + ", baselineBuild: " + baselineBuild + ", buildResultKey " + buildResultKey )

    val eventUrl = host + "/events"
    val event = new targetsIoEvent( buildId, what, baselineBuild, productName, dashboardName, buildResultKey )

    try {
      val response = TargetsIoEventClient.postEvent( eventUrl, event )
      println( "responseCode: "+response )
      if ( response == 500 ) {

        println( "Sending event went wrong, please check in your pom.xml if the builId is unique for this dashboard. If so please contact support." )
//        System.exit( -1 )

      }
    } catch {
      case e: Exception =>
        println ( "exception occured: "+e+", please send above event manually" );
    }

  }

}

class targetsIoEvent( var testRunId: String, var eventDescription: String, var baseline: String, var productName: String, var dashboardName: String, var buildResultKey: String ) {
}

