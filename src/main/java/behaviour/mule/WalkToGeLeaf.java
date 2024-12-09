package behaviour.mule;

import framework.Leaf;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.container.impl.bank.BankMode;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.utilities.Sleep;
import utilities.API;
import utilities.Timing;
import utilities.handlers.BankHandler;

import static org.dreambot.api.utilities.Logger.log;

public class WalkToGeLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return !BankLocation.GRAND_EXCHANGE.getArea(15).contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        if(!Inventory.contains(i -> i.getName().contains("Ring of wealth ("))){
            log("Need to grab ring of wealth");
            if(BankHandler.withdrawItem("Ring of wealth (", BankMode.ITEM)){
                Sleep.sleepUntil(() -> Inventory.contains(i -> i.getName().contains("Ring of wealth (")),2000,100);
            } else{
                log("Failed to withdraw ring of wealth");
            }
        } else{
            API.status = "Walking to GE";
            Walking.walk(BankLocation.GRAND_EXCHANGE.getArea(15).getRandomTile());
        }
        return Timing.loopReturn();
    }
}
