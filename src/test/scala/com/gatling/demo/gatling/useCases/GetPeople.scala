package com.gatling.demo.gatling.useCases

import com.gatling.demo.gatling.configuration.Configuration
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import jodd.util.URLDecoder
import scala.concurrent.duration._


object GetPeople  {



	val useCase = 
  exec(http("Get People")
			.get("/people")
      .check(regex("fullName").exists))


}