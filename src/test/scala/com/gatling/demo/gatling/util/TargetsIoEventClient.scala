package com.klm.gatling.util

import com.google.gson.Gson
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.methods.HttpGet

import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.DefaultHttpClient

object TargetsIoEventClient{

  def runningTestCall (url: String): Int = {

    val get = new HttpGet(url)

    // set the Content-type
    get.setHeader("Content-Type", "application/json")

    // send the get request
    val response = (new DefaultHttpClient).execute(get)

    return response.getStatusLine().getStatusCode();


  }

 }
