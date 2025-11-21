package com.example.todoapp.presentation.util

fun String.limit(maxLength: Int) = if(length > maxLength) take(maxLength) + "..." else this