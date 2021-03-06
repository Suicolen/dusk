import rs.dusk.engine.entity.Registered
import rs.dusk.engine.entity.character.move.NPCMoved
import rs.dusk.engine.entity.character.move.PlayerMoved
import rs.dusk.engine.entity.character.npc.NPCs
import rs.dusk.engine.entity.character.player.Player
import rs.dusk.engine.entity.character.player.PlayerMoveType
import rs.dusk.engine.entity.character.player.Players
import rs.dusk.engine.entity.character.update.visual.player.*
import rs.dusk.engine.event.priority
import rs.dusk.engine.event.then
import rs.dusk.utility.inject

val players: Players by inject()
val npcs: NPCs by inject()

Registered priority 9 then {
    when (entity) {
        is Player -> {
            val entity = entity as Player
            players.add(entity)
            entity.viewport.players.add(entity)
            entity.temporaryMoveType = PlayerMoveType.None
            entity.movementType = PlayerMoveType.None
            entity.flagMovementType()
            entity.flagTemporaryMoveType()
            entity.face()
        }
    }
}

PlayerMoved priority 9 then {
    players.update(from, to, player)
}

NPCMoved priority 9 then {
    npcs.update(from, to, npc)
}