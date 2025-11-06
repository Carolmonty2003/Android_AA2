package com.example.androidaa2

//uso de chat gpt

data class Level(
    val id: Int,
    val word: String
) {
    fun lettersCount(): Int = word.length
}

