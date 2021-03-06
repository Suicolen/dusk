package rs.dusk.engine.client.ui.io

import rs.dusk.engine.client.ui.InterfaceSendTest
import rs.dusk.engine.client.ui.detail.InterfaceComponentDetail

internal class InterfaceVisibilityTest : InterfaceSendTest() {

    override fun sendStrings(name: String, component: String): Boolean {
        return manager.sendVisibility(name, component, true)
    }

    override fun expected(component: InterfaceComponentDetail) {
        io.sendVisibility(component, true)
    }

}
