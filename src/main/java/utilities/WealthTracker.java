package utilities;

import config.GlobalVariables;
import config.LootItem;
import main.Main;

import java.text.DecimalFormat;

public class WealthTracker {
    private int totalWealth;
    private int wealthGenerated;
    private int wealthGeneratedPerHour;
    private int profit;
    private int expenses;

    public WealthTracker(){

    }

    public void setTotalWealth(int totalWealth) {
        this.totalWealth = totalWealth;
    }

    public int getWealthGenerated() {
        this.wealthGenerated = 0;
        for(LootItem item: GlobalVariables.lootedItems){
            wealthGenerated+= (item.getAmount() * item.getPrice());
        }

        return wealthGenerated;
    }

    public void setWealthGenerated(int wealthGenerated) {
        this.wealthGenerated = wealthGenerated;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    public int getExpenses() {
        return expenses;
    }

    public void setExpenses(int expenses) {
        this.expenses = expenses;
    }

    public int getWealthGeneratedPerHour() {
        return (int) (getWealthGenerated()/ (Main.timer.elapsed()/3600000.0));
    }

    public void setWealthGeneratedPerHour(int wealthGeneratedPerHour) {
        this.wealthGeneratedPerHour = wealthGeneratedPerHour;
    }
}
