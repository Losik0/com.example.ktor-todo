package com.example.dao

import com.example.models.TodoBody
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class DAOFacadeDatabase(val db: Database): DAOFacade {
    override fun init() = transaction(db) {
        SchemaUtils.create(Todos)
        val todos = listOf(TodoBody(1,"first contents", false),
            TodoBody(2, "eat buffalo wings", true),
            TodoBody(3, "Study Kotlin", false))
        Todos.batchInsert(todos){ todos ->
            this[Todos.contents] = todos.contents
            this[Todos.is_finished] = todos.is_finished
        }
        Unit
    }

    override fun close() {
    }
}