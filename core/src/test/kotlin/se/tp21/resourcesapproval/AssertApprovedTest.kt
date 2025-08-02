package se.tp21.resourcesapproval

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.opentest4j.AssertionFailedError

class AssertApprovedTest : ResourcesApprovalTest() {
    @Test
    fun `can assert approved`() {
        assertHaveApprovedFileWithApprovedContent()
        assertDontHaveActualFile()

        assertDoesNotThrow {
            ResourcesApproval.assertApproved(
                actual = approvedContent,
                approved = approvedFile.name
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
                approved = approvedFile.name
            )
        }

        assertHaveApprovedFileWithApprovedContent()
        assertDontHaveActualFile()
    }
}
