package behaviour.loadInventory;

import config.ScriptConfiguration;
import framework.Leaf;
import utilities.Timing;
import utilities.handlers.BankHandler;
import utilities.handlers.InventoryHandler;

import static org.dreambot.api.utilities.Logger.log;


public class LoadInventoryLeaf extends Leaf {

    @Override
    public boolean isValid() {
        return !InventoryHandler.containsHashMap(ScriptConfiguration.getScriptConfiguration().getInventoryLoadout());
    }

    @Override
    public int onLoop() {
        log("Contains hash map: "+InventoryHandler.containsHashMap(ScriptConfiguration.getScriptConfiguration().getInventoryLoadout()));
        BankHandler.loadInventory(ScriptConfiguration.getScriptConfiguration().getInventoryLoadout());
        return Timing.loopReturn();
    }

}
