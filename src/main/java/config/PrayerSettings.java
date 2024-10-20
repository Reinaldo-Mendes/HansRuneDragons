package config;

import org.dreambot.api.methods.prayer.Prayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrayerSettings {
    private static PrayerSettings prayerSettings = new PrayerSettings();
    public static PrayerSettings getPrayerSettings(){
        return prayerSettings;
    }
    private static void setPrayerSettings(PrayerSettings prayerSettings){
        PrayerSettings.prayerSettings = prayerSettings;
    }
    private PrayerSettings(){

    }
    private boolean flickPrayer = false;
    private boolean useQuickPrayer = true;
    private int minPrayerToDrinkPotion = 20;
    private int maxPrayerToDrinkPotion = 60;
    private List<Prayer> selectedPrayers = new ArrayList<>(Arrays.asList(Prayer.PIETY, Prayer.PROTECT_FROM_MAGIC));

    public boolean isFlickPrayer() {
        return flickPrayer;
    }

    public void setFlickPrayer(boolean flickPrayer) {
        this.flickPrayer = flickPrayer;
    }

    public boolean isUseQuickPrayer() {
        return useQuickPrayer;
    }

    public void setUseQuickPrayer(boolean useQuickPrayer) {
        this.useQuickPrayer = useQuickPrayer;
    }

    public int getMinPrayerToDrinkPotion() {
        return minPrayerToDrinkPotion;
    }

    public void setMinPrayerToDrinkPotion(int minPrayerToDrinkPotion) {
        this.minPrayerToDrinkPotion = minPrayerToDrinkPotion;
    }

    public int getMaxPrayerToDrinkPotion() {
        return maxPrayerToDrinkPotion;
    }

    public void setMaxPrayerToDrinkPotion(int maxPrayerToDrinkPotion) {
        this.maxPrayerToDrinkPotion = maxPrayerToDrinkPotion;
    }

    public List<Prayer> getSelectedPrayers() {
        return selectedPrayers;
    }

    public void setSelectedPrayers(List<Prayer> selectedPrayers) {
        this.selectedPrayers = selectedPrayers;
    }
}
