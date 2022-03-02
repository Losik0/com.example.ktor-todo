package com.example.dao

import com.example.models.TodoBody
import java.io.Closeable

interface TodoRepo: Closeable{
    fun createTodo(contents: String)
    fun updateTodo(id:Int, contents: String)
    fun completeTodo(id: Int)
    fun deleteTodo(id:Int)
    fun deleteCom()
    fun getTodo(id:Int): TodoBody?
    fun getAllTodo(): List<TodoBody>
    fun getComTodo(): List<TodoBody>
    fun getUnComTodo(): List<TodoBody>
    fun getTodoSize(): Int
}