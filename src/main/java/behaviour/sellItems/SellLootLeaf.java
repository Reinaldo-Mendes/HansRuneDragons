package behaviour.sellItems;

import config.GlobalVariables;
import config.ScriptConfiguration;
import framework.Leaf;
import org.dreambot.api.methods.container.impl.bank.BankMode;
import utilities.Timing;
import utilities.handlers.BankHandler;
import utilities.handlers.GrandExchangeHandler;
import utilities.handlers.InventoryHandler;

import static org.dreambot.api.utilities.Logger.log;

public class SellLootLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return GlobalVariables.isSellingLootToMule;
    }

    @Override
    public int onLoop() {
        if(!BankHandler.containsAny(ScriptConfiguration.getScriptConfiguration().getItemsToLoot()) && !InventoryHandler.containsAny(ScriptConfiguration.getScriptConfiguration().getItemsToLoot())){
            log("We finished selling our loot...");
            GlobalVariables.isSellingLootToMule = false;
        }
        if(BankHandler.containsAny(ScriptConfiguration.getScriptConfiguration().getItemsToLoot())){
            BankHandler.withdrawAllItemList(ScriptConfiguration.getScriptConfiguration().getItemsToLoot(), BankMode.NOTE);
        } else{
            log("Selling items...");
            GrandExchangeHandler.sellItemList(ScriptConfiguration.getScriptConfiguration().getItemsToLoot(), 30);
        }

        return Timing.loopReturn();
    }
}
