package behaviour.sellItems;

import config.GlobalVariables;
import config.ScriptConfiguration;
import framework.Branch;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.grandexchange.LivePrices;
import org.dreambot.api.wrappers.items.Item;

import java.util.List;
import java.util.stream.Collectors;

import static org.dreambot.api.utilities.Logger.log;

public class SellItemsBranch extends Branch {
    @Override
    public boolean isValid() {
        return lootIsOverXAmount(ScriptConfiguration.getScriptConfiguration().getMuleSettings().getMuleAtXAmountOfLoot()) || GlobalVariables.isSellingLootToMule;
    }

    private boolean lootIsOverXAmount(int amount) {
        int lootInBank = 0;
        if (Bank.isCached()) {
            List<Item> bankItems = Bank.all().stream()
                    .filter(item -> item != null && ScriptConfiguration.getScriptConfiguration().getItemsToLoot().contains(item.getName()))
                    .collect(Collectors.toList());
            for (Item itemInBank : bankItems) {
                lootInBank += itemInBank.getAmount() * LivePrices.get(itemInBank.getName());
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
