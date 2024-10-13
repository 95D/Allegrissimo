package com.viento.allegrissimo.tempo

data class WaveConfiguration(
    val sampleRate: Int = 44100,
    val numChannels: Int = 1,
    val sampleSize: Int = 16
) {
    val bytePerSample get() = sampleSize / Byte.SIZE_BITS
    fun getTotalSamples(duration: Float): Float =
        sampleRate * numChannels * duration

    fun getTotalBytes(duration: Float): Int =
        (getTotalSamples(duration) * bytePerSample).toInt()
}