package com.example.androidaa2

//uso de chat gpt

data class Level(
    val id: Int,
    val word: String
) {
    private val lettersCount: Int get() = word.length
    val difficulty: Difficulty
        get() = when (lettersCount) {
            in 1..4 -> Difficulty.EASY
            in 5..7 -> Difficulty.MEDIUM
            else -> Difficulty.HARD
        }
}
enum class Difficulty { EASY, MEDIUM, HARD }
