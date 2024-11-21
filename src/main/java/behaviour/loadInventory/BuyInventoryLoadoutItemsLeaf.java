package behaviour.loadInventory;

import config.ScriptConfiguration;
import framework.Leaf;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import utilities.API;
import utilities.Timing;
import utilities.handlers.GrandExchangeHandler;
import utilities.handlers.InventoryHandler;
import utilities.handlers.WalkHandler;

import static org.dreambot.api.utilities.Logger.log;

public class BuyInventoryLoadoutItemsLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return InventoryHandler.getMissingLoadoutItemsFromBankAndInventory(ScriptConfiguration.getScriptConfiguration().getInventoryLoadout()).size() >= 1;
    }

    @Override
    public int onLoop() {
        if(!BankLocation.GRAND_EXCHANGE.getArea(10).contains(Players.getLocal())){
            API.status = "Walking to GE";
            WalkHandler.walkToGe();
        } else{
            log(GrandExchangeHandler.calculateItemsToBuy(ScriptConfiguration.getScriptConfiguration().getInventoryLoadoutBuyList()));
            GrandExchangeHandler.buyHashMap(GrandExchangeHandler.calculateItemsToBuy(
                    ScriptConfiguration.getScriptConfiguration().getInventoryLoadoutBuyList()),30);
        }
        return Timing.loopReturn();
    }
}
