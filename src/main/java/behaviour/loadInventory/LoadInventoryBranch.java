package behaviour.loadInventory;

import config.ScriptConfiguration;
import framework.Branch;
import org.dreambot.api.methods.interactive.Players;
import utilities.Areas;
import utilities.handlers.BankHandler;
import utilities.handlers.InventoryHandler;

public class LoadInventoryBranch extends Branch {
    @Override
    public boolean isValid() {
        return !InventoryHandler.containsHashMap(ScriptConfiguration.getScriptConfiguration().getInventoryLoadout()) &&
                //BankHandler.containsHashMap(ScriptConfiguration.getScriptConfiguration().getInventoryLoadout()) &&
                !Areas.RUNE_DRAGONS.contains(Players.getLocal());
    }
}
