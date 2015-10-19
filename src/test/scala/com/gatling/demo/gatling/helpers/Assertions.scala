package com.gatling.demo.gatling.helpers


import _root_.io.gatling.core.Predef._
import _root_.io.gatling.core.scenario.Simulation
import _root_.io.gatling.http.Predef._
import io.gatling.core.Predef._
import io.gatling.core.result.message.KO
import io.gatling.http.Predef._

class Assertions extends Simulation{


  var targetsIoUrl = "http://" + System.getProperty("TARGETSIO_TARGETSIO_1_PORT_3000_TCP_ADDR") + ":3000"
  var productName = System.getProperty("productName")
  var dashboardName = System.getProperty("dashboardName")
  var testRunId = System.getProperty("testRunId")

  val httpProtocol = http
    .baseURL(targetsIoUrl)
    .extraInfoExtractor(ExtraInfo => {
    if (ExtraInfo.status == KO)
      ExtraInfo.requestName match{

        case "Get requirements results for test run" =>

          println("Requirements results failed: " + targetsIoUrl + "/#!/requirements/" + productName + "/" + dashboardName + "/" + testRunId + "/failed")

        case "Get benchmark to previous build results" =>

          println("Benchmark to previous build results failed: " + targetsIoUrl + "/#!/benchmark-previous-build/" + productName + "/" + dashboardName + "/" + testRunId + "/failed")

        case "Get benchmark to fixed baseline results" =>

          println("Benchmark to previous build results failed: " + targetsIoUrl + "/#!/benchmark-fixed-baseline/" + productName + "/" + dashboardName + "/" + testRunId + "/failed")

      }

      println("Response: " + ExtraInfo.response.body)
    Nil
  })




  val ltdashHeaders = Map(
    """Content-Type""" -> """application/json""")


  val targetsIoAssertions =
    exec(session => session.set("productName", productName)
                           .set("dashboardName", dashboardName)
                           .set("testRunId", testRunId)
    )
      .exec(http("Get requirements results for test run")
      .get( """/testrun/${productName}/${dashboardName}/${testRunId}""" )
      .headers(ltdashHeaders)
//      .check(jsonPath("$.benchmarkResultPreviousOK").is("true"))
//      .check(jsonPath("$.benchmarkResultFixedOK").is("true"))
      .check(jsonPath("$.meetsRequirement").is("true"))
     )
      .exec(http("Get benchmark to previous build results")
      .get( """/testrun/${productName}/${dashboardName}/${testRunId}""" )
      .headers(ltdashHeaders)
      .check(jsonPath("$.benchmarkResultPreviousOK").is("true"))
//      .check(jsonPath("$.benchmarkResultFixedOK").is("true"))
//      .check(jsonPath("$.meetsRequirement").is("true"))
      )
      .exec(http("Get benchmark to fixed baseline results")
      .get( """/testrun/${productName}/${dashboardName}/${testRunId}""" )
      .headers(ltdashHeaders)
//      .check(jsonPath("$.benchmarkResultPreviousOK").is("true"))
      .check(jsonPath("$.benchmarkResultFixedOK").is("true"))
//      .check(jsonPath("$.meetsRequirement").is("true"))
      )
  val assertionsScenario = scenario("assertions")
    .exec(targetsIoAssertions)

  setUp(

    assertionsScenario.inject(atOnceUsers(1))

  ).protocols(httpProtocol)
    .assertions(forAll.failedRequests.count.is(0))


}
