package com.example

import com.example.models.TodoBody
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.features.*
import io.ktor.application.*
import io.ktor.response.*
import kotlin.test.*
import io.ktor.server.testing.*

class TodoRouteTests {

    @Test // Note: get all list
    fun testTodo() {
        withTestApplication(Application::module) {
            handleRequest(HttpMethod.Get, "/todo").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                //assertEquals("Nothing", response.content)
            }
        }
    }

    @Test // Note: post to-do
    fun postTodo(){
        withTestApplication(Application::module) {
            with(handleRequest(HttpMethod.Post, "/todo"){
                addHeader(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded.toString())
                setBody(listOf("contents" to "test-contents").formUrlEncode())
            })
            {
                assertEquals(HttpStatusCode.Created, response.status())
                assertEquals("Todo stored", response.content)
            }
        }
    }

}