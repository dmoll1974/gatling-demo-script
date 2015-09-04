package com.gatling.demo.gatling.useCases


import com.gatling.demo.gatling.configuration.Configuration
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import jodd.util.URLDecoder
import scala.concurrent.duration._


object PostPeople{



  val useCase =
    exec(http("Get People")
      .post("/people")
      .header("Content-Type", "application/json")
      .body(ELFileBody("useCases/people.json"))
      .check(regex("fullName").exists))


}