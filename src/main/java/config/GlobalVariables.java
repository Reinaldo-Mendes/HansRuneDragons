package config;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.grandexchange.LivePrices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GlobalVariables {
    public static String muleName = "null";
    public static int nextPraySip = 30;
    public static int nextFoodEat = 68;
    public static int tripsToRestock;
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
    public static HashMap<String, Integer> inventoryLoadoutBuyList;
    public static HashMap<String, Integer> digsitePendantIngredients;
    public static boolean wishesToTrade = false;

    static {
        // Inicialize o HashMap no bloco estático de forma segura
        inventoryLoadoutBuyList = new HashMap<>();
        inventoryLoadoutBuyList.put("Divine super combat potion(2)", 1);
        inventoryLoadoutBuyList.put("Extended super antifire(2)", 1);
        inventoryLoadoutBuyList.put("Ring of dueling(8)", 1);
        inventoryLoadoutBuyList.put("Prayer potion(4)", 4);
        inventoryLoadoutBuyList.put("Monkfish", 17);
        inventoryLoadoutBuyList.put("Cooked karambwan", 3);

        digsitePendantIngredients = new HashMap<>();
        digsitePendantIngredients.put("Cosmic rune", 1);
        digsitePendantIngredients.put("Ruby necklace", 1);
    }

    public static void cacheLootedItemsList() {
        lootedItems = new ArrayList<>();
        List<String> itemsToLoot = ScriptConfiguration.getScriptConfiguration().getItemsToLoot();
        if (itemsToLoot != null) {
            for (String itemName : itemsToLoot) {
                LootItem item = new LootItem();
                item.setName(itemName);
                item.setAmount(0);
                item.setPrice(LivePrices.get(itemName));
                lootedItems.add(item);
            }
        }
        tripsToRestock = Calculations.random(
                ScriptConfiguration.getScriptConfiguration().getMinRestockNumber(),
                ScriptConfiguration.getScriptConfiguration().getMaxRestockNumber()
        );
    }
}
