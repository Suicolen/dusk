package rs.dusk.engine.entity.character.update

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 25, 2020
 */
@Suppress("ArrayInDataClass")
data class Visuals(
    var flag: Int = 0,
    var aspects: MutableMap<Int, Visual> = mutableMapOf(),
    var appearance: ByteArray? = null,
    var update: ByteArray? = null,
    var addition: ByteArray? = null
) {

    inline fun <reified T : Visual> getOrPut(mask: Int, put: () -> T): T {
        return aspects.getOrPut(mask, put) as T
    }

    fun flag(mask: Int) {
        flag = flag or mask
    }

    fun flagged(mask: Int): Boolean {
        return flag and mask != 0
    }

    fun clear() {
        flag = 0
        aspects.clear()
        appearance = null
        update = null
        addition = null
    }
}