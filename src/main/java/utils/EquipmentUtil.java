package utils;


import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.items.Item;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.dreambot.api.utilities.Logger.log;

public class EquipmentUtil {

    public static boolean compareEquipments(HashMap<EquipmentSlot, String> definedEquipment, List<Item> currentEquipment) {
        List<String> currentEquipmentString = new ArrayList<>();
        for (Item i : currentEquipment) {
            if (i != null) {
                currentEquipmentString.add(i.getName());
            }
        }
        for (String itemName : definedEquipment.values()) {
            if (!currentEquipmentString.contains(itemName)) {
                log("Our current equipment does not contain " + itemName);
                return false;
            }
        }
        return true;
    }

    public static HashMap<EquipmentSlot, String> getWornEquipment() {
        HashMap<EquipmentSlot, String> equipment = new HashMap<>();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (Equipment.getItemInSlot(slot) != null) {
                log("Slot: " + slot + " Equipment: " + Equipment.getItemInSlot(slot).getName());
                equipment.put(slot, Equipment.getItemInSlot(slot).getName());
            }
        }

        return equipment;
    }

    public static List<String> getMissingEquipment(HashMap<EquipmentSlot, String> definedEquipment, List<Item> currentEquipment) {
        List<String> currentEquipmentString = new ArrayList<>();
        List<String> definedEquipmentString = new ArrayList<>();
        List<String> missingItems = new ArrayList<>();
        for (Item i : currentEquipment) {
            if (i != null) {
                currentEquipmentString.add(i.getName());
            }
        }

        for (String name : definedEquipment.values()) {
            if (name != null) {
                definedEquipmentString.add(name);
            }
        }

        for (String item1 : currentEquipmentString) {
            if (!definedEquipmentString.contains(item1)) {
                missingItems.add(item1);
            }
        }

        for (String item2 : definedEquipmentString) {
            if (!currentEquipmentString.contains(item2)) {
                missingItems.add(item2);
            }
        }

        return missingItems;
    }

    public static boolean equipMissingEquipment(List<String> missingItems) {
        boolean equipped = false;
        if (missingItems != null && missingItems.size() >= 1) {
            for (String item : missingItems) {
                if (Inventory.get(item) != null) {
                    if (Inventory.get(item).interact("Wield")) {
                        Sleep.sleepUntil(() -> !Inventory.contains(item), 3000);
                        equipped = true;
                    }
                    if (Inventory.get(item).interact("Wear")) {
                        Sleep.sleepUntil(() -> !Inventory.contains(item), 3000);
                        equipped = true;
                    }
                }
            }
        }
        return equipped;
    }

}
