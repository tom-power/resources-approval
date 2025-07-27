@file:Suppress("unused")

package se.tp21.resourcesapproval

import java.io.File
import kotlin.test.assertEquals

object ResourcesApproval {
    const val resourcesPath = "src/test/resources"

    fun assertApproved(actual: String, approved: String) {
        assertEquals(
            expected = approved.content(),
            actual = actual,
        )
    }

    fun assertApprovedWriteActual(actual: String, approved: String) {
        runCatching {
            assertEquals(
                expected = approved.content(),
                actual = actual,
            )
        }.onFailure { throwable ->
            approved.toActual().write(actual).also { throw throwable }
        }
    }

    fun assertApprovedWriteApproved(actual: String, approved: String) {
        runCatching {
            assertEquals(
                expected = approved.content(),
                actual = actual,
            )
        }.onFailure { throwable ->
            approved.write(actual).also { throw throwable }
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
