package utilities;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.PrayerSettings;
import config.ScriptConfiguration;
import main.Main;
import org.dreambot.api.methods.combat.CombatStyle;
import org.dreambot.api.methods.prayer.Prayer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.dreambot.api.utilities.Logger.log;


public class FileUtility {

    public static boolean saveProfile(String filePath) {
        log("Save profile method - Path: " + filePath);
        File file = new File(filePath);

        // Garantir que o diretório existe
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            if (parentDir.mkdirs()) {
                log("Created parent directories for the file");
            } else {
                log("Failed to create parent directories");
                return false;
            }
        }

        // Verificar e deletar arquivo existente
        if (file.exists()) {
            log("File exists!");
            if (file.delete()) {
                log("Deleted current configuration file");
            } else {
                log("Failed to delete existing file");
                return false;
            }
        } else {
            log("File does not exist.");
        }

        try {
            log("Trying to create new file...");
            if (file.createNewFile()) {
                log("Created new configuration file");
            } else {
                log("Failed to create new configuration file");
                return false;
            }

            // Escrever o conteúdo no arquivo com try-with-resources
            Gson gson = new GsonBuilder().setPrettyPrinting()
                    .disableHtmlEscaping()
                    .create();
            try (Writer writer = Files.newBufferedWriter(Paths.get(filePath))) {
                gson.toJson(ScriptConfiguration.getScriptConfiguration(), writer);
            }

            log("Created file at " + filePath);
            return true;
        } catch (IOException e) {
            log("Error during file save: " + e.getMessage());
            e.printStackTrace();
        }

        log("Save profile method returned false");
        return false;
    }

    public static boolean readProfile(String filePath) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Prayer.class, new PrayerDeserializer())
                .create();
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath))) {

            ScriptConfiguration.setScriptConfiguration(gson.fromJson(reader, ScriptConfiguration.class));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false; 
    }


    public static boolean readFileToList(String filePath, List<String> list){
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void initializeDefaultProfile() {
        ScriptConfiguration.getScriptConfiguration().setPlayersToHop(3);
        ScriptConfiguration.getScriptConfiguration().setMinRestockNumber(100);
        ScriptConfiguration.getScriptConfiguration().setMaxRestockNumber(150);
        ScriptConfiguration.getScriptConfiguration().setItemsToLoot(new ArrayList<>(Arrays.asList("Dragon bones","Runite bar", "Rune platebody","Rune longsword","Rune mace","Rune scimitar","Rune warhammer","Rune platelegs",
                "Dragon platelegs","Dragon plateskirt","Dragon med helm","Wrath rune","Chaos rune","Death rune","Rune javelin heads","Runite bolts (unf)","Dragonstone","Runite ore",
                "Dragon javelin heads","Dragon bolts (unf)","Loop half of key","Tooth half of key", "Rune 2h sword","Rune battleaxe","Rune sq shield","Law rune","Rune kiteshield","Rune spear",
                "Shield left half","Dragon spear","Brimstone key","Dragon limbs","Dragon metal lump","Draconic visage")));
        ScriptConfiguration.getScriptConfiguration().setEquipmentToWear(new ArrayList<>(Arrays.asList("Osmumten's fang", "Justiciar faceguard", "Justiciar chestguard", "Justiciar legguards", "Amulet of fury", "Berserker ring (i)",
                "Avernic defender", "Insulated boots", "Obsidian cape", "Peaceful blessing", "Barrows gloves")));
        ScriptConfiguration.getScriptConfiguration().setInventoryLoadout(new HashMap<String, Integer>() {{
            put("Divine super combat potion(2)", 1);
            put("Extended super antifire(2)", 1);
            put("Ring of dueling", 1);
            put("Prayer potion(4)", 4);
            put("Digsite pendant ", 1);
            put("Monkfish", 17);
            put("Cooked karambwan", 3);
        }});

        ScriptConfiguration.getScriptConfiguration().getPrayerSettings().setFlickPrayer(false);
        ScriptConfiguration.getScriptConfiguration().getPrayerSettings().setUseQuickPrayer(true);
        ScriptConfiguration.getScriptConfiguration().getPrayerSettings().setMinPrayerToDrinkPotion(13);
        ScriptConfiguration.getScriptConfiguration().getPrayerSettings().setMaxPrayerToDrinkPotion(45);
        ScriptConfiguration.getScriptConfiguration().getPrayerSettings().setSelectedPrayers(new ArrayList<>(Arrays.asList(Prayer.PIETY, Prayer.PROTECT_FROM_MAGIC)));

        ScriptConfiguration.getScriptConfiguration().getCombatSettings().setCombatStyle(CombatStyle.ATTACK);
        ScriptConfiguration.getScriptConfiguration().getCombatSettings().setEnableAutoRetaliate(true);
        ScriptConfiguration.getScriptConfiguration().getCombatSettings().setUseSpecialAttack(false);
        ScriptConfiguration.getScriptConfiguration().getCombatSettings().setMinPercentageToSpecialAttack(0);
        ScriptConfiguration.getScriptConfiguration().getCombatSettings().setMaxPercentageToSpecialAttack(0);
        ScriptConfiguration.getScriptConfiguration().getCombatSettings().setMinHpToEAt(50);
        ScriptConfiguration.getScriptConfiguration().getCombatSettings().setMaxHpToEat(68);
        ScriptConfiguration.getScriptConfiguration().getCombatSettings().setEmergencyTeleportHp(25);

        ScriptConfiguration.getScriptConfiguration().getMuleSettings().setMuleAtXAmountOfLoot(10000000);

        ScriptConfiguration.getScriptConfiguration().getWebhookSettings().setURL("Your discord webhook URL");
        ScriptConfiguration.getScriptConfiguration().getWebhookSettings().setMinutesBetweenMessages(60);
        ScriptConfiguration.getScriptConfiguration().getWebhookSettings().setSendOnLevelUp(false);
        ScriptConfiguration.getScriptConfiguration().getWebhookSettings().setSendOnRareDrop(false);
        ScriptConfiguration.getScriptConfiguration().getWebhookSettings().setSendOnMuleEvent(false);
        FileUtility.saveProfile(Main.hansRuneDragonFilePath+"\\default.txt");
    }
}
