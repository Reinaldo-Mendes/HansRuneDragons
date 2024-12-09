package config;

public class MuleSettings {

    public MuleSettings(){

    }

    private int muleAtXAmountOfLoot = 1000000;
    private int amountOfCoinsToKeep;
    private boolean muleAtCertainTime;

    public int getMuleAtXAmountOfLoot() {
        return muleAtXAmountOfLoot;
    }

    public void setMuleAtXAmountOfLoot(int muleAtXAmountOfLoot) {
        this.muleAtXAmountOfLoot = muleAtXAmountOfLoot;
    }
}
