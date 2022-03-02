package com.example.routes

import com.example.dao.TodoRepo
import com.example.dao.TodoRepoImpl
import com.example.models.*
import com.example.service.TodoService
import com.example.service.TodoServiceImpl
import io.ktor.routing.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import org.jetbrains.exposed.sql.Database

fun Route.todoRouting(service: TodoService) {
    route("/todo") {
        //note: get List
        get {
            call.respond(service.getAllTodo())
        }
        //note: get one item
        get("{id}") {
            val id = call.parameters["id"]?: return@get call.respondText(
                "Missing id", status=HttpStatusCode.BadRequest
            )
            val todo = service.getTodo(id.toInt()) ?: return@get call.respondText(
                    "No todo item with id $id", status=HttpStatusCode.NotFound
            )
            call.respond(todo)
        }
        //note: post item
        post {
            val parameters = call.receiveParameters()
            service.createTodo(parameters["contents"].toString())
            call.respondText("Todo stored", status= HttpStatusCode.Created)
        }
        //note: delete item
        delete("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            service.deleteTodo(id.toInt())
            call.respondText("Todo Deleted", status=HttpStatusCode.Accepted)
        }
        //note: get item size
        get("items"){
            call.respond(mapOf("size" to service.getAllTodo().size))
        }
    }
}

fun Route.todoCompletedRouting(service: TodoService){
    route("/completed"){

        //note: make item completed
        put("{id}") {
            val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest)
            service.completeTodo(id.toInt())
            call.respondText("Todo finished", status=HttpStatusCode.Accepted)
        }

        //note: get completed
        get {
            call.respond(service.getComTodo())
        }

        get("un") {
            call.respond(service.getUnComTodo())
        }

        //note: delete all of completed items
        delete {
            service.deleteCom()
            call.respondText("Completed Todo Deleted", status=HttpStatusCode.Accepted)
        }
    }
}



fun Application.registerTodoRoutes(services: TodoService){
    routing{
        todoRouting(services)
        todoCompletedRouting(services)
    }
}
