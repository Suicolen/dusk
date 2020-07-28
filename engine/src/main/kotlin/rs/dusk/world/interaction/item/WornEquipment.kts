package rs.dusk.world.interaction.item

import rs.dusk.engine.client.ui.event.InterfaceInteraction
import rs.dusk.engine.client.ui.open
import rs.dusk.engine.event.on
import rs.dusk.engine.event.then

on(InterfaceInteraction) {
    where {
        name == "worn_equipment" && component == "bonuses" && option == "Show Equipment Stats"
    }
    then {
        player.open("equipment_bonuses")
    }
}

on(InterfaceInteraction) {
    where {
        name == "worn_equipment" && component == "price" && option == "Show Price-checker"
    }
    then {
        player.open("price_checker")
    }
}

on(InterfaceInteraction) {
    where {
        name == "worn_equipment" && component == "items" && option == "Show Items Kept on Death"
    }
    then {
        player.open("items_kept_on_death")
    }
}