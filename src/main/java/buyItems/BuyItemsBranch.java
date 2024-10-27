package buyItems;

import config.GlobalVariables;
import config.ScriptConfiguration;
import framework.Branch;
import utilities.handlers.BankHandler;
import utilities.handlers.InventoryHandler;

public class BuyItemsBranch extends Branch {
    @Override
    public boolean isValid() {
        return doesntHaveLoadoutItems() || GlobalVariables.isBuyingLoadoutItems;
    }

    public boolean doesntHaveLoadoutItems(){
        if(!InventoryHandler.containsHashMap(ScriptConfiguration.getScriptConfiguration().getInventoryLoadout()) &&
                !BankHandler.containsHashMap(ScriptConfiguration.getScriptConfiguration().getInventoryLoadout())){
            GlobalVariables.isBuyingLoadoutItems = true;
            return true;
        }
        return false;
    }

}
