package behaviour.loadInventory;

import config.ScriptConfiguration;
import framework.Branch;
import org.dreambot.api.methods.interactive.Players;
import utilities.Areas;
import utilities.handlers.InventoryHandler;

import static org.dreambot.api.utilities.Logger.log;

public class LoadInventoryBranch extends Branch {
    @Override
    public boolean isValid() {
        return (!InventoryHandler.containsHashMap(ScriptConfiguration.getScriptConfiguration().getInventoryLoadout()) &&
                !Areas.RUNE_DRAGONS.contains(Players.getLocal())) ||
                InventoryHandler.getMissingLoadoutItemsFromBankAndInventory(ScriptConfiguration.getScriptConfiguration().getInventoryLoadout()).size() >= 1;
    }

}
