package config;

import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ScriptConfiguration {
    private static ScriptConfiguration scriptConfiguration = new ScriptConfiguration();

    public static void setScriptConfiguration(ScriptConfiguration scriptConfiguration) {
        ScriptConfiguration.scriptConfiguration = scriptConfiguration;
    }

    private ScriptConfiguration() {
    }

    public static ScriptConfiguration getScriptConfiguration() {
        return scriptConfiguration;
    }

    private int loopTime;
    private int minHpEat;
    private int maxHpEat;
    private int playersToHop;
    private int minRestockNumber;
    private int maxRestockNumber;
    private List<String> equipmentToWear = new ArrayList<>(Arrays.asList("Osmumten's fang", "Justiciar faceguard", "Justiciar chestguard", "Justiciar legguards", "Amulet of fury", "Berserker ring",
            "Avernic defender", "Insulated boots", "Hitpoints cape(t)", "Peaceful blessing", "Barrows gloves"));

    private HashMap<String, Integer> inventoryLoadout = new HashMap<>();
    public int getLoopTime() {
        return loopTime;
    }

    public void setLoopTime(int loopTime) {
        this.loopTime = loopTime;
    }

    public int getMinHpEat() {
        return minHpEat;
    }

    public void setMinHpEat(int minHpEat) {
        this.minHpEat = minHpEat;
    }

    public int getMaxHpEat() {
        return maxHpEat;
    }

    public void setMaxHpEat(int maxHpEat) {
        this.maxHpEat = maxHpEat;
    }

    public int getPlayersToHop() {
        return playersToHop;
    }

    public void setPlayersToHop(int playersToHop) {
        this.playersToHop = playersToHop;
    }

    public int getMinRestockNumber() {
        return minRestockNumber;
    }

    public void setMinRestockNumber(int minRestockNumber) {
        this.minRestockNumber = minRestockNumber;
    }

    public int getMaxRestockNumber() {
        return maxRestockNumber;
    }

    public void setMaxRestockNumber(int maxRestockNumber) {
        this.maxRestockNumber = maxRestockNumber;
    }

    public List<String> getEquipmentToWear() {
        return equipmentToWear;
    }

    public void setEquipmentToWear(List<String> equipmentToWear) {
        this.equipmentToWear = equipmentToWear;
    }

    public HashMap<String, Integer> getInventoryLoadout() {
        return inventoryLoadout;
    }

    public void setInventoryLoadout(HashMap<String, Integer> inventoryLoadout) {
        this.inventoryLoadout = inventoryLoadout;
    }

    public void initInventoryLoadout(){
        inventoryLoadout.put("Divine super combat potion(1)", 1);
        inventoryLoadout.put("Extended super antifire(2)", 1);
        inventoryLoadout.put("Ring of dueling", 1);
        inventoryLoadout.put("Prayer potion(4)", 3);
        inventoryLoadout.put("Digsite pendant ", 1);
        inventoryLoadout.put("Monkfish",21);
    }
}
