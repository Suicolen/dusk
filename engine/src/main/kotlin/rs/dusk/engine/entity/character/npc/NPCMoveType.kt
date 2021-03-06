package rs.dusk.engine.entity.character.npc

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since May 15, 2020
 */
sealed class NPCMoveType {
    object None : NPCMoveType()
    object Crawl : NPCMoveType()
    object Walk : NPCMoveType()
    object Run : NPCMoveType()
    object Teleport : NPCMoveType()
}