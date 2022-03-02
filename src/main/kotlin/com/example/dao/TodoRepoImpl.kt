package com.example.dao

import com.example.models.TodoBody
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class TodoRepoImpl(val db: Database): TodoRepo {

    override fun createTodo(contents: String) = transaction(db) {
        Todos.insert { it[Todos.contents]=contents }
        Unit
    }

    override fun updateTodo(id: Int, contents: String) = transaction(db) {
        Todos.update({Todos.id eq id}){
            it[Todos.contents] = contents
        }
        Unit
    }

    override fun completeTodo(id: Int) = transaction(db) {
        Todos.update({Todos.id eq id}){
            it[Todos.is_finished] = true
        }
        Unit
    }

    override fun deleteTodo(id: Int) = transaction(db) {
        Todos.deleteWhere { Todos.id eq id }
        Unit
    }

    override fun deleteCom()  = transaction(db) {
        Todos.deleteWhere { Todos.is_finished eq true }
        Unit
    }

    override fun getTodo(id: Int): TodoBody? = transaction(db) {
        Todos.select{Todos.id eq id}.map{
            TodoBody(it[Todos.id], it[Todos.contents], it[Todos.is_finished])
        }.singleOrNull()
    }

    override fun getAllTodo(): List<TodoBody> = transaction(db) {
        Todos.selectAll().map {
            TodoBody(it[Todos.id], it[Todos.contents], it[Todos.is_finished]
            )
        }
    }

    override fun getComTodo(): List<TodoBody>  = transaction(db) {
        Todos.select{Todos.is_finished eq true}.map {
            TodoBody(it[Todos.id], it[Todos.contents], it[Todos.is_finished]
            )
        }
    }

    override fun getUnComTodo(): List<TodoBody> = transaction(db) {
        Todos.select{Todos.is_finished eq false}.map {
            TodoBody(it[Todos.id], it[Todos.contents], it[Todos.is_finished]
            )
        }
    }

    override fun getTodoSize(): Int {
        return Todos.select{Todos.is_finished eq false}.fetchSize ?: 0
    }

    override fun close() {
    }
}