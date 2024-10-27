package buyItems;

import config.GlobalVariables;
import config.ScriptConfiguration;
import framework.Leaf;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import utilities.Timing;
import utilities.handlers.BankHandler;
import utilities.handlers.GrandExchangeHandler;
import utilities.handlers.InventoryHandler;

import java.util.HashMap;
import java.util.Map;

import static org.dreambot.api.utilities.Logger.log;

public class BuyItemsLeaf extends Leaf {
    boolean hasStartedHashmap = false;
    @Override
    public boolean isValid() {
        return GlobalVariables.isBuyingLoadoutItems;
    }

    @Override
    public int onLoop() {
        log("buy items leaf");
        HashMap<String, Integer> itemsToBuy = initHashMap(ScriptConfiguration.getScriptConfiguration().getInventoryLoadout());
        if(BankHandler.containsHashMap(itemsToBuy) || InventoryHandler.containsHashMap(itemsToBuy)){
            log("We no longer need to buy our loadout... We already have it in the bank or inventory.");
            GlobalVariables.isBuyingLoadoutItems = false;
            hasStartedHashmap = false;
            return Timing.loopReturn();
        }
        GrandExchangeHandler.buyHashMap(ScriptConfiguration.getScriptConfiguration().getInventoryLoadout(), 25);
        return Timing.loopReturn();
    }

    private HashMap<String, Integer> initHashMap(HashMap<String, Integer> hashmap){
        log("Trips to restock: "+GlobalVariables.tripsToRestock);
        log("init hash map");
        if(!hasStartedHashmap){
            if(hashmap.containsKey("Digsite pendant ")){
                hashmap.remove("Digsite pendant ");
            }
            if(hashmap.containsKey("Ring of dueling")){
                hashmap.remove("Ring of dueling");
                hashmap.put("Ring of dueling(8)", 0);
            }
            for(Map.Entry<String, Integer> entry: hashmap.entrySet()){
                int amountInBank = 0;
                int amountInInventory = 0;
                if(Bank.contains(entry.getKey())){
                    amountInBank = Bank.count(i -> i.getName().contains(entry.getKey()));
                }
                if(Inventory.contains(entry.getKey())){
                    amountInInventory = Inventory.count(i -> i.getName().contains(entry.getKey()));
                }

                entry.setValue((entry.getValue() * GlobalVariables.tripsToRestock) - (amountInBank + amountInInventory));
                //entry.setValue((ScriptConfiguration.getScriptConfiguration().getInventoryLoadout().get(entry.getKey()) * GlobalVariables.tripsToRestock )- (amountInBank + amountInInventory));
                //hashmap.put(entry.getKey(),(ScriptConfiguration.getScriptConfiguration().getInventoryLoadout().get(entry.getKey()) * GlobalVariables.tripsToRestock )- (amountInBank + amountInInventory));
                /*if(entry.getKey().contains("Digsite")){
                    entry.setValue(0);
                }
                if(entry.getKey().contains("dueling")){
                    int value = entry.getValue();
                    hashmap.remove(entry.getKey());
                    hashmap.put("Ring of dueling(8)", value);
                }*/
                log("We need to buy "+entry.getValue()+" of item "+entry.getKey());
                hasStartedHashmap = true;
            }
        }

        return hashmap;
    }
}
