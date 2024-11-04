package utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.ScriptConfiguration;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.methods.world.Worlds;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class FileUtility {

    public static boolean saveProfile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Deleted current configuration file");
            }
        }
        try {
            if (file.createNewFile()) {
                System.out.println("Created new configuration file");
            } else {
                System.out.println("Failed to create new configuration file");
            }
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            Writer writer = Files.newBufferedWriter(Paths.get(filePath));
            gson.toJson(ScriptConfiguration.getScriptConfiguration(), writer);
            writer.close();
            System.out.println("Created file at "+filePath);
            return true;
        } catch (IOException e) {
            //System.out.println(e.getMessage());
        }
        return false;
    }


    public static boolean readProfile(String filePath) {
        Gson gson = new Gson();
        try {
            Reader reader = Files.newBufferedReader(Paths.get(filePath));
            if (reader != null) {
                ScriptConfiguration.setScriptConfiguration(gson.fromJson(reader, ScriptConfiguration.class));
                return true;
            }
        } catch (IOException e) {
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

        FileUtility.saveProfile( System.getProperty("user.dir")+"\\default.txt");
    }
}
