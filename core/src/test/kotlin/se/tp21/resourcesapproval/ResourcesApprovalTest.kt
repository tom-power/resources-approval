package se.tp21.resourcesapproval

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import java.io.File
import kotlin.test.assertEquals

abstract class ResourcesApprovalTest {
    protected val approvedContent =
        """
            {
              "test": true
            }
        """.trimIndent()
    protected val newContent =
        """
            {
              "test": false
            }
        """.trimIndent()
    protected val approvedFile: File = File("src/test/resources/test.json")
    protected val actualFile: File = File("src/test/resources/test.actual.json")

    @AfterEach
    fun cleanUp() {
        actualFile.delete()
        approvedFile.delete()
        approvedFile.writeText(approvedContent)
    }

    protected fun assertHaveApprovedFileWithApprovedContent() {
        assertHaveApprovedFile()
        assertApprovedFileHasApprovedContent()
    }

    protected fun assertHaveApprovedFile() {
        assertTrue(approvedFile.exists())
    }

    protected fun assertDontHaveActualFile() {
        assertFalse(actualFile.exists())
    }

    protected fun assertHaveActualFile() {
        assertTrue(actualFile.exists())
    }

    protected fun assertActualFileHasNewContent() {
        assertEquals(
            expected = newContent,
            actual = actualFile.readText()
        )
    }

    protected fun assertApprovedFileHasNewContent() {
        assertEquals(
            expected = newContent,
            actual = approvedFile.readText()
        )
    }

    protected fun assertApprovedFileHasApprovedContent() {
        assertEquals(
            expected = approvedContent,
            actual = approvedFile.readText()
        )
    }
}
