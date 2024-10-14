package behaviour.killDragon;

import config.ScriptConfiguration;
import framework.Leaf;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.items.Item;
import utilities.Timing;

public class EatFoodLeaf extends Leaf
{
    @Override
    public boolean isValid() {
        return isHpBetweenMinAndMax() || isLowHp();
    }

    @Override
    public int onLoop() {
        Item primaryFood = Inventory.get(i -> !i.getName().contains("karambwan") && i.hasAction("Eat"));
        if(isLowHp()){
            Item karambwan = Inventory.get(k -> k.getName().equals("Cooked karambwan"));
            if(primaryFood != null && karambwan != null){
                primaryFood.interact("Eat");
                karambwan.interact("Eat");
                return Timing.loopReturn();
            }
            if(primaryFood != null){
                primaryFood.interact("Eat");
                Sleep.sleepUntil(() -> Skills.getBoostedLevel(Skill.HITPOINTS) < ScriptConfiguration.getScriptConfiguration().getMinHpEat(),
                        500,100); // We sleep for 0.5 seconds or until hp changes if it's still lower than min hp to eat
            }

        }
        return Timing.loopReturn();
    }

    private boolean isLowHp(){
        return Skills.getBoostedLevel(Skill.HITPOINTS) < ScriptConfiguration.getScriptConfiguration().getMinHpEat();
    }

    private boolean isHpBetweenMinAndMax(){
        return Skills.getBoostedLevel(Skill.HITPOINTS) < ScriptConfiguration.getScriptConfiguration().getCombatSettings().getMaxHpToEat() &&
                Skills.getBoostedLevel(Skill.HITPOINTS) > ScriptConfiguration.getScriptConfiguration().getCombatSettings().getMinHpToEAt();
    }
}
