package org.redrune.engine.entity.factory

import org.redrune.engine.entity.event.Registered
import org.redrune.engine.entity.model.FloorItem
import org.redrune.engine.entity.tile.Tiles
import org.redrune.engine.event.EventBus
import org.redrune.engine.model.Tile
import org.redrune.utility.inject

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since March 30, 2020
 */
class FloorItemFactory {

    private val bus: EventBus by inject()
    private val tiles: Tiles by inject()

    fun spawn(index: Int, x: Int, y: Int, plane: Int): FloorItem {
        val floorItem = FloorItem(index)
        bus.emit(Registered(floorItem))
        tiles[floorItem] = Tile(x, y, plane)
        return floorItem
    }
}