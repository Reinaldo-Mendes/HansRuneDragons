package behaviour.killDragon;

import framework.Leaf;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.settings.PlayerSettings;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.items.Item;
import utilities.Timing;

public class DrinkCombatPotionLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return PlayerSettings.getBitValue(13663) == 0 && Inventory.contains(i -> i.getName().contains("uper combat potion")); // Setting for extended super combat
    }

    @Override
    public int onLoop() {
        Item combatPotion = Inventory.get(i -> i.getName().contains("uper combat potion"));
        if(combatPotion != null){
            if(combatPotion.interact("Drink")){
                Sleep.sleepUntil(() -> PlayerSettings.getBitValue(13663) > 0, 1000, 100);
            }
        }
        return Timing.loopReturn();
    }
}
