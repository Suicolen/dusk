package rs.dusk.engine.entity.model.visual.visuals.player

import rs.dusk.engine.entity.model.visual.Visual
import rs.dusk.engine.entity.model.visual.VisualCompanion

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 25, 2020
 */
data class Face(var direction: Int = 0) : Visual {
    companion object : VisualCompanion<Face>()
}