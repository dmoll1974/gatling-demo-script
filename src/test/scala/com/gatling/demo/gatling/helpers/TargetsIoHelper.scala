package com.gatling.demo.gatling.helpers

import com.gatling.demo.gatling.helpers.TargetsIoClient
import io.gatling.core.Predef._

/**
 * Requires at least application and version to be exposed as JVM parameters
 */
class TargetsIoHelper extends Simulation {

  var baselineBuild : String = _
  var ltdashUrl : String = _
  var buildResultKey : String = _
  if (System.getProperty("baselineBuild") != null) baselineBuild = System.getProperty("baselineBuild") else  baselineBuild = "none"
  if (System.getProperty("buildResultKey") != null) buildResultKey = System.getProperty("buildResultKey") else buildResultKey = "MANUAL_TEST"
  if (System.getProperty("ltdashUrl") != null) ltdashUrl = System.getProperty("ltdashUrl") else ltdashUrl = "http://dashboard.com"
  val dashboardName = System.getProperty("dashboardName")
  val productName = System.getProperty("productName")
  val buildId = System.getProperty("buildId")

  if (buildId != "DEBUG") {
    println("ltdashUrl: " + ltdashUrl + " buildId: "+ buildId + " productName: " + productName + " dashboardName: " + dashboardName + " baselineBuild: " + baselineBuild + " buildResultKey: " + buildResultKey)
    require(ltdashUrl != null && buildId != null && productName != null && dashboardName != null && baselineBuild != null)
  }
  
  def beforeSimulation() {
    if (buildId != "DEBUG")
        TargetsIoClient.sendEvent(ltdashUrl, "start", buildId, baselineBuild, buildResultKey, dashboardName, productName)

  }

  before {
    beforeSimulation()
  }

  def afterSimulation() {

    if (buildId != "DEBUG")
        TargetsIoClient.sendEvent(ltdashUrl, "end", buildId, baselineBuild, buildResultKey, dashboardName, productName)

  }

  after {
    afterSimulation()
  }
}
