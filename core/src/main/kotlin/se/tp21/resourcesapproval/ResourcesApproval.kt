@file:Suppress("unused")

package se.tp21.resourcesapproval

import java.io.File
import kotlin.test.assertEquals

enum class WriteTo {
    Approved, Actual
}

object ResourcesApproval {
    const val resourcesPath = "src/test/resources"

    fun assertApproved(actual: String, approved: String, writeTo: WriteTo? = null) {
        runCatching {
            assertEquals(
                expected = approved.content(),
                actual = actual,
            )
        }.onFailure { throwable ->
            writeTo?.let {
                when (writeTo) {
                    WriteTo.Approved -> approved.write(actual)
                    WriteTo.Actual -> approved.toActual().write(actual)
                }
            }
            throw throwable
        }

    }

    private fun String.content(): String = File("${this@ResourcesApproval.resourcesPath}/${this}").readText()

    private fun String.toActual(): String {
        val (fileName, extension) = this.split(".").let { it.dropLast(1).joinToString(".") to it.last() }
        return "$fileName.actual.$extension"
    }

    private fun String.write(content: String) {
        File("${this@ResourcesApproval.resourcesPath}/${this}").writeText(content)
    }
}
