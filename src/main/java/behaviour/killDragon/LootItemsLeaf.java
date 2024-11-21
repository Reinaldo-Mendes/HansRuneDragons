package behaviour.killDragon;

import config.GlobalVariables;
import config.ScriptConfiguration;
import framework.Leaf;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.item.GroundItems;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.items.GroundItem;
import org.dreambot.api.wrappers.items.Item;
import utilities.API;
import utilities.Areas;
import utilities.Timing;

import java.util.List;

import static org.dreambot.api.utilities.Logger.log;

public class LootItemsLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return getLootOnGround() != null && Areas.RUNE_DRAGONS.contains(Players.getLocal());
    }

    @Override
    public int onLoop() {
        log("Loot items leaf");
        //log(getAllLootOnGround());
        //simpleLooting();
        hardLooting();
        return Timing.loopReturn();
    }

    private GroundItem getLootOnGround() {
        //log("Items to loot: "+ScriptConfiguration.getScriptConfiguration().getItemsToLoot());
        return GroundItems.closest(f -> ScriptConfiguration.getScriptConfiguration().getItemsToLoot().contains(f.getName()) && Areas.RUNE_DRAGONS.contains(f.getTile()));
    }

    private List<GroundItem> getAllLootOnGround() {
        return GroundItems.all(f -> ScriptConfiguration.getScriptConfiguration().getItemsToLoot().contains(f.getName()) && Areas.RUNE_DRAGONS.contains(f.getTile()));
    }

    private void hardLooting() {
        log("Hard looting method");
        int neededFreeInventorySpaces = getAllLootOnGround().size();
        int slotsToFree = neededFreeInventorySpaces - Inventory.getEmptySlots();
        log("Slots to free: "+slotsToFree);
        if (slotsToFree > 0) {
            log("Slots to free is over 1");
            for (int i = 0; i < slotsToFree; ) {
                log("Current i: "+i);
                if (shouldEatToFreeSlots()) {
                    log("We should eat to free slots.");
                    Item food = Inventory.get(f -> f.getName().equals("Monkfish"));
                    if (food == null) {
                        log("Our current food (monkfish) is null... Let's get any item with 'eat' instead");
                        food = Inventory.get(j -> j.hasAction("Eat"));
                    }
                    if (food != null) {
                        log("Food is not null!");
                        API.status = "Freeing inventory";
                        if (food.interact("Eat")) {
                            Sleep.sleepUntil(() -> !shouldEatToFreeSlots(), 1000, 100);
                        }
                    }
                } else {
                    log("We should not eat... We should drop instead.");
                    Item foodToDrop = Inventory.get(f -> f.hasAction("Eat"));
                    if (foodToDrop != null) {
                        log("Food to drop is not null.");
                        API.status = "Dropping "+foodToDrop.getName();
                        if (foodToDrop.interact("Drop")) {
                            log("We dropped food.. lets return!");
                            return;
                        }
                    } else{
                        log("Food to drop is null! We are returning because we might need to teleport out.");
                        return;
                        /*GroundItem loot = getLootOnGround();
                        if(loot != null){
                            if (loot.interact("Take")) {
                                Sleep.sleepUntil(() -> GlobalVariables.lootedItem, 1500, 100);
                                GlobalVariables.lootedItem = false;
                            }
                        }*/

                    }
                }
            }
        } else {
            log("We dont need to free any slots.");
            for (GroundItem item : getAllLootOnGround()) {
                if (item != null) {
                    log("Current trying to take: "+item.getName());
                    API.status = "Looting "+item.getName();
                    if (item.interact("Take")) {
                        Sleep.sleepUntil(() -> GlobalVariables.lootedItem || Skills.getBoostedLevel(Skill.HITPOINTS) >
                                ScriptConfiguration.getScriptConfiguration().getCombatSettings().getEmergencyTeleportHp(), 5000, 100);
                        GlobalVariables.lootedItem = false;
                    }
                } else {
                    log("Our item is null... Let's return!");
                    return;
                }
            }
        }
    }

    private void simpleLooting() {
        GroundItem loot = getLootOnGround();
        if (loot != null) {
            if (Inventory.getEmptySlots() >= 1) {
                if (loot.interact("Take")) {
                    Sleep.sleepUntil(() -> GlobalVariables.lootedItem, 1500, 100);
                    GlobalVariables.lootedItem = false;
                }
            } else {
                log("Dropping food to loot...");
                Item foodToDrop = Inventory.get(i -> i.hasAction("Eat"));
                if (foodToDrop != null) {
                    if (foodToDrop.interact("Drop")) {
                        return;
                    }
                }
            }
        }
    }

    private boolean shouldEatToFreeSlots() {
        return (Skills.getRealLevel(Skill.HITPOINTS) - Skills.getBoostedLevel(Skill.HITPOINTS)) >= 16 && Inventory.contains(item -> item.hasAction("Eat"));
    }
}
