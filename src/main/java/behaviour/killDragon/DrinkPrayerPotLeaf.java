package behaviour.killDragon;

import config.GlobalVariables;
import config.ScriptConfiguration;
import framework.Leaf;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.items.Item;
import utilities.API;
import utilities.Timing;

public class DrinkPrayerPotLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return needPrayer() && Inventory.contains(f -> f.getName().contains("Prayer potion"));

    }

    @Override
    public int onLoop() {
        Item prayerPotion = Inventory.get(p -> p.getName().contains("Prayer potion"));
        if (prayerPotion != null){
            API.status = "Drinking pray pot";
            int currentPray = Skills.getBoostedLevel(Skill.PRAYER);
            prayerPotion.interact("Drink");
            Sleep.sleepUntil(() -> Skills.getBoostedLevel(Skill.PRAYER) > currentPray,1000, 100);
            GlobalVariables.nextPraySip = Calculations.random(ScriptConfiguration.getScriptConfiguration().getPrayerSettings().getMinPrayerToDrinkPotion(),
                    ScriptConfiguration.getScriptConfiguration().getPrayerSettings().getMaxPrayerToDrinkPotion());
        }
        return Timing.loopReturn();
    }

    private boolean needPrayer() {
        return Skills.getBoostedLevel(Skill.PRAYER) <= GlobalVariables.nextPraySip;

    }
}
