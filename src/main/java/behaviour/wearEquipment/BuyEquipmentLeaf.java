package behaviour.wearEquipment;

import config.ScriptConfiguration;
import framework.Leaf;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.wrappers.items.Item;
import utilities.Timing;
import utilities.handlers.EquipmentHandler;
import utilities.handlers.GrandExchangeHandler;
import utilities.handlers.WalkHandler;

import java.util.List;
import java.util.stream.Collectors;

import static org.dreambot.api.utilities.Logger.log;

public class BuyEquipmentLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return EquipmentHandler.getMissingEquipmentFromAllContainers(ScriptConfiguration.getScriptConfiguration().getEquipmentToWear()).size() >= 1;
    }

    @Override
    public int onLoop() {
        if(!BankLocation.GRAND_EXCHANGE.getArea(10).contains(Players.getLocal())){
            WalkHandler.walkToGe();
        } else{
            log("Missing " +EquipmentHandler.getMissingEquipmentFromAllContainers(ScriptConfiguration.getScriptConfiguration().getEquipmentToWear()));
            GrandExchangeHandler.buyItemList(EquipmentHandler.getMissingEquipmentFromAllContainers(ScriptConfiguration.getScriptConfiguration().getEquipmentToWear()), 30);
        }
        return Timing.loopReturn();
    }

    private boolean haveArmorInBankOrInventory(List<String> itemList) {
        boolean value = true;
        for (String item : itemList) {
            if (!Bank.contains(i -> i.getName().contains(item)))
                value = false;

            if (!Inventory.contains(i -> i.getName().contains(item)))
                value = false;
        }

        return value;
    }


}
