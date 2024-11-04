package config;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.grandexchange.LivePrices;
import java.util.ArrayList;
import java.util.List;

public class GlobalVariables {
    public static int nextPraySip = 30;
    public static int nextFoodEat = 68;
    public static int tripsToRestock = Calculations.random(ScriptConfiguration.getScriptConfiguration().getMinRestockNumber(), ScriptConfiguration.getScriptConfiguration().getMaxRestockNumber());
    public static List<LootItem> lootedItems;
    public static int killcount = 0;
    public static int currentDragonIndex;
    public static int lastDragonIndex;
    public static boolean lootedItem;
    public static boolean lootedFood;
    public static boolean isSellingLootToMule = false;
    public static boolean isBuyingLoadoutItems = false;
    public static boolean hasInitiatedScript = false;
    public static boolean needPendant = false;


    public static void cacheLootedItemsList(){
        lootedItems = new ArrayList<>();
        for(String itemName: ScriptConfiguration.getScriptConfiguration().getItemsToLoot()){
            LootItem item = new LootItem();
            item.setName(itemName);
            item.setAmount(0);
            item.setPrice(LivePrices.get(itemName));
            lootedItems.add(item);
        }
    }
}
