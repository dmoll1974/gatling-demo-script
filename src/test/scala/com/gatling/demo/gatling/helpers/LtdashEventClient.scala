package com.gatling.demo.gatling.helpers

import com.google.gson.Gson
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.DefaultHttpClient

object LtdashEventClient{

  def postEvent (url: String, event: Event) : Int = {

    // convert it to a JSON string
    val eventAsJson = new Gson().toJson(event)

    // create an HttpPost object
    val post = new HttpPost(url)

    // set the Content-type
    post.setHeader("Content-Type", "application/json")

    // add the JSON as a StringEntity
    post.setEntity(new StringEntity(eventAsJson))

    // send the post request
    val response = (new DefaultHttpClient).execute(post)

    return response.getStatusLine().getStatusCode();

  }
 }