package behaviour.makeDigsitePendant;

import config.GlobalVariables;
import framework.Leaf;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import utilities.Timing;
import utilities.handlers.GrandExchangeHandler;
import java.util.Arrays;

import static org.dreambot.api.utilities.Logger.log;

public class BuyDigsitePendantIngredients extends Leaf {
    @Override
    public boolean isValid() {
        return getItemCount("Ruby necklace") < GlobalVariables.tripsToRestock ||
                getItemCount("Cosmic rune") < GlobalVariables.tripsToRestock;
    }

    @Override
    public int onLoop() {
        log(GrandExchangeHandler.calculateItemsToBuy(GlobalVariables.digsitePendantIngredients));
        if(!Bank.contains("Staff of fire") && !Inventory.contains("Staff of fire")){
            GrandExchangeHandler.buyItemList(Arrays.asList("Staff of fire"), 50); // Need to create method to buy single item
        }
        GrandExchangeHandler.buyHashMap(GrandExchangeHandler.calculateItemsToBuy(GlobalVariables.digsitePendantIngredients),30);
        return Timing.loopReturn();
    }

    private int getItemCount(String itemName){
        int count = 0;
        if(Inventory.contains(itemName)){
            count+= Inventory.count(itemName);
        }
        if(Bank.contains(itemName)){
            count+= Bank.count(itemName);
        }
        return count;
    }
}
