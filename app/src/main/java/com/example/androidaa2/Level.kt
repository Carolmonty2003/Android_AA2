package com.example.androidaa2

/**
 * Level
 * ------
 * Entidad simple que representa un nivel jugable del ahorcado.
 *
 * Campos:
 *  @property id   Identificador único del nivel (entero positivo).
 *  @property word Palabra objetivo en MAYÚSCULAS o minúsculas (la lógica del juego
 *                 la normaliza cuando corresponde).
 *
 * Buenas prácticas:
 *  - Data class para disponer de equals/hashCode/toString/ copy automáticamente.
 *  - Tipos explícitos y nombres en lowerCamelCase.
 *
 * Uso responsable de IA:
 *  - IA-asistida solo para redactar esta documentación y estandarizar comentarios.
 *  - Logica de detección de tamaño de cadema de letras para mostrar dificultad.
 */

data class Level(
    val id: Int,
    val word: String
) {
    /**
     * Devuelve la cantidad de letras de la palabra objetivo.
     * Se usa para:
     *  - Mostrar "Letras: N" en el selector.
     *  - Calcular la dificultad (fácil/medio/difícil) según el rango.
     */
    fun lettersCount(): Int = word.length
}

