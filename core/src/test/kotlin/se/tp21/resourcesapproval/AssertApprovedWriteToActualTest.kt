package se.tp21.resourcesapproval

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.opentest4j.AssertionFailedError
import se.tp21.resourcesapproval.WriteTo.Actual

class AssertApprovedWriteToActualTest : ResourcesApprovalTest() {
    @Test
    fun `can assert approved`() {
        assertHaveApprovedFileWithApprovedContent()
        assertDontHaveActualFile()

        assertDoesNotThrow {
            ResourcesApproval.assertApproved(
                actual = approvedContent,
                approved = approvedFile.name,
                writeTo = Actual
            )
        }

        assertHaveApprovedFileWithApprovedContent()
        assertDontHaveActualFile()
    }

    @Test
    fun `can assert not approved`() {
        assertHaveApprovedFileWithApprovedContent()
        assertDontHaveActualFile()

        assertThrows<AssertionFailedError> {
            ResourcesApproval.assertApproved(
                actual = newContent,
                approved = approvedFile.name,
                writeTo = Actual
            )
        }

        assertHaveApprovedFileWithApprovedContent()
        assertHaveActualFile()
        assertActualFileHasNewContent()
    }
}