package behaviour.killDragon;

import config.GlobalVariables;
import framework.Leaf;
import org.dreambot.api.data.consumables.Food;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.items.GroundItem;
import org.dreambot.api.wrappers.items.Item;
import utilities.Timing;

import java.util.Arrays;
import java.util.List;

import static org.dreambot.api.utilities.Logger.log;

public class LootFoodLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return foodOnGround() && Inventory.getEmptySlots() >= 1;
    }

    @Override
    public int onLoop() {
        log("Loot food leaf");
        GroundItem food = GroundItems.closest(this::isFood);
        if(food != null){
            if(food.interact("Take")){
                Sleep.sleepUntil(() -> GlobalVariables.lootedFood, 1500, 100);
                GlobalVariables.lootedFood = false;
            }
        } else{
            log("Food is null!");
        }
        return Timing.loopReturn();
    }

    private boolean foodOnGround(){
        return GroundItems.closest(this::isFood) != null;
    }

    private boolean isFood(GroundItem item){
        return Arrays.stream(Food.values())
                .anyMatch(food -> Arrays.asList(food.getNames()).contains(item.getName()));
    }
}
