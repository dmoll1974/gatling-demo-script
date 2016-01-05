package com.gatling.demo.gatling.helpers


import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

/**
  * This object collects the Scenarios in the project for use in the Simulation. There are two
  * main properties in this object: acceptanceTestScenario and debugScenario. These two are
  * used in the Simulation class to setup the actual tests to run. If you wish to add
  * scenarios to either run, add them here.
  */
object HelperScenarios {


  val targetsIoHeaders = Map(
    """Content-Type""" -> """application/json""")

  /**
    * Auxiliary scenario, included only when the wily export profile is active. It will send
    * keep alive requests to the Wily Export Daemon during the test run.
    */

  val runningTestKeepAliveScenario = scenario("Wily Export Keepalive")
    .forever(
      exec(session => session
        .set("productName", System.getProperty("productName"))
        .set("dashboardName", System.getProperty("dashboardName"))
        .set("testRunId", System.getProperty("testRunId"))
        .set("targetsIoUrl", System.getProperty("targetsIoUrl"))
      )
        .exec(http("Wily Export Keep Alive")
          .post("${targetsIoUrl}/running-test/keep-alive")
          .body(ELFileBody("bodies/testrun/testrun.json")).asJSON
          .headers(targetsIoHeaders)
          .silent
        )
        .pause(14 seconds)
    )
}