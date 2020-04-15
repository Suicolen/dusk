package rs.dusk.engine.entity.factory

import rs.dusk.engine.entity.event.Registered
import rs.dusk.engine.entity.model.FloorItem
import rs.dusk.engine.event.EventBus
import rs.dusk.engine.model.Tile
import rs.dusk.utility.inject

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since March 30, 2020
 */
class FloorItemFactory {

    private val bus: EventBus by inject()

    fun spawn(index: Int, x: Int, y: Int, plane: Int): FloorItem {
        val floorItem = FloorItem(index, Tile(x, y, plane))
        bus.emit(Registered(floorItem))
        return floorItem
    }
}