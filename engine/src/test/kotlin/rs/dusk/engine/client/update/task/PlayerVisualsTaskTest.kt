package rs.dusk.engine.client.update.task

import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.koin.dsl.module
import rs.dusk.core.io.read.BufferReader
import rs.dusk.core.io.write.BufferWriter
import rs.dusk.engine.client.update.task.player.PlayerVisualsTask
import rs.dusk.engine.entity.character.Character
import rs.dusk.engine.entity.character.player.Player
import rs.dusk.engine.entity.character.player.Players
import rs.dusk.engine.entity.character.update.Visual
import rs.dusk.engine.entity.character.update.VisualEncoder
import rs.dusk.engine.entity.character.update.Visuals
import rs.dusk.engine.entity.list.PooledMapList
import rs.dusk.engine.entity.list.entityListModule
import rs.dusk.engine.event.eventModule
import rs.dusk.engine.script.KoinMock
import rs.dusk.utility.get

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 26, 2020
 */
internal class PlayerVisualsTaskTest : KoinMock() {

    private val encoder: VisualEncoder<Visual> = mockk(relaxed = true)

    init {
        every { encoder.mask } returns 0x8
    }

    private val addMasks = intArrayOf(encoder.mask)
    private val players: PooledMapList<Player> = mockk(relaxed = true)
    private val encoderModule = module {
        single { spyk(
            PlayerVisualsTask(
                players,
                arrayOf(encoder),
                addMasks
            )
        ) }
    }
    override val modules = listOf(eventModule, entityListModule, encoderModule)

    @Test
    fun `Run runs all in parallel`() {
        // Given
        val updateTask: PlayerVisualsTask = get()
        val player: Player = mockk(relaxed = true)
        every { players.forEach(any()) } answers {
            arg<(Character) -> Unit>(0).invoke(player)
        }
        val visuals: Visuals = mockk(relaxed = true)
        every { player.visuals } returns visuals
        // When
        updateTask.run()
        // Then
        coVerify {
            updateTask.runAsync(player)
        }
    }

    @Test
    fun `Update skips if un-flagged`() {
        // Given
        val task: PlayerVisualsTask = get()
        val players: Players = get()
        val visuals: Visuals = mockk(relaxed = true)
        val player: Player = mockk(relaxed = true)
        every { player.visuals } returns visuals
        players.add(0, player)
        // When
        every { visuals.flag } returns 0
        task.runAsync(player)
        // Then
        verify { visuals.update = null }
        verify(exactly = 0) {
            task.encodeUpdate(any())
            task.encodeAddition(any())
        }
    }

    @Test
    fun `Update writes addition if any addMasks changed`() {
        // Given
        val updateTask: PlayerVisualsTask = get()
        val visuals: Visuals = mockk(relaxed = true)
        val player: Player = mockk(relaxed = true)
        every { player.visuals } returns visuals
        every { visuals.flag } returns 1
        every { visuals.flagged(any()) } returns true
        every { updateTask.encodeUpdate(visuals) } just Runs
        every { updateTask.encodeAddition(visuals) } just Runs
        // When
        updateTask.runAsync(player)
        // Then
        verifyOrder {
            updateTask.encodeUpdate(visuals)
            updateTask.encodeAddition(visuals)
            visuals.flag = 0
        }
    }

    @Test
    fun `Update doesn't rewrite addition`() {
        // Given
        val updateTask: PlayerVisualsTask = get()
        val visuals: Visuals = mockk(relaxed = true)
        val player: Player = mockk(relaxed = true)
        every { player.visuals } returns visuals
        every { visuals.flag } returns 1
        every { visuals.flagged(any()) } returns false
        // When
        updateTask.runAsync(player)
        // Then
        verify(exactly = 0) { updateTask.encodeAddition(visuals) }
        verifyOrder {
            updateTask.encodeUpdate(visuals)
            visuals.flag = 0
        }
    }

    @Test
    fun `Encode flagged update`() {
        // Given
        val updateTask: PlayerVisualsTask = get()
        val visuals: Visuals = mockk(relaxed = true)
        val mask = 0x8
        every { visuals.flag } returns mask
        every { visuals.flagged(mask) } returns true
        every { visuals.aspects[any()] } returns mockk(relaxed = true)
        // When
        updateTask.encodeUpdate(visuals)
        // Then
        verifyOrder {
            updateTask.writeFlag(any(), 0x8)
            encoder.encode(any(), any())
            visuals.update = any()
        }
    }

    @Test
    fun `Encode ignores not flagged update`() {
        // Given
        val updateTask: PlayerVisualsTask = get()
        val visuals: Visuals = mockk(relaxed = true)
        val mask = 0x8
        every { visuals.flag } returns mask
        every { visuals.flagged(mask) } returns false

        // When
        updateTask.encodeUpdate(visuals)
        // Then
        verify {
            updateTask.writeFlag(any(), 0x8)
            visuals.update = any()
        }
        verify(exactly = 0) {
            encoder.encode(any(), any())
        }
    }

    @Test
    fun `Encode addition`() {
        // Given
        val updateTask: PlayerVisualsTask = get()
        val visuals: Visuals = mockk(relaxed = true)
        every { visuals.aspects[any()] } returns mockk(relaxed = true)
        // When
        updateTask.encodeAddition(visuals)
        // Then
        verifyOrder {
            updateTask.writeFlag(any(), addMasks.sum())
            encoder.encode(any(), any())
            visuals.addition = any()
        }
    }

    @Test
    fun `Write small flag`() {
        // Given
        val updateTask: PlayerVisualsTask = get()
        val writer = BufferWriter()
        // When
        updateTask.writeFlag(writer, 0x10)
        // Then
        val reader = BufferReader(writer.buffer.array())
        assertEquals(0x10, reader.readByte())
    }

    @Test
    fun `Write medium flag`() {
        // Given
        val updateTask: PlayerVisualsTask = get()
        val writer = BufferWriter()
        // When
        updateTask.writeFlag(writer, 0x100)
        // Then
        val reader = BufferReader(writer.buffer.array())
        assertEquals(0x80, reader.readUnsignedByte())
        assertEquals(0x1, reader.readUnsignedByte())
    }

    @Test
    fun `Write large flag`() {
        // Given
        val updateTask: PlayerVisualsTask = get()
        val writer = BufferWriter()
        // When
        updateTask.writeFlag(writer, 0x10000)
        // Then
        val reader = BufferReader(writer.buffer.array())
        assertEquals(0x80, reader.readUnsignedByte())
        assertEquals(0x8, reader.readUnsignedByte())
        assertEquals(0x1, reader.readUnsignedByte())
    }
}