package rs.dusk.engine.path.strat

import rs.dusk.engine.entity.Size
import rs.dusk.engine.map.Tile
import rs.dusk.engine.path.TargetStrategy

/**
 * Checks if on an exact tile
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since May 18, 2020
 */
data class TileTargetStrategy(
    override val tile: Tile,
    override val size: Size = Size.TILE
) : TargetStrategy {

    override fun reached(currentX: Int, currentY: Int, plane: Int, size: Size): Boolean {
        return tile.equals(currentX, currentY, plane)
    }
}