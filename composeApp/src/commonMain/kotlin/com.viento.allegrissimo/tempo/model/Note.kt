package com.viento.allegrissimo.tempo.model

enum class Note(
    val divided: Int
) {
    THIRTY_SECOND(divided = 32),
    SIXTEENTH(divided = 16),
    EIGHTH(divided = 8),
    QUARTER(divided = 4),
    HALF(divided = 2),
    WHOLE(divided = 1);

    val length: Float get() = 4f / divided

    fun up(): Note = when (this) {
        THIRTY_SECOND -> THIRTY_SECOND
        SIXTEENTH -> THIRTY_SECOND
        EIGHTH -> SIXTEENTH
        QUARTER -> EIGHTH
        HALF -> QUARTER
        WHOLE -> HALF
    }

    fun down(): Note = when (this) {
        THIRTY_SECOND -> SIXTEENTH
        SIXTEENTH -> EIGHTH
        EIGHTH -> QUARTER
        QUARTER -> HALF
        HALF -> WHOLE
        WHOLE -> WHOLE
    }
}