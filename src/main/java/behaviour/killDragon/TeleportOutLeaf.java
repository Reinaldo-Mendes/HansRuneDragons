package behaviour.killDragon;

import config.ScriptConfiguration;
import framework.Leaf;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.dreambot.api.methods.combat.Combat;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.container.impl.equipment.Equipment;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.Item;
import utilities.Areas;
import utilities.Timing;
import utilities.handlers.EquipmentHandler;

import static org.dreambot.api.utilities.Logger.log;

public class TeleportOutLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return (isLowHpAndOutOfFood() ||
                isExtremelyLowHp() ||
                isOutOfAntifireAndNoAntifireEffect() ||
                isOutOfFoodAndInventoryIsFull() ||
                isOutOfPrayerPotionAndLowPrayer()) && !Areas.FEROX_ENCLAVE.contains(Players.getLocal());

    }

    @Override
    public int onLoop() {
        Item ring = Equipment.getItemInSlot(EquipmentSlot.RING);
        if (ring != null && ring.getName().contains("Ring of dueling")) {
            if (ring.interact("Ferox Enclave")) {
                Sleep.sleepUntil(() -> Areas.FEROX_ENCLAVE.contains(Players.getLocal()), 1000, 100);
            }
        } else {
            log("We are not wearing dueling ring...");
            Item inventoryRing = Inventory.get(i -> i.getName().contains("Ring of dueling"));
            if (inventoryRing != null) {
                EquipmentHandler.wearItem(inventoryRing.getName());
            } else {
                log("We don't have dueling ring in inventory... Let's cross the barrier.");
                GameObject westBarrier = GameObjects.closest(g -> g.getName().equals("Barrier") && g.getX() == 1574);
                if (westBarrier != null && Players.getLocal().getX() >= 1575) {
                    if (westBarrier.interact("Pass")) {
                        Sleep.sleepUntil(() -> Players.getLocal().getX() <= 1573, 2000, 100);
                    }
                }
            }
        }

        return Timing.loopReturn();
    }

    private boolean isOutOfFoodAndInventoryIsFull() {
        return !Inventory.contains(i -> i.hasAction("Eat")) && Inventory.isFull();
    }

    private boolean isLowHpAndOutOfFood() {
        return Skills.getBoostedLevel(Skill.HITPOINTS) <= 55 && !Inventory.contains(i -> i.hasAction("Eat"));
    }

    private boolean isExtremelyLowHp() {
        return Skills.getBoostedLevel(Skill.HITPOINTS) <= ScriptConfiguration.getScriptConfiguration().getCombatSettings().getEmergencyTeleportHp();
    }

    private boolean isOutOfAntifireAndNoAntifireEffect() {
        return !Inventory.contains(i -> i.getName().contains("antifire")) && !Combat.isAntiFireEnabled() &&
                !Combat.isSuperAntiFireEnabled();
    }

    private boolean isOutOfPrayerPotionAndLowPrayer() {
        return !Inventory.contains(i -> i.getName().contains("Prayer")) && Skills.getBoostedLevel(Skill.PRAYER) <= 30;
    }
}
