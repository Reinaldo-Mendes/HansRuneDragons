package utilities.handlers;

import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.container.impl.bank.BankMode;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.items.Item;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankHandler {
static int openBankFailCounter;
    public static boolean withdrawItem(String itemName, BankMode bankMode){
        if(!Bank.isOpen()){
            if(Bank.open()){
                Sleep.sleepUntil(() -> Bank.isOpen(),1000, 300);
                withdrawItem(itemName, bankMode);
            }
        }
        if(Bank.isOpen()){
            if(!bankMode.equals(Bank.getWithdrawMode())){
                if(Bank.setWithdrawMode(bankMode)){
                    Logger.log("[BANK HANDLER] Set withdraw mode to "+bankMode);
                    withdrawItem(itemName,bankMode);
                }
            }
            if(Inventory.getEmptySlots() < 1){
                Bank.depositAllItems();
                Logger.log("[BANK HANDLER] Deposited all items to make room for "+itemName);
            }

            if(Bank.contains(i -> i.getName().contains(itemName))){
                if(Bank.withdraw(i -> i.getName().contains(itemName))){
                    Sleep.sleepUntil(() -> Inventory.contains(i -> i.getName().contains(itemName)), 2000, 100);
                    Logger.log("[BANK HANDLER] Withdrew "+itemName+" successfully.");
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean withdrawExactItem(String itemName, BankMode bankMode){
        if(!Bank.isOpen()){
            if(Bank.open()){
                Sleep.sleepUntil(() -> Bank.isOpen(),1000, 300);
                withdrawExactItem(itemName, bankMode);
            }
        }
        if(Bank.isOpen()){
            if(!bankMode.equals(Bank.getWithdrawMode())){
                if(Bank.setWithdrawMode(bankMode)){
                    Logger.log("[BANK HANDLER] Set withdraw mode to "+bankMode);
                    withdrawExactItem(itemName,bankMode);
                }
            }
            if(Bank.contains(itemName)){
                if(Bank.withdraw(itemName)){
                    Sleep.sleepUntil(() -> Inventory.contains(itemName), 1000, 300);
                    Logger.log("[BANK HANDLER] Withdrew "+itemName+" successfully.");
                    return true;
                }
            } else{
                //Logger.log("[BANK HANDLER] Adding "+itemName+" to buy list.");
            }
        }
        return false;
    }

    public static boolean withdrawAll(String itemName, BankMode bankMode){
        if(!Bank.isOpen()){
            if(Bank.open()){
                Sleep.sleepUntil(() -> Bank.isOpen(),1000, 300);
                withdrawAll(itemName, bankMode);
            }
        }
        if(Bank.isOpen()){
            if(!bankMode.equals(Bank.getWithdrawMode())){
                if(Bank.setWithdrawMode(bankMode)){
                    Logger.log("[BANK HANDLER] Set withdraw mode to "+bankMode);
                    withdrawAll(itemName,bankMode);
                }
            }
            if(Bank.contains(itemName)){
                if(Bank.withdrawAll(itemName)){
                    Sleep.sleepUntil(() -> Inventory.contains(itemName), 1000, 300);
                    Logger.log("[BANK HANDLER] Withdrew "+itemName+" successfully.");
                    return true;
                }
            } else{
                //Logger.log("[BANK HANDLER] Adding "+itemName+" to buy list.");
            }
        }
        return false;
    }

    public static boolean withdrawItem(String itemName, BankMode bankMode, int amount){
        if(!Bank.isOpen()){
            if(Bank.open()){
                Sleep.sleepUntil(() -> Bank.isOpen(),1000, 300);
                withdrawItem(itemName, bankMode, amount);
            }
        }
        if(Bank.isOpen()){
            if(!bankMode.equals(Bank.getWithdrawMode())){
                if(Bank.setWithdrawMode(bankMode)){
                    Logger.log("[BANK HANDLER] Set withdraw mode to "+bankMode);
                    withdrawItem(itemName,bankMode, amount);
                }
            }
            if(Bank.withdraw(itemName, amount)){
                Sleep.sleepUntil(() -> Inventory.contains(itemName), 1000, 300);
                //Logger.log("[BANK HANDLER] Withdrew "+itemName+" successfully.");
                return true;
            }
        }
        return false;
    }

    public static boolean containsAny(List<String> itemList){
        for(String itemInList: itemList){
            if(Bank.contains(itemInList))
                return true;
        }
        return false;
    }

    public static boolean withdrawItemList(List<String> itemList, BankMode bankMode){
        boolean value = false;
        if(!Bank.isOpen()){
            if(Bank.open()){
                Sleep.sleepUntil(() -> Bank.isOpen(),1000, 300);
                withdrawItemList(itemList, bankMode);
            } else{
                Logger.log("[BANK HANDLER] Failed to open bank.");
                openBankFailCounter++;
                Logger.log("Fail counter: "+openBankFailCounter);
                if(openBankFailCounter >= 10){
                    Logger.log("Unable to bank from here. Walking to default bank location...");
                    Walking.walk(BankLocation.GRAND_EXCHANGE);
                }
            }
        }
        if(Bank.isOpen()){
            openBankFailCounter = 0;
            if(!bankMode.equals(Bank.getWithdrawMode())){
                if(Bank.setWithdrawMode(bankMode)){
                    Logger.log("[BANK HANDLER] Set withdraw mode to "+bankMode);
                    withdrawItemList(itemList,bankMode);
                }
            }
            if(Inventory.isFull()){
                if(Bank.depositAllItems()){
                    Logger.log("[BANK HANDLER] Deposited all items to make room to withdraw item list.");
                }
            }
            for(String itemInList: itemList){
                if(Bank.contains(itemInList)){
                    if(!Inventory.contains(itemInList)){
                        if(Bank.withdraw(itemInList)){
                            Sleep.sleepUntil(() -> Inventory.contains(itemInList), 400, 100);
                            Logger.log("[BANK HANDLER] Withdrew "+itemInList+" successfully.");
                            value = true;

                        }
                    }
                }
            }
        }
        return value;
    }

    public static boolean withdrawAllItemList(List<String> itemList, BankMode bankMode){
        boolean value = false;
        if(!Bank.isOpen()){
            if(Bank.open()){
                Sleep.sleepUntil(() -> Bank.isOpen(),1000, 300);
                withdrawAllItemList(itemList, bankMode);
            }
        }
        if(Bank.isOpen()){
            if(!bankMode.equals(Bank.getWithdrawMode())){
                if(Bank.setWithdrawMode(bankMode)){
                    Logger.log("[BANK HANDLER] Set withdraw mode to "+bankMode);
                    withdrawAllItemList(itemList,bankMode);
                }
            }
            for(String itemInList: itemList){
                if(Bank.contains(itemInList)){
                    if(Bank.withdrawAll(itemInList)){
                        Sleep.sleepUntil(() -> Inventory.contains(itemInList), 3000, 300);
                        Logger.log("[BANK HANDLER] Withdrew "+itemInList+" successfully.");
                        value = true;
                    }
                } else{
                    //Logger.log("[BANK HANDLER] Adding "+itemInList+" to buy list.");
                }
            }
        }
        return value;
    }

    public static boolean containsHashMap(HashMap<String, Integer> items){
        boolean value = true;
        for (Map.Entry<String, Integer> entry: items.entrySet()){
            String name = entry.getKey();
            int amount = entry.getValue() ;
            if(Bank.contains(i -> i.getName().contains(name))){
                if(Bank.count(i -> i.getName().contains(name)) != amount){
                    value = false;
                }
            } else{
                value = false;
            }
        }
        return value;
    }

    public static boolean loadInventory(HashMap<String, Integer> loadout) {
        boolean value = false;
        if (!Bank.isOpen()) {
            if (Bank.open()) {
                loadInventory(loadout);
            }
        }
        for (Item i : Inventory.all()) {
            if (i != null) {
                String itemName = i.getName();
                if(i.getName().contains("(") && !i.hasAction("Drink")){
                    itemName = i.getName().substring(0,i.getName().indexOf("(")); // Getting the item name without ( for items like dueling ring and digsite pendant.
                    Logger.log("Item name: "+itemName);
                }
                if (!loadout.containsKey(itemName)) {
                    if (Bank.depositAll(i)) {
                        Logger.log("We deposited " + itemName);
                    }
                } else {
                    if(Inventory.contains(i)){
                        if (!i.isStackable()) {
                            if (Inventory.count(i.getName()) < loadout.get(itemName)) {
                                int difference = loadout.get(itemName) - Inventory.count(i.getName());
                                if(Bank.withdraw(i.getName(), difference)){
                                    Logger.log(Color.cyan, "[BANK HANDLER] Withdrew " +difference + " "+i.getName());
                                    Sleep.sleepUntil(() -> Inventory.count(i.getName()) == loadout.get(i.getName()), 2000, 100);
                                }
                            }
                            if(Inventory.count(f -> f.getName().contains(i.getName())) > loadout.get(itemName)){
                                int difference = Inventory.count(f -> f.getName().contains(i.getName())) - loadout.get(itemName);
                                if(Bank.deposit(f -> f.getName().contains(i.getName()), difference)){
                                    Logger.log(Color.cyan, "[BANK HANDLER] Deposited " +difference + " "+i.getName());
                                    Sleep.sleepUntil(() -> Inventory.count(i.getName()) == loadout.get(i.getName()), 2000, 100);
                                }
                            }
                        }
                    }
                }
                if(loadout.get(i.getName()) != null && loadout.get(i.getName()) != -1){
                    if (Inventory.count(i.getName()) == loadout.get(i.getName())){
                        //Logger.log("Our count in inventory is the same in loadout for "+i.getName());
                        value = true;
                    } else{
                        //Logger.log(Color.RED,"Our count in inventory is NOT in loadout for "+i.getName());
                        value = false;
                    }
                }
            }
        }

        for(Map.Entry<String, Integer> entry: loadout.entrySet()){
            if (entry != null){
                String name = entry.getKey();
                Integer amount = entry.getValue();
                if(!Inventory.contains(i -> i.getName().contains(name))){
                    Logger.log("We need to withdraw "+name);
                    if(amount == -1){
                        if(Bank.withdrawAll(i -> i.getName().contains(name))){
                            Logger.log("[BANK HANDLER] Withdrew all "+name);
                            Sleep.sleepUntil(() -> Inventory.contains(i -> i.getName().contains(name)), 1000, 100);
                        }
                    } else{
                        if(Bank.withdraw(i -> i.getName().contains(name),amount)){
                            Logger.log("[BANK HANDLER] Withdrew "+amount+ " "+name);
                        }
                    }
                } else{
                    Logger.log("Inventory already contains "+name);
                    if(amount != -1){
                        if (Inventory.count(i -> i.getName().contains(name)) != amount){
                            int difference = amount - Inventory.count(i -> i.getName().contains(name));
                            if(Bank.withdraw(i -> i.getName().contains(name), difference)){
                                Sleep.sleep(1000,2000);
                            }
                        } else{
                            Logger.log("We don't have to withdraw "+name);
                        }
                    } else{
                        Logger.log("For the item "+name+" we must withdraw all!");
                        if(Bank.contains(i -> i.getName().contains(name))){
                            if(Bank.withdrawAll(i -> i.getName().contains(name))){
                                Sleep.sleep(1000, 2000);
                            }
                        }
                    }
                }
            }
        }
        Logger.log("Return value: "+value);
        return value;
    }
}
