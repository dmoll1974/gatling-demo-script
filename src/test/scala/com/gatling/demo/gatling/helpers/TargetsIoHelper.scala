package com.gatling.demo.gatling.helpers

import com.gatling.demo.gatling.helpers.TargetsIoClient
import io.gatling.core.Predef._

/**
 * Requires at least application and version to be exposed as JVM parameters
 */
class TargetsIoHelper extends Simulation {

  var baselineBuild : String = _
  var targetsIoUrl : String = _
  var buildResultKey : String = _
  if (System.getProperty("buildResultKey") != null) buildResultKey = System.getProperty("buildResultKey") else buildResultKey = "MANUAL_TEST"
  if (System.getProperty("targetsIoUrl") != null) targetsIoUrl = System.getProperty("targetsIoUrl") else targetsIoUrl = "http://dashboard.com"
  val dashboardName = System.getProperty("dashboardName")
  val productName = System.getProperty("productName")
  val testRunId = System.getProperty("testRunId")

  if (testRunId != "DEBUG") {
    println("targetsIoUrl: " + targetsIoUrl + " testRunId: "+ testRunId + " productName: " + productName + " dashboardName: " + dashboardName + buildResultKey)
    require(targetsIoUrl != null && testRunId != null && productName != null && dashboardName != null)
  }
  
  def beforeSimulation() {
    if (testRunId != "DEBUG")
        TargetsIoClient.sendEvent(targetsIoUrl, "start", testRunId,  buildResultKey, dashboardName, productName)

  }

  before {
    beforeSimulation()
  }

  def afterSimulation() {

    if (testRunId != "DEBUG")
        TargetsIoClient.sendEvent(targetsIoUrl, "end", testRunId, buildResultKey, dashboardName, productName)

  }

  after {
    afterSimulation()
  }
}
