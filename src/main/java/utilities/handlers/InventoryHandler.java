package utilities.handlers;

import config.ScriptConfiguration;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.wrappers.items.Item;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.dreambot.api.utilities.Logger.log;

public class InventoryHandler {

    public static boolean containsAny(List<String> itemList) {
        for (String itemInList : itemList) {
            if (Inventory.contains(itemInList)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsItemList(List<String> itemList) {
        for (String itemInList : itemList) {
            if (!Inventory.contains(itemInList)) {
                return false;
            }
        }
        return true;
    }

    public static boolean containsHashMap(HashMap<String, Integer> items){
        boolean value = true;
        for (Map.Entry<String, Integer> entry: items.entrySet()){
            String name = entry.getKey();
            int amount = entry.getValue() ;
            if(Inventory.contains(i -> i.getName().contains(name))){
                if(Inventory.count(i -> i.getName().contains(name)) != amount){
                    value = false;
                }
            } else{
                value = false;
            }
        }
        return value;
    }

    public static boolean interact(String itemName, String action) {
        Item item = Inventory.get(itemName);
        if (item != null) {
            if (item.hasAction(action)) {
                if (item.interact(action)) {
                    return true;
                }
            } else {
                log(Color.red, "[INVENTORY HANDLER]" + itemName + " doesn't have action " + action);
            }
        } else {
            log(Color.RED, "[INVENTORY HANDLER] Item " + itemName + " is null");
        }
        return false;
    }

    public static List<String> getMissingLoadoutItemsFromBankAndInventory(HashMap<String, Integer> loadout){
        // Mapeia os itens no inventário e banco por nome base e quantidade
        Map<String, Integer> availableItems = new HashMap<>();

        // Adiciona itens do inventário
        Inventory.all().stream()
                .filter(item -> item != null && item.getName() != null)
                .forEach(item -> addItemToMap(availableItems, item));

        // Adiciona itens do banco
        Bank.all().stream()
                .filter(item -> item != null && item.getName() != null)
                .forEach(item -> addItemToMap(availableItems, item));

        // Lista de itens que faltam
        List<String> missingItems = new ArrayList<>();

        // Verifica cada item e quantidade do loadout necessário
        ScriptConfiguration.getScriptConfiguration().getInventoryLoadout().forEach((baseName, requiredAmount) -> {
            int availableAmount = getAvailableAmount(availableItems, baseName);
            if (availableAmount < requiredAmount) {
                missingItems.add(baseName + " (faltam: " + (requiredAmount - availableAmount) + ")");
            }
        });

        return missingItems;
    }

    public static List<String> getMissingLoadoutItems(HashMap<String, Integer> loadout){
        // Mapeia os itens no inventário e banco por nome base e quantidade
        Map<String, Integer> availableItems = new HashMap<>();

        // Adiciona itens do inventário
        Inventory.all().stream()
                .filter(item -> item != null && item.getName() != null)
                .forEach(item -> addItemToMap(availableItems, item));

        // Lista de itens que faltam
        List<String> missingItems = new ArrayList<>();

        // Verifica cada item e quantidade do loadout necessário
        ScriptConfiguration.getScriptConfiguration().getInventoryLoadout().forEach((baseName, requiredAmount) -> {
            int availableAmount = getAvailableAmount(availableItems, baseName);
            if (availableAmount < requiredAmount) {
                missingItems.add(baseName + " (faltam: " + (requiredAmount - availableAmount) + ")");
            }
        });

        return missingItems;
    }

    private static void addItemToMap(Map<String, Integer> map, Item item) {
        String baseName = item.getName().split(" \\(")[0].trim();
        map.merge(baseName, item.getAmount(), Integer::sum);
    }

    private static int getAvailableAmount(Map<String, Integer> availableItems, String baseName) {
        return availableItems.entrySet().stream()
                .filter(entry -> entry.getKey().trim().startsWith(baseName.trim())) // Verifica se o nome base corresponde
                .mapToInt(Map.Entry::getValue) // Soma as quantidades
                .sum();
    }

    private static int getCountInInventoryAndBank(String itemName){
        return Inventory.count(itemName) + Bank.count(itemName);
    }

}
