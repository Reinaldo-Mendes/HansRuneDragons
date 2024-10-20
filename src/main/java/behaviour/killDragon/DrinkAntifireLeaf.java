package behaviour.killDragon;

import config.ScriptConfiguration;
import framework.Leaf;
import org.dreambot.api.methods.combat.Combat;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.items.Item;
import utilities.Timing;
public class DrinkAntifireLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return !isAntifireEnabled();
    }

    @Override
    public int onLoop() {
        Item antifire = Inventory.get(i -> i.getName().contains("antifire"));
        if (antifire != null){
            antifire.interact("Drink");
            Sleep.sleepUntil(() -> isAntifireEnabled(),1000,100);
        }
        return Timing.loopReturn();
    }

    private boolean isAntifireEnabled(){
        boolean containsKey = ScriptConfiguration.getScriptConfiguration().getInventoryLoadout().keySet()
                .stream()
                .anyMatch(key -> key.contains("super antifire"));
        if(containsKey){
            return Combat.isSuperAntiFireEnabled();
        }
        return Combat.isAntiFireEnabled();
    }


}
