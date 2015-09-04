package com.gatling.demo.gatling.helpers

import io.gatling.core.Predef._
import io.gatling.http.Predef._

/**
 * Contains a (sub)chain to be used when sending events to the loadtest dashboard.
 */
object EventHelper {

  /**
   * Sends an event log to the loadtest dashboard.
   * 
   * Requires the session to contain values for the variables eventDescription, testRunId and dashboardName.
   */
  val sendEvent = exec(http("Send event")
        .post( """http://lt-dash.cf.eden.klm.com/event/create""")
        .body(ELFileBody("event/event.json")).asJSON
        .silent
      )


}
