package com.example

import com.example.dao.DAOFacadeDatabase
import com.example.dao.TodoRepoImpl
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*

import com.example.routes.registerTodoRoutes
import com.example.service.TodoServiceImpl
import io.ktor.request.*
import org.jetbrains.exposed.sql.Database
import org.slf4j.event.Level

/*
fun main() {
    embeddedServer(Netty, port = 8000, host = "localhost") {
        configureRouting()
        configureSerialization()
        registerTodoRoutes()
    }.start(wait = true)
}
 */


val db = Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation){ json()}
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }
    val dbs = DAOFacadeDatabase(db)
    dbs.init()
    registerTodoRoutes(TodoServiceImpl(TodoRepoImpl(db)))
}
