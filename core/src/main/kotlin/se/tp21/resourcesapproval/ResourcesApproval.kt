@file:Suppress("unused")

package se.tp21.resourcesapproval

import se.tp21.resourcesapproval.WriteTo.Actual
import se.tp21.resourcesapproval.WriteTo.Approved
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
                    Approved -> approved.write(actual)
                    Actual -> approved.toActualFile().write(actual)
                }
            }
            throw throwable
        }

    }

    private fun String.content(): String = File("${resourcesPath}/${this}").readText()

    private fun String.toActualFile(): String =
        this.split(".")
            .let { it.dropLast(1).joinToString(".") to it.last() }
            .let { (fileName, extension) ->
                "$fileName.actual.$extension"
            }

    private fun String.write(content: String) {
        File("${resourcesPath}/${this}").writeText(content)
    }
}
