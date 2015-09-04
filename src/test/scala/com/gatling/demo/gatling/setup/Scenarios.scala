package com.gatling.demo.gatling.setup

import com.gatling.demo.gatling.useCases._
import com.gatling.demo.gatling.feeders._
import io.gatling.core.Predef._
import scala.concurrent.duration._

/**
 * This object collects the Scenarios in the project for use in the Simulation. There are two
 * main properties in this object: acceptanceTestScenario and debugScenario. These two are
 * used in the Simulation class to setup the actual tests to run. If you wish to add
 * scenarios to either run, add them here. 
 */
object Scenarios {

  /**
   * These are the scenarios run in 'normal' mode.
   */
  val acceptanceTestScenarioUnlimited = scenario("acceptanceTestScenarioUnlimited").feed(RelatiesFeeder.relaties).feed(UsersFeeder.users)
    .exec(HelloWorld.useCase)
        .forever(
          exec(ZoekRelatie.useCase)
         )
  val acceptanceTestScenarioUnityContractAanmaken = scenario("acceptanceTestScenarioUnityContractAanmaken").feed(RelatiesFeeder.relaties).feed(UsersFeeder.users)
    .forever(
      pace(60 seconds)
      .exec(GetPeople.useCase)
    )
  val acceptanceTestScenarioUnityDocumentenAanmaken = scenario("acceptanceTestScenarioUnityDocumentenAanmaken").feed(RelatiesFeeder.relaties).feed(UsersFeeder.users)
    .forever(
      pace(60 seconds)
      .exec(PostPeople.useCase)
    )

  /**
   * These are the scenarios run in 'debug' mode.
   */
  val debugScenario = scenario("debug").feed(RelatiesFeeder.relaties).feed(UsersFeeder.users)
    .exitBlockOnFail(
      exec(ZoekRelatie.useCase)
    )

}