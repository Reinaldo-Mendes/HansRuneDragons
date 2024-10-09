package behaviour.walkToDragons;

import config.ScriptConfiguration;
import framework.Branch;
import org.dreambot.api.methods.interactive.Players;
import utilities.Areas;
import utilities.handlers.EquipmentHandler;
import utilities.handlers.InventoryHandler;

public class WalkToDragonsBranch extends Branch {
    @Override
    public boolean isValid() {
        return InventoryHandler.containsHashMap(ScriptConfiguration.getScriptConfiguration().getInventoryLoadout()) &&
        EquipmentHandler.isWearingItemList(ScriptConfiguration.getScriptConfiguration().getEquipmentToWear()) &&
                !Areas.RUNE_DRAGONS.contains(Players.getLocal());
    }
}
