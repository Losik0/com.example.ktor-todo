package com.example.dao

import java.io.Closeable

interface DAOFacade: Closeable{
    fun init()
}