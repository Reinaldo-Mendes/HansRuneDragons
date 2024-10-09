package behaviour.wearEquipment;

import config.ScriptConfiguration;
import framework.Leaf;
import org.dreambot.api.methods.container.impl.bank.BankMode;
import utilities.handlers.BankHandler;
import utilities.handlers.EquipmentHandler;
import utilities.Timing;
import utilities.handlers.InventoryHandler;

import java.util.List;

import static org.dreambot.api.utilities.Logger.log;

public class WearEquipmentLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return !EquipmentHandler.isWearingItemList(ScriptConfiguration.getScriptConfiguration().getEquipmentToWear());
    }

    @Override
    public int onLoop() {
        List<String> missingItems = EquipmentHandler.missingItems(ScriptConfiguration.getScriptConfiguration().getEquipmentToWear());
        log("Missing items: "+missingItems);
        if(InventoryHandler.containsItemList(missingItems)){
            EquipmentHandler.wearItemList(missingItems);
        } else{
            log("We don't have the items to wear in the inventory...");
            if(BankHandler.withdrawItemList(missingItems, BankMode.ITEM)){
                log("We withdrew our list!");
            } else{
                log("Failed to withdraw our list!");
            }
        }


        return Timing.loopReturn();
    }
}
