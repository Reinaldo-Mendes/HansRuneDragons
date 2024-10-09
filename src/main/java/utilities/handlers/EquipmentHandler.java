package utilities.handlers;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.items.Item;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EquipmentHandler {

    public static boolean isWearingItemList(List<String> itemList) {
        boolean value = true;
        List<String> currentEquipment = new ArrayList<>();
        for (Item i : Equipment.all()) {
            if (i != null)
                currentEquipment.add(i.getName());
        }

        for (String itemInList : itemList) {
            if (!currentEquipment.contains(itemInList)) {
                //Logger.log("Our current equipment does not contain "+itemInList);
                value = false;
            }
        }
        return value;
    }

    public static boolean wearItemList(List<String> itemList) {
        boolean value = true;
        for (String itemInList : itemList) {
            Item item = Inventory.get(itemInList);
            if (item != null) {
                if (item.hasAction("Wear")) {
                    if (item.interact("Wear")) {
                        Sleep.sleepUntil(() -> Equipment.all().contains(itemInList), 400, 100);
                    } else {
                        value = false;
                        Logger.log(Color.RED, "[EQUIPMENT HANDLER] Failed to wear " + item.getName());
                    }
                }
                if (item.hasAction("Wield")) {
                    if (item.interact("Wield")) {
                        Sleep.sleepUntil(() -> Equipment.all().contains(itemInList), 400, 100);
                    } else {
                        value = false;
                        Logger.log(Color.RED, "[EQUIPMENT HANDLER] Failed to wield " + item.getName());
                    }
                }

                if(item.hasAction("Equip")){
                    if(item.interact("Equip")){
                        Sleep.sleepUntil(() -> Equipment.all().contains(itemInList), 400, 100);
                    } else{
                        value = false;
                        Logger.log(Color.RED,"[EQUIPMENT HANDLER] Failed to equip "+item.getName());
                    }
                }
            } else {
                Logger.log(Color.red, "[EQUIPMENT HANDLER] Item " + itemInList + " is null");
            }
        }
        return value;
    }

    public static boolean wearItem(String itemName) {
        Item item = Inventory.get(itemName);
        if (item != null) {
            if (item.hasAction("Wear")) {
                if (item.interact("Wear")) {
                    Sleep.sleepUntil(() -> Equipment.all().contains(itemName), 1000, 100);
                    Logger.log(Color.cyan, "[EQUIPMENT HANDLER] Worn " + itemName + " successfully.");
                    return true;
                }
            }
            if (item.hasAction("Wield")) {
                if (item.interact("Wield")) {
                    Sleep.sleepUntil(() -> Equipment.all().contains(itemName), 1000, 100);
                    Logger.log(Color.cyan, "[EQUIPMENT HANDLER] Wielded " + itemName + " successfully.");
                    return true;
                }
            }

        }
        return false;
    }

    public static List<String> missingItems(List<String> itemList) {
        List<String> missingItems = new ArrayList<>();
        List<String> currentEquipment = new ArrayList<>();
        for (Item i : Equipment.all()) {
            if (i != null)
                currentEquipment.add(i.getName());
        }
        for (String itemInList : itemList) {
            if (!currentEquipment.contains(itemInList)) {
                missingItems.add(itemInList);
            }
        }
        return missingItems;
    }


}
