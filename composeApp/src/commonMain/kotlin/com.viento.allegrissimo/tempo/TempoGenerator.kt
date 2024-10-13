package com.viento.allegrissimo.tempo

import com.ditchoom.buffer.ByteOrder
import com.ditchoom.buffer.PlatformBuffer
import com.ditchoom.buffer.wrap
import kotlin.math.PI
import kotlin.math.sin

class TempoGenerator(private val waveConfiguration: WaveConfiguration = WaveConfiguration()) {
    private fun generateBeepForm(
        duration: Float
    ): ByteArray {
        val totalBytes = waveConfiguration.getTotalBytes(duration)
        val dataArray = ByteArray(totalBytes)
        val beepSampleSize = totalBytes / 4
        // Generate audio data (e.g., a sine wave)
        for (i in 0 until beepSampleSize step 2) {
            val wave = sin(i * 2 * PI * 440.0 / waveConfiguration.sampleRate)
            val sample = (wave * Short.MAX_VALUE).toInt()
            dataArray[i] = (sample and 0xFF).toByte()
            dataArray[i + 1] = (sample shr  Byte.SIZE_BITS).toByte()
        }
        return dataArray
    }

    fun generateWaveBuffer(bpm: Int, tone: Int, beat: Int): Pair<ByteArray, ByteArray> {
        val durationForBeat = SECONDS_60.toFloat()/bpm.toFloat() * tone
        val waveFormArray = generateBeepForm(durationForBeat)
        val dataArray = ByteArray(waveFormArray.size * beat)
        val dataBuffer = PlatformBuffer.wrap(dataArray, ByteOrder.LITTLE_ENDIAN)
        // Generate audio data (e.g., a sine wave)
        for (i in 0 until beat) {
            dataBuffer.writeBytes(waveFormArray)
        }

        val header = createWaveHeader((durationForBeat * beat))
        return header to dataArray
    }

    private fun createWaveHeader(
        duration: Float
    ): ByteArray {
        val totalBytes = waveConfiguration.getTotalBytes(duration)
        val bytesPerSample = waveConfiguration.sampleSize / Byte.SIZE_BYTES
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
        header.writeShort(waveConfiguration.numChannels.toShort())
        header.writeInt(waveConfiguration.sampleRate)
        val byteRate =
            waveConfiguration.sampleRate * waveConfiguration.numChannels * bytesPerSample
        header.writeInt(byteRate)
        val blockAlign = waveConfiguration.numChannels * bytesPerSample /  Byte.SIZE_BITS
        header.writeShort(blockAlign.toShort())
        header.writeShort(waveConfiguration.sampleSize.toShort())
        header.writeString("data")
        header.writeInt(subChunk2Size)
        return byteArray
    }

    companion object {
        private const val SECONDS_60 = 60
    }
}