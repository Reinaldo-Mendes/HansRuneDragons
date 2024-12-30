package behaviour.mule;

import config.GlobalVariables;
import framework.Leaf;
import mule.MulingInformation;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.trade.Trade;
import org.dreambot.api.methods.world.Worlds;
import org.dreambot.api.methods.worldhopper.WorldHopper;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.Player;
import utilities.MuleAPI;
import utilities.Timing;

import static behaviour.mule.MuleBranch.hasSentNotificationToMule;
import static behaviour.mule.MuleBranch.muleInformation;
import static org.dreambot.api.utilities.Logger.log;
import static org.dreambot.api.utilities.Sleep.sleepUntil;

public class GiveCoins extends Leaf {
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int onLoop() {
        log("Current node: GiveCoins xD");
        log("Mule name: "+GlobalVariables.muleName);
        if(!hasSentNotificationToMule){
            log("We havent sent notification to mule.");
            MulingInformation botInformation = new MulingInformation();
            botInformation.setRsn(Players.getLocal().getName());
            botInformation.setWorld(Worlds.getCurrentWorld());
            botInformation.setValue(Inventory.count("Coins"));
            botInformation.setRequestType("DEPOSIT");
            botInformation.setMessage("We would like to give you some money!");
            Area westGeMulingArea = new Area(3155, 3492, 3166, 3485);
            int[] definedCoordinates = new int[3];
            Tile definedTile = westGeMulingArea.getCenter();
            definedCoordinates[0] = definedTile.getX();
            definedCoordinates[1] = definedTile.getY();
            definedCoordinates[2] = definedTile.getZ();
            botInformation.setPositionCoordinates(definedCoordinates);
            if(MuleAPI.sendMulingInformation(botInformation)){
                hasSentNotificationToMule = true;
                log("We sent information to mule.");
            } else{
                log("Failed to send muling information xD");
            }
        } else{
            log("We have already sent notification to mule");
            Sleep.sleep(1000,2000);
            if(muleInformation == null){
                muleInformation = MuleAPI.readMulingInformation();
                log("Trying to read mule info now");
            } else{
                log("Mule has sent their object to us!");
                log("Mule name: "+muleInformation.getRsn());
                GlobalVariables.muleName = muleInformation.getRsn();
            }
        }
        if(!GlobalVariables.muleName.equals("null")){
            log("Our mule already gave us a name... We should trade ;)");
            if(Worlds.getCurrentWorld() == muleInformation.getWorld()){
                tradeCoins();
            } else{
                if(WorldHopper.hopWorld(muleInformation.getWorld())){
                    Sleep.sleepUntil(() -> Worlds.getCurrentWorld() == muleInformation.getWorld(),5000);
                }
            }

        } else{
            log("Mule name is null hahaha");
        }
        log("Mule name: "+GlobalVariables.muleName);
        return Timing.loopReturn();
    }

    private void tradeCoins() {
        if (Trade.isOpen(2)) {
            if (Trade.acceptTrade()) {
                log("Accepted second screen.");
                sleepUntil(() -> !Trade.isOpen(), 10000, 100);

            }
        }
        if (Trade.isOpen(1)) {
            if (!Trade.contains(true, "Coins")) {
                if (Inventory.contains("Coins")) {
                    if (Trade.addItem("Coins", Inventory.count("Coins"))) {
                        log("Added coins to trade");
                        sleepUntil(() -> Trade.contains(true, "Coins"), 10000, 100);

                    }
                }
            }
            if(Trade.acceptTrade()){
                sleepUntil(() -> Trade.isOpen(2), 10000, 100);

            }
        }
        if(!Trade.isOpen()){
            Player mule = Players.closest(GlobalVariables.muleName);
            if (mule != null) {
                if (mule.interact("Trade with")) {
                    log("Successfully interacted (Trade) with bot.");
                    sleepUntil(() -> Trade.isOpen() || GlobalVariables.wishesToTrade, 30000, 100);

                }
            } else{
                log("Our mule player "+GlobalVariables.muleName+" is currently null");
            }
        }
    }



}
