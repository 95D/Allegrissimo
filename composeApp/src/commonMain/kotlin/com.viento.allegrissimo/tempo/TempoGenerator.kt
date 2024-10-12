package com.viento.allegrissimo.tempo

import com.ditchoom.buffer.ByteOrder
import com.ditchoom.buffer.PlatformBuffer
import com.ditchoom.buffer.wrap
import kotlin.math.PI
import kotlin.math.sin

class TempoGenerator {
    fun generateWaveFile(bpm: Int, tone: Int, beat: Int): Pair<ByteArray, ByteArray> {
        val sampleRate = 44100 // Samples per second
        val numChannels = 1 // Mono
        val sampleSize = 16 // Bits per sample

        val durationForBeat = 60f/bpm * tone
        val samplesPerBeat = (sampleRate * durationForBeat/60).toInt()
        val duration = (durationForBeat * beat).toInt() // Seconds

        val totalSamples = samplesPerBeat * duration
        val bytesPerSample = sampleSize / 8
        val totalBytes = totalSamples * bytesPerSample

        val data = ByteArray(totalBytes)

        // Generate audio data (e.g., a sine wave)
        for (i in 0 until totalSamples) {
            val sample =  //(sin(i * 2 * PI * tone / sampleRate) * Short.MAX_VALUE)
                (sin(i * 2 * PI * 440.0 / sampleRate) * Short.MAX_VALUE)
                .toInt().toShort().toInt()
            val index = i * bytesPerSample
            data[index] = (sample and 0xFF).toByte()
            data[index + 1] = (sample shr 8).toByte()
        }

        val header = createWaveHeader(sampleRate, numChannels, sampleSize, totalBytes)
        return header to data
    }

    private fun createWaveHeader(
        sampleRate: Int,
        numChannels: Int,
        sampleSize: Int,
        totalBytes: Int
    ): ByteArray {
        val bytesPerSample = sampleSize / 8
        val chunkSize = totalBytes + 36 // Header size
        val subChunk2Size = totalBytes

        val byteArray = ByteArray(44)
        val header = PlatformBuffer.wrap(byteArray, byteOrder = ByteOrder.LITTLE_ENDIAN)
        header.writeString("RIFF")
        header.writeInt(chunkSize)
        header.writeString("WAVE")
        header.writeString("fmt ")
        header.writeInt(16) // Subchunk 1 size
        header.writeShort(1.toShort()) // Audio format (PCM)
        header.writeShort(numChannels.toShort())
        header.writeInt(sampleRate)
        header.writeInt(sampleRate * numChannels * bytesPerSample)
        header.writeShort((numChannels * bytesPerSample).toShort())
        header.writeShort(sampleSize.toShort())
        header.writeString("data")
        header.writeInt(subChunk2Size)
        return byteArray
    }
}