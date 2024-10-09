package behaviour.attachDigsitePendant;

import framework.Leaf;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.BankMode;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.settings.PlayerSettings;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.Item;
import utilities.Areas;
import utilities.Timing;
import utilities.handlers.BankHandler;

import static org.dreambot.api.utilities.Logger.log;

public class AttachDigsitePendantLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return PlayerSettings.getBitValue(6142) == 0;
    }

    @Override
    public int onLoop() {
            Item pendant = Inventory.get(i -> i.getName().contains("Digsite pendant"));
            if(pendant == null){
                BankHandler.withdrawItem("Digsite pendant", BankMode.ITEM);
            } else{
                GameObject strangeMachine = GameObjects.closest(g -> g.getName().equals("Strange Machine") && g.getTile().equals(new Tile(1580,5101)));
                if(strangeMachine != null && strangeMachine.canReach()){
                    pendant.useOn(strangeMachine);
                    Sleep.sleepUntil(() -> PlayerSettings.getBitValue(6142) == 1, 2000, 100);
                } else{
                    Walking.walk(Areas.STRANGE_MACHINE);
                }
            }


        return Timing.loopReturn();
    }
}
