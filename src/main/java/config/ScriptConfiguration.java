package config;
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
    private int playersToHop;
    private int minRestockNumber = 100;
    private int maxRestockNumber = 150;
    private List<String> itemsToLoot = new ArrayList<>(Arrays.asList("Dragon bones","Runite bar", "Rune platebody","Rune longsword","Rune mace","Rune scimitar","Rune warhammer","Rune platelegs",
            "Dragon platelegs","Dragon plateskirt","Dragon med helm","Wrath rune","Chaos rune","Death rune","Rune javelin heads","Runite bolts (unf)","Dragonstone","Runite ore",
            "Dragon javelin heads","Dragon bolts (unf)","Loop half of key","Tooth half of key", "Rune 2h sword","Rune battleaxe","Rune sq shield","Law rune","Rune kiteshield","Rune spear",
            "Shield left half","Dragon spear","Brimstone key","Dragon limbs","Dragon metal lump","Draconic visage"));
    private List<String> equipmentToWear = new ArrayList<>(Arrays.asList("Osmumten's fang", "Justiciar faceguard", "Justiciar chestguard", "Justiciar legguards", "Amulet of fury", "Berserker ring (i)",
            "Avernic defender", "Insulated boots", "Obsidian cape", "Peaceful blessing", "Barrows gloves"));
    private HashMap<String, Integer> inventoryLoadout = new HashMap<>();
    private HashMap<String, Integer> inventoryLoadoutBuyList = new HashMap<>();
    private HashMap<String, Integer> digsitePendantIngredients = new HashMap<>();
    private PrayerSettings prayerSettings;
    private CombatSettings combatSettings;
    private MuleSettings muleSettings;
    public int getLoopTime() {
        return loopTime;
    }

    public void setLoopTime(int loopTime) {
        this.loopTime = loopTime;
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

    public List<String> getItemsToLoot() {
        return itemsToLoot;
    }

    public void setItemsToLoot(List<String> itemsToLoot) {
        this.itemsToLoot = itemsToLoot;
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

    public HashMap<String, Integer> getInventoryLoadoutBuyList() {
        return inventoryLoadoutBuyList;
    }

    public void setInventoryLoadoutBuyList(HashMap<String, Integer> inventoryLoadoutBuyList) {
        this.inventoryLoadoutBuyList = inventoryLoadoutBuyList;
    }

    public HashMap<String, Integer> getDigsitePendantIngredients() {
        return digsitePendantIngredients;
    }

    public void setDigsitePendantIngredients(HashMap<String, Integer> digsitePendantIngredients) {
        this.digsitePendantIngredients = digsitePendantIngredients;
    }

    public PrayerSettings getPrayerSettings() {
        return PrayerSettings.getPrayerSettings();
    }

    public void setPrayerSettings(PrayerSettings prayerSettings) {
        this.prayerSettings = prayerSettings;
    }

    public CombatSettings getCombatSettings() {
        return CombatSettings.getCombatSettings();
    }

    public void setCombatSettings(CombatSettings combatSettings) {
        this.combatSettings = combatSettings;
    }

    public MuleSettings getMuleSettings (){
        return MuleSettings.getMuleSettings();
    }

    public void setMuleSettings(MuleSettings muleSettings){
        this.muleSettings = muleSettings;
    }



    public void initInventoryLoadout(){
        inventoryLoadout.put("Divine super combat potion(2)", 1);
        inventoryLoadout.put("Extended super antifire(2)", 1);
        inventoryLoadout.put("Ring of dueling", 1);
        inventoryLoadout.put("Prayer potion(4)", 4);
        inventoryLoadout.put("Digsite pendant ", 1);
        inventoryLoadout.put("Monkfish",17);
        inventoryLoadout.put("Cooked karambwan",3);
    }

    public void initInventoryLoadoutBuyList(){
        inventoryLoadoutBuyList.put("Divine super combat potion(2)", 1);
        inventoryLoadoutBuyList.put("Extended super antifire(2)", 1);
        inventoryLoadoutBuyList.put("Ring of dueling(8)", 1);
        inventoryLoadoutBuyList.put("Prayer potion(4)", 4);
        inventoryLoadoutBuyList.put("Monkfish",17);
        inventoryLoadoutBuyList.put("Cooked karambwan",3);
    }

    public void initDigsitePendantIngredientsMap(){
        digsitePendantIngredients.put("Cosmic rune",1);
        digsitePendantIngredients.put("Ruby necklace",1);
    }


}
