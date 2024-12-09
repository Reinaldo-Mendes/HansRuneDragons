package utilities;

import config.GlobalVariables;
import config.LootItem;
import config.ScriptConfiguration;
import main.Main;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.grandexchange.LivePrices;
import org.dreambot.api.wrappers.items.Item;

import java.util.List;
import java.util.stream.Collectors;

public class WealthTracker {
    private int totalWealth;
    private int wealthGenerated;
    private int wealthGeneratedPerHour;
    private int profit;
    private int expenses;
    private int bankValue;

    public WealthTracker(){

    }

    public void setTotalWealth(int totalWealth) {
        this.totalWealth = totalWealth;
    }

    public int getWealthGenerated() {
        this.wealthGenerated = 0;
        if(GlobalVariables.lootedItems != null){
            for(LootItem item: GlobalVariables.lootedItems){
                if(item != null)
                wealthGenerated+= (item.getAmount() * item.getPrice());
            }
        }
        return wealthGenerated;
    }

    public int getBankValue(){
        int value = 0;
        for(Item i: Bank.all()){
            value+= LivePrices.get(i);
        }
        return value;
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

    public static boolean lootIsOverXAmount(int amount) {
        int lootInBank = 0;
        if (Bank.isCached()) {
            List<Item> bankItems = Bank.all().stream()
                    .filter(item -> item != null && ScriptConfiguration.getScriptConfiguration().getItemsToLoot().contains(item.getName()))
                    .collect(Collectors.toList());
            for (Item itemInBank : bankItems) {
                lootInBank += itemInBank.getAmount() * LivePrices.get(itemInBank);
            }
        } else {
            //log("Bank is not cached...");
        }
        //log("Total loot in bank: " + lootInBank / 1000 + "k");
        if (lootInBank > amount) {
            GlobalVariables.isSellingLootToMule = true;
            return true;
        }
        return false;
    }
}
