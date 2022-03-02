package com.example.dao

import org.jetbrains.exposed.sql.Table

object Todos: Table() {
    val id = integer("id").autoIncrement()
    val contents = varchar("contents", 255)
    val is_finished = bool("is_finished").default(false)
    override val primaryKey = PrimaryKey(id)
}