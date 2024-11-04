package behaviour.makeDigsitePendant;

import framework.Leaf;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.bank.BankMode;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.magic.Magic;
import org.dreambot.api.methods.magic.Normal;
import org.dreambot.api.methods.magic.Spellbook;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.items.Item;
import utilities.Timing;
import utilities.handlers.BankHandler;
import utilities.handlers.EquipmentHandler;
import utilities.handlers.InventoryHandler;

import java.util.Arrays;

import static org.dreambot.api.utilities.Logger.log;

public class MakeDigsitePendantLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int onLoop() {
        if (!Magic.getSpellbook().equals(Spellbook.NORMAL)) {
            log("Please change your spellbook to normal one.");
            return -1;
        }
        if(!Inventory.contains("Ruby necklace") || !Inventory.contains("Cosmic rune")){
            BankHandler.depositAllBut(Arrays.asList("Staff of fire","Cosmic rune","Ruby necklace"));
        }
        Item weapon = Equipment.getItemInSlot(EquipmentSlot.WEAPON);
        if (weapon != null && weapon.getName().equals("Staff of fire")) {
            if(Inventory.contains("Cosmic rune")){
                if(Inventory.contains("Ruby necklace")){
                    if(Magic.castSpell(Normal.LEVEL_3_ENCHANT)){
                        if(Sleep.sleepUntil(() -> Inventory.isOpen(),2000,100)){
                            if(Inventory.get("Ruby necklace").interact()){
                                Sleep.sleepUntil(() -> !Inventory.contains("Ruby necklace"), 150000,1000);
                            }
                        }
                    }
                } else{
                    BankHandler.withdrawAll("Ruby necklace", BankMode.ITEM);
                }
            } else{
                BankHandler.withdrawAll("Cosmic rune", BankMode.ITEM);
            }
          } else {
            if (!Inventory.contains("Staff of fire")) {
                BankHandler.withdrawItem("Staff of fire", BankMode.ITEM);
            } else {
                EquipmentHandler.wearItem("Staff of fire");
            }
        }
        return Timing.loopReturn();
    }
}
