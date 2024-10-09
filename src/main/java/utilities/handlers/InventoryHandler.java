package utilities.handlers;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.wrappers.items.Item;

import java.awt.*;
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

}
