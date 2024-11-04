package behaviour.loadInventory;

import framework.Leaf;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.wrappers.items.Item;
import utilities.Timing;

import static org.dreambot.api.utilities.Sleep.sleepUntil;

public class BankNotedItemsLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return isThereAnyNotedItems();
    }

    @Override
    public int onLoop() {
        if (!Bank.isOpen()) {
            Bank.open();
            sleepUntil(() -> Bank.isOpen(), 5000); // Aguarda até que o banco esteja aberto
        }
        if (Bank.isOpen()) {
            Inventory.all().stream()
                    .filter(item -> item != null && item.isNoted())
                    .forEach(item -> Bank.depositAll(item.getName()));
            Bank.close();
        }
        return Timing.loopReturn();
    }

    private boolean isThereAnyNotedItems(){
        return Inventory.all().stream()
                .filter(item -> item != null)
                .anyMatch(Item::isNoted);
    }
}
