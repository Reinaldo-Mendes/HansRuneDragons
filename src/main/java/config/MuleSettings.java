package config;

public class MuleSettings {
    private static MuleSettings muleSettings = new MuleSettings();
    public static MuleSettings getMuleSettings(){
        return muleSettings;
    }

    private MuleSettings(){

    }

    private int muleAtXAmountOfLoot = 10000000;
    private int amountOfCoinsToKeep;
    private boolean muleAtCertainTime;

    public int getMuleAtXAmountOfLoot() {
        return muleAtXAmountOfLoot;
    }

    public void setMuleAtXAmountOfLoot(int muleAtXAmountOfLoot) {
        this.muleAtXAmountOfLoot = muleAtXAmountOfLoot;
    }
}
