package behaviour.loadInventory;

import config.ScriptConfiguration;
import framework.Branch;
import utilities.handlers.InventoryHandler;

public class LoadInventoryBranch extends Branch {
    @Override
    public boolean isValid() {
        return !InventoryHandler.containsHashMap(ScriptConfiguration.getScriptConfiguration().getInventoryLoadout());
    }
}
