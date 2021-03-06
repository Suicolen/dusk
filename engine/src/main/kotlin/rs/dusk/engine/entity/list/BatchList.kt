package rs.dusk.engine.entity.list

import rs.dusk.engine.entity.Entity
import rs.dusk.engine.map.Tile
import rs.dusk.engine.map.chunk.Chunk

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since June 04, 2020
 */
interface BatchList<T : Entity> {

    val chunks: MutableMap<Chunk, MutableSet<T>>

    fun add(entity: T) = chunks.getOrPut(entity.tile.chunk) { mutableSetOf() }.add(entity)

    fun remove(entity: T): Boolean {
        val tile = chunks[entity.tile.chunk] ?: return false
        return tile.remove(entity)
    }

    fun clear(chunk: Chunk) {
        chunks.remove(chunk)
    }

    operator fun get(tile: Tile): Set<T> = get(tile.chunk)

    operator fun get(chunk: Chunk): Set<T> = chunks[chunk] ?: emptySet()
}