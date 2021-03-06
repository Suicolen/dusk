package rs.dusk.network.rs.codec.game.encode

import rs.dusk.core.io.Endian
import rs.dusk.core.network.codec.packet.access.PacketWriter
import rs.dusk.network.rs.codec.game.GameMessageEncoder
import rs.dusk.network.rs.codec.game.GameOpcodes.INTERFACE_NPC_HEAD
import rs.dusk.network.rs.codec.game.encode.message.InterfaceHeadNPCMessage

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since August 2, 2020
 */
class InterfaceHeadNPCMessageEncoder : GameMessageEncoder<InterfaceHeadNPCMessage>() {

    override fun encode(builder: PacketWriter, msg: InterfaceHeadNPCMessage) {
        val (id, component, npc) = msg
        builder.apply {
            writeOpcode(INTERFACE_NPC_HEAD)
            writeInt(id shl 16 or component)
            writeShort(npc, order = Endian.LITTLE)
        }
    }
}