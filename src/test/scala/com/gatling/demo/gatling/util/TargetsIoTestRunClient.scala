package com.gatling.demo.gatling.util

import com.google.gson.Gson
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.DefaultHttpClient

object TargetsIoTestRunClient{

  def runningTestCall (url: String, runningTest: targetsIoRunningTest): Int = {

    // convert runningTest to a JSON string
    val runningTestAsJson = new Gson().toJson(runningTest)

    val post = new HttpPost(url)

    // set the Content-type
    post.setHeader("Content-Type", "application/json")

    // add the JSON as a StringEntity
    post.setEntity(new StringEntity(runningTestAsJson))

    // send the get request
    val response = (new DefaultHttpClient).execute(post)

    return response.getStatusLine().getStatusCode();


  }

 }
