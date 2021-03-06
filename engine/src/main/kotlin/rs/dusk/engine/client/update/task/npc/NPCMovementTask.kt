package rs.dusk.engine.client.update.task.npc

import kotlinx.coroutines.runBlocking
import rs.dusk.engine.entity.character.move.NPCMoved
import rs.dusk.engine.entity.character.npc.NPC
import rs.dusk.engine.entity.character.npc.NPCMoveType
import rs.dusk.engine.entity.character.npc.NPCs
import rs.dusk.engine.event.EventBus
import rs.dusk.engine.event.Priority.NPC_MOVEMENT
import rs.dusk.engine.map.Tile
import rs.dusk.engine.tick.task.EngineTask

/**
 * Changes the tile npcs are located on based on [Movement.delta] and [Movement.steps]
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 25, 2020
 */
class NPCMovementTask(private val npcs: NPCs, private val bus: EventBus) : EngineTask(NPC_MOVEMENT) {

    override fun run() = runBlocking {
        npcs.forEach { npc ->
            val locked = npc.movement.frozen
            if (!locked) {
                step(npc)
                move(npc)
            }
        }
    }

    /**
     * Sets up walk and run changes based on [Steps] queue.
     */
    fun step(npc: NPC) {
        val movement = npc.movement
        val steps = movement.steps
        if (steps.peek() != null) {
            var step = steps.poll()
            if (!movement.traversal.blocked(npc.tile, step)) {
                movement.walkStep = step
                movement.delta = step.delta
                npc.movementType = NPCMoveType.Walk
                if (movement.running) {
                    if (steps.peek() != null) {
                        val tile = npc.tile.add(step.delta)
                        step = steps.poll()
                        if (!movement.traversal.blocked(tile, step)) {
                            movement.runStep = step
                            movement.delta = movement.delta.add(step.delta)
                            npc.movementType = NPCMoveType.Run
                        }
                    } else {
                        npc.movementType = NPCMoveType.Walk
                    }
                }
            }
        }
    }

    /**
     * Moves the npc tile and emits Moved event
     */
    fun move(npc: NPC) {
        val movement = npc.movement
        if (movement.delta != Tile.EMPTY) {
            val from = npc.tile
            npc.tile = npc.tile.add(movement.delta)
            bus.emit(NPCMoved(npc, from, npc.tile))
        }
    }
}