package com.gatling.demo.gatling.feeders

import io.gatling.core.result.message.KO

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

/**
 * Created by x077411 on 12/12/2014.
 */
object RelatiesFeeder {

  val relaties = csv("relaties.csv").circular

}
