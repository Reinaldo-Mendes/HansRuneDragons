package behaviour.loadInventory;

import config.ScriptConfiguration;
import framework.Leaf;
import org.dreambot.api.methods.container.impl.Inventory;
import utilities.Timing;
import utilities.handlers.BankHandler;
import utilities.handlers.InventoryHandler;

import static org.dreambot.api.utilities.Logger.log;

public class LoadInventoryLeaf extends Leaf {

    @Override
    public boolean isValid() {
        //return InventoryHandler.getMissingLoadoutItems(ScriptConfiguration.getScriptConfiguration().getInventoryLoadout()).size() >= 1;
        //return !InventoryHandler.containsHashMap(ScriptConfiguration.getScriptConfiguration().getInventoryLoadout());
        return InventoryHandler.getMissingLoadoutItems(ScriptConfiguration.getScriptConfiguration().getInventoryLoadout()).size() >= 1;
    }

    @Override
    public int onLoop() {
        //log("Missing items: "+InventoryHandler.getMissingLoadoutItems(ScriptConfiguration.getScriptConfiguration().getInventoryLoadout()));
        //log("Contains hash map: "+InventoryHandler.containsHashMap(ScriptConfiguration.getScriptConfiguration().getInventoryLoadout()));
        BankHandler.loadInventory(ScriptConfiguration.getScriptConfiguration().getInventoryLoadout());
        log(InventoryHandler.getMissingLoadoutItemsFromBankAndInventory(ScriptConfiguration.getScriptConfiguration().getInventoryLoadout()));
        return Timing.loopReturn();
    }

}
