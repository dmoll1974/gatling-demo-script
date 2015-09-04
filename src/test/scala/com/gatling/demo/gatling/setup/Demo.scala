package com.gatling.demo.gatling.setup

import com.gatling.demo.gatling.configuration.Configuration
import com.gatling.demo.gatling.helpers.{LtDashHelper, HelperScenarios}
import io.gatling.core.Predef._
import io.gatling.core.structure.{PopulatedScenarioBuilder, ScenarioBuilder}

/**
 * This Simulation class is responsible for configuring the Scenarios to run, keeping the active
 * profile and configuration in mind. A lot of what happens here depends on the configuration in 
 * the application.conf file. When changes have to be made to which scenarios to run, that
 * information is gathered in the Scenarios object.
 */
class Demo extends LtDashHelper {


  /** 
   * This baseScenario determines whether Debug is active and it should override the default scenarios, or
   * whether it should simply take up the scenarios as defined in Scenarios.acceptanceTestScanario.
   */
  val baseScenario = if (Configuration.isDebugActive) Scenarios.debugScenario else Scenarios.acceptanceTestScenario

  /**
   * The runnableScenarios list will contain injected versions of all of the scenarios that need to be run.
   * This list is constructed by configuring the scenarios in the baseScenario and optionally prepending the
   * list with some auxiliary scenarios when they are needed.
   */
  val runnableScenarios: List[PopulatedScenarioBuilder] = configureBaseScenarios(List(baseScenario))



  /**
   * Recurses over the list of scenarios passed into the function and sets them up with the required users, ramp-up,
   * etc.
   * @param scenarios the list of Scenarios to initialize
   * @return a list of PopulatedScenarioBuilders, ready to be simulated
   */
  def configureBaseScenarios(scenarios: List[ScenarioBuilder]): List[PopulatedScenarioBuilder] = scenarios match {
    case Nil => Nil
    case sc :: scs => (if (Configuration.isDebugActive) setupSingleDebugScenario(sc) else setupSingleScenario(sc)) :: configureBaseScenarios(scs)
  }

  /**
   * Injects the required debug settings into a single ScenarioBuilder.
   * @param scn the Scenario to initialize
   * @return the initialized PopulatedScenarioBuilder
   */
  def setupSingleDebugScenario(scn: ScenarioBuilder): PopulatedScenarioBuilder = scn.inject(
      atOnceUsers(1)
    ).disablePauses

  /**
   * Injects the required settings into a single ScenarioBuilder.
   * @param scn the Scenario to initialize
   * @return the initialized PopulatedScenarioBuilder
   */
  def setupSingleScenario(scn: ScenarioBuilder): PopulatedScenarioBuilder = scn.inject(
      // Each default scenario is delayed by 10 seconds to allow the Mocca caches to be loaded
      rampUsers(Configuration.initialUsersPerSecond) over (Configuration.rampUpPeriodInSeconds),
      constantUsersPerSec(Configuration.targetUsersPerSecond) during(Configuration.constantUsagePeriodInSeconds)


    )
    .exponentialPauses


  // Go!
  setUp(runnableScenarios).protocols(if (Configuration.isDebugActive) Configuration.httpDebugProtocol else Configuration.httpProtocol)

}
