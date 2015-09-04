package com.gatling.demo.gatling.useCases

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.session.Expression
import scala.concurrent.duration._


object HelloWorld  {
  {



    val useCase =
      exec(http("Hello world")
        .get("/hello-world")
        .check(regex("greetings").exists))


  }