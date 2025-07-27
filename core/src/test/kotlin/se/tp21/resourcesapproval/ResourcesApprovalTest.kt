package se.tp21.resourcesapproval

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.opentest4j.AssertionFailedError
import se.tp21.resourcesapproval.WriteTo.*
import java.io.File
import kotlin.test.assertEquals

class ResourcesApprovalTest {
    private val approvedContent =
        """
            {
              "test": true
            }
        """.trimIndent()
    private val newContent =
        """
            {
              "test": false
            }
        """.trimIndent()
    private val approvedFile: File = File("src/test/resources/test.json")
    private val actualFile: File = File("src/test/resources/test.actual.json")

    @AfterEach
    fun cleanUp() {
        actualFile.delete()
        approvedFile.delete()
        approvedFile.writeText(approvedContent)
    }

    @Nested
    inner class AssertApproved {
        @Test
        fun `can assert approved`() {
            assertOnlyHaveApprovedFile()

            assertDoesNotThrow {
                ResourcesApproval.assertApproved(
                    actual = approvedContent,
                    approved = approvedFile.name
                )
            }

            assertOnlyHaveApprovedFile()
        }

        @Test
        fun `can assert not approved`() {
            assertOnlyHaveApprovedFile()

            assertThrows<AssertionFailedError> {
                ResourcesApproval.assertApproved(
                    actual = newContent,
                    approved = approvedFile.name
                )
            }

            assertOnlyHaveApprovedFile()
        }
    }

    @Nested
    inner class AssertApprovedWriteActual {
        @Test
        fun `can assert approved`() {
            assertOnlyHaveApprovedFile()

            assertDoesNotThrow {
                ResourcesApproval.assertApproved(
                    actual = approvedContent,
                    approved = approvedFile.name,
                    writeTo = Actual
                )
            }

            assertOnlyHaveApprovedFile()
        }

        @Test
        fun `can assert not approved`() {
            assertOnlyHaveApprovedFile()

            assertThrows<AssertionFailedError> {
                ResourcesApproval.assertApproved(
                    actual = newContent,
                    approved = approvedFile.name,
                    writeTo = Actual
                )
            }

            assertHaveApprovedFile()
            assertApprovedFileHasApprovedContent()
            assertHaveActualFile()
            assertActualFileHasNewContent()
        }
    }

    @Nested
    inner class AutoApprovedWriteApproved {
        @Test
        fun `can assert approved`() {
            assertOnlyHaveApprovedFile()

            assertDoesNotThrow {
                ResourcesApproval.assertApproved(
                    actual = approvedContent,
                    approved = approvedFile.name,
                    writeTo = Approved
                )
            }

            assertOnlyHaveApprovedFile()
        }

        @Test
        fun `can assert not approved`() {
            assertOnlyHaveApprovedFile()

            assertThrows<AssertionFailedError> {
                ResourcesApproval.assertApproved(
                    actual = newContent,
                    approved = approvedFile.name,
                    writeTo = Approved
                )
            }

            assertHaveApprovedFile()
            assertApprovedFileHasNewContent()
            assertDontHaveActualFile()
        }
    }

    private fun assertOnlyHaveApprovedFile() {
        assertHaveApprovedFile()
        assertApprovedFileHasApprovedContent()
        assertDontHaveActualFile()
    }

    private fun assertHaveApprovedFile() {
        assertTrue(approvedFile.exists())
    }

    private fun assertDontHaveActualFile() {
        assertFalse(actualFile.exists())
    }

    private fun assertHaveActualFile() {
        assertTrue(actualFile.exists())
    }

    private fun assertActualFileHasNewContent() {
        assertEquals(
            expected = newContent,
            actual = actualFile.readText()
        )
    }

    private fun assertApprovedFileHasNewContent() {
        assertEquals(
            expected = newContent,
            actual = approvedFile.readText()
        )
    }

    private fun assertApprovedFileHasApprovedContent() {
        assertEquals(
            expected = approvedContent,
            actual = approvedFile.readText()
        )
    }
}
