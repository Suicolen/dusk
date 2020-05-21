package rs.dusk.engine.path

import rs.dusk.engine.model.entity.Direction

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since May 18, 2020
 */
interface ObstructionStrategy {
    fun obstructed(x: Int, y: Int, plane: Int, direction: Direction): Boolean
}