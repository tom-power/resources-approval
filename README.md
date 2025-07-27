Resources Approval
=========

Playing with simple use case of asserting content against an approved file in resources using JUnit5. 

```kotlin
class ResourcesApprovalExampleTest {
    @Test
    fun `assert actual content against approved`() {
        ResourcesApproval.assertApproved(
            actual = "test", 
            approved = "test.txt"
        )    
    }
    
    @Test
    fun `assert actual content against approved and write actual content to test.actual.txt`() {
        ResourcesApproval.assertApproved(
            actual = "test", 
            approved = "test.txt",
            writeTo = Actual
        )    
    }
    
    @Test
    fun `assert actual content against approved and write actual content to test.txt`() {
        ResourcesApproval.assertApproved(
            actual = "test", 
            approved = "test.txt",
            writeTo = Approved
        )
    }    
}
```

