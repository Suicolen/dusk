package rs.dusk.engine.model.entity

import rs.dusk.engine.event.Event
import rs.dusk.engine.event.EventCompanion

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since March 28, 2020
 */
data class Unregistered(val entity: Entity) : Event() {
    companion object : EventCompanion<Unregistered>
}