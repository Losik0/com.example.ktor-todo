package com.example.service

import com.example.models.TodoBody

interface TodoService {
    fun todoMain(): Map<String, Any>
    fun createTodo(contents: String)
    fun updateTodo(id:Int, contents: String)
    fun completeTodo(id: Int)
    fun deleteTodo(id:Int)
    fun deleteCom()
    fun getTodo(id:Int): TodoBody?
    fun getAllTodo(): List<TodoBody>
    fun getComTodo(): List<TodoBody>
    fun getUnComTodo(): List<TodoBody>
}