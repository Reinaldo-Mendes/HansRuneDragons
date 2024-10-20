package config;

import org.dreambot.api.methods.grandexchange.LivePrices;
import java.util.ArrayList;
import java.util.List;

public class GlobalVariables {
    public static int nextPraySip = 30;
    public static int nextFoodEat = 70;
    public static List<LootItem> lootedItems;
    public static int killcount = 0;
    public static int currentDragonIndex;
    public static int lastDragonIndex;
    public static boolean lootedItem;
    public static boolean lootedFood;


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
