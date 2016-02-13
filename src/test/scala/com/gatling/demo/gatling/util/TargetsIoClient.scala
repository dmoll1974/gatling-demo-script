package com.gatling.demo.gatling.util

object TargetsIoClient {

  def sendTestRunEvent(host: String, command: String, testRunId: String, buildResultsUrl: String, dashboardName: String, productName: String, productRelease: String ) {

    println( "sending "+ command + " test run call to rest service at host " + host + " with data: testRunId: "+ testRunId + ", productName: " + productName + ", productRelease: " + productRelease + ", dashboardName: " + dashboardName +  ", buildResultsUrl " + buildResultsUrl )

    val runningTestUrl = host + "/running-test/" + command

    val runningTest = new targetsIoRunningTest(productName, dashboardName, testRunId, buildResultsUrl, productRelease)

    try {
      val response = TargetsIoTestRunClient.runningTestCall(runningTestUrl, runningTest)
      println( "responseCode: "+ response )
      if ( response != 200 ) {

        println( "Something went wrong when starting the tesr run, please check in your pom.xml if the test run ID is unique for this dashboard. If so please contact support." )

      }
    } catch {
      case e: Exception =>
        println ( "exception occured: " + e + ", please create test run manually" );
    }

  }

}

class targetsIoRunningTest( var productName: String, var dashboardName: String, var testRunId: String, var buildResultsUrl: String, var productRelease: String ) {
}

