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
import utilities.Timing;

public class EatFoodLeaf extends Leaf
{
    @Override
    public boolean isValid() {
        return (needToEat() || isLowHp()) && Inventory.get(i -> i.hasAction("Eat")) != null;
    }

    @Override
    public int onLoop() {
        Item primaryFood = Inventory.get(i -> !i.getName().contains("karambwan") && i.hasAction("Eat"));
        if(isLowHp()){
            Item karambwan = Inventory.get(k -> k.getName().equals("Cooked karambwan"));
            if(primaryFood != null && karambwan != null){
                primaryFood.interact("Eat");
                karambwan.interact("Eat");
                GlobalVariables.nextFoodEat = Calculations.random(ScriptConfiguration.getScriptConfiguration().getCombatSettings().getMinHpToEAt(),
                        ScriptConfiguration.getScriptConfiguration().getCombatSettings().getMaxHpToEat());
                return Timing.loopReturn();
            }
        }
        if(primaryFood != null){
            primaryFood.interact("Eat");
            Sleep.sleepUntil(() -> Skills.getBoostedLevel(Skill.HITPOINTS) < ScriptConfiguration.getScriptConfiguration().getCombatSettings().getMinHpToEAt(),
                    500,100); // We sleep for 0.5 seconds or until hp changes if it's still lower than min hp to eat
            GlobalVariables.nextFoodEat = Calculations.random(ScriptConfiguration.getScriptConfiguration().getCombatSettings().getMinHpToEAt(),
                    ScriptConfiguration.getScriptConfiguration().getCombatSettings().getMaxHpToEat());
            return Timing.loopReturn();
        } else{
            Item anyFood = Inventory.get(i -> i.hasAction("Eat"));
            if(anyFood != null){
                anyFood.interact("Eat");
                GlobalVariables.nextFoodEat = Calculations.random(ScriptConfiguration.getScriptConfiguration().getCombatSettings().getMinHpToEAt(),
                        ScriptConfiguration.getScriptConfiguration().getCombatSettings().getMaxHpToEat());
                return Timing.loopReturn();
            }
        }
        return Timing.loopReturn();
    }

    private boolean isLowHp(){
        return Skills.getBoostedLevel(Skill.HITPOINTS) < ScriptConfiguration.getScriptConfiguration().getCombatSettings().getMinHpToEAt();
    }

    private boolean needToEat(){
        return Skills.getBoostedLevel(Skill.HITPOINTS) <= GlobalVariables.nextFoodEat;
    }
}
