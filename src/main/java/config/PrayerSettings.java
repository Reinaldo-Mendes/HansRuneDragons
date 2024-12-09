package config;

import org.dreambot.api.methods.prayer.Prayer;
import java.util.List;

public class PrayerSettings {
    private boolean flickPrayer;
    private boolean useQuickPrayer;
    private int minPrayerToDrinkPotion;
    private int maxPrayerToDrinkPotion;
    private List<Prayer> selectedPrayers;


    public PrayerSettings() {
    }

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
