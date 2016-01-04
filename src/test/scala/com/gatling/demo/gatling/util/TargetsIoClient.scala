package com.klm.gatling.util

object TargetsIoClient {

  def sendEvent(host: String, command: String, testRunId: String, buildResultKey: String, dashboardName: String, productName: String ) {

    println( "sending "+command+"-loadtest call to rest service with data: testRunId: "+ testRunId + ", productName: " + productName + ", dashboardName: " + dashboardName +  ", buildResultKey " + buildResultKey )

    val eventUrl = host + "/running-test/" + productName + '/' + dashboardName + '/' + testRunId + '/' + command


    try {
      val response = TargetsIoEventClient.runningTestCall( eventUrl)
      println( "responseCode: "+ response )
      if ( response != 200 ) {

        println( "Sending event went wrong, please check in your pom.xml if the test run ID is unique for this dashboard. If so please contact support." )

      }
    } catch {
      case e: Exception =>
        println ( "exception occured: " + e + ", please send above event manually" );
    }

  }

}


