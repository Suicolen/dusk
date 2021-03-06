package rs.dusk.world.interact.entity.npc.spawn

import rs.dusk.engine.entity.Direction
import rs.dusk.engine.entity.character.npc.NPC
import rs.dusk.engine.event.Event
import rs.dusk.engine.event.EventCompanion
import rs.dusk.engine.map.Tile

data class NPCSpawn(val id: Int, val tile: Tile, val direction: Direction) : Event<NPC>() {
    companion object : EventCompanion<NPCSpawn>
}