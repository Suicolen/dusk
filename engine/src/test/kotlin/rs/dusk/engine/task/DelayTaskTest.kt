package rs.dusk.engine.task

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class DelayTaskTest {

    @Test
    fun `Is time to run when same tick`() {
        // Given
        val task = DelayTask(2, mockk())
        // When
        val result = task.isTimeToRun(2)
        // Then
        assertTrue(result)
    }

    @Test
    fun `Is time to run when less than current tick`() {
        // Given
        val task = DelayTask(2, mockk())
        // When
        val result = task.isTimeToRun(3)
        // Then
        assertTrue(result)
    }

    @Test
    fun `Isn't time to run if great than current tick`() {
        // Given
        val task = DelayTask(2, mockk())
        task.cancel()
        // When
        val result = task.isTimeToRun(0)
        // Then
        assertFalse(result)
    }

    @Test
    fun `Isn't time to run if cancelled`() {
        // Given
        val task = DelayTask(2, mockk())
        task.cancel()
        // When
        val result = task.isTimeToRun(3)
        // Then
        assertFalse(result)
    }

    @Test
    fun `Is time to remove when same tick`() {
        // Given
        val task = DelayTask(2, mockk())
        // When
        val result = task.isTimeToRemove(2)
        // Then
        assertTrue(result)
    }

    @Test
    fun `Is time to remove when less than current tick`() {
        // Given
        val task = DelayTask(2, mockk())
        // When
        val result = task.isTimeToRemove(3)
        // Then
        assertTrue(result)
    }

    @Test
    fun `Isn't time to remove if great than current tick`() {
        // Given
        val task = DelayTask(2, mockk())
        // When
        val result = task.isTimeToRemove(0)
        // Then
        assertFalse(result)
    }

    @Test
    fun `Is time to remove if cancelled`() {
        // Given
        val task = DelayTask(2, mockk())
        task.cancel()
        // When
        val result = task.isTimeToRemove(0)
        // Then
        assertTrue(result)
    }

    @Test
    fun `Run executes task`() {
        // Given
        val block: (Long) -> Unit = mockk(relaxed = true)
        val task = DelayTask(0, block)
        task.cancel()
        // When
        task.run(2)
        // Then
        verify { block.invoke(2) }
    }
}