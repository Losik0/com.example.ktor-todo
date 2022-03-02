package com.example.service

import com.example.dao.TodoRepo
import com.example.models.TodoBody

// Todo : 예외 처리 추가
class TodoServiceImpl(val repo: TodoRepo): TodoService {

    // not working on TodoRoutes....?
    // note: map 으로 묶어서 보내는게 아닌가?
    override fun todoMain(): Map<String, Any> {
        val todoList = repo.getAllTodo()
        return mapOf("size" to todoList.size, "to_do_list" to todoList)
    }

    override fun createTodo(contents: String) {
        repo.createTodo(contents)
    }

    override fun updateTodo(id: Int, contents: String) {
        repo.updateTodo(id, contents)
    }

    override fun completeTodo(id: Int) {
        repo.completeTodo(id)
    }

    override fun deleteTodo(id: Int) {
        repo.deleteTodo(id)
    }

    override fun deleteCom() {
        repo.deleteCom()
    }

    override fun getTodo(id: Int): TodoBody? = repo.getTodo(id)

    override fun getAllTodo(): List<TodoBody> = repo.getAllTodo()

    override fun getComTodo(): List<TodoBody> = repo.getComTodo()

    override fun getUnComTodo(): List<TodoBody> = repo.getUnComTodo()
}