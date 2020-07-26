package rs.dusk.engine.client.ui

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import rs.dusk.engine.client.ui.Interfaces.Companion.ROOT_ID
import rs.dusk.engine.client.ui.Interfaces.Companion.ROOT_INDEX

internal class InterfaceCloseChildrenTest : InterfaceTest() {

    @Test
    fun `Close children`() {
        interfaces[0] = Interface(id = 0, data = InterfaceData(fixedParent = 1, fixedIndex = ROOT_INDEX))
        interfaces[1] = Interface(id = 1, data = InterfaceData(fixedParent = ROOT_ID, fixedIndex = ROOT_INDEX))
        manager.open(1)
        manager.open(0)
        manager.closeChildren(1)
        assertTrue(manager.contains(1))
        assertFalse(manager.contains(0))
    }

    @Test
    fun `Close children's children`() {
        interfaces[0] = Interface(id = 0, data = InterfaceData(fixedParent = 1, fixedIndex = ROOT_INDEX))
        interfaces[1] = Interface(id = 1, data = InterfaceData(fixedParent = 2, fixedIndex = ROOT_INDEX))
        interfaces[2] = Interface(id = 2, data = InterfaceData(fixedParent = ROOT_ID, fixedIndex = ROOT_INDEX))
        manager.open(2)
        manager.open(1)
        manager.open(0)
        manager.closeChildren(2)
        assertTrue(manager.contains(2))
        assertFalse(manager.contains(1))
        assertFalse(manager.contains(0))
    }

}