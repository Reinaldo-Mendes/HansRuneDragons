package behaviour.killDragon;

import config.GlobalVariables;
import framework.Leaf;
import org.dreambot.api.input.Mouse;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.NPCs;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.Character;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;
import utilities.API;
import utilities.Areas;
import utilities.Timing;

public class AttackDragonLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int onLoop() {
        if (Areas.RUNE_DRAGONS_CAVE.contains(Players.getLocal()) && Players.getLocal().getTile().getX() <= 1573) {
            /*GameObject barrier = GameObjects.closest(b -> b.getName().equals("Barrier") && b.getX() == 1574);
            if (barrier != null) {
                API.status = "Clicking barrier";
                if (barrier.interact("Pass")) {
                    Sleep.sleepUntil(() -> Players.getLocal().getX() >= 1575, 3000, 100);
                }
            }*/
            Walking.walk(Areas.RUNE_DRAGONS.getRandomTile());
        }
        Sleep.sleepUntil(() -> Players.getLocal().isInCombat() &&
                (Players.getLocal().getInteractingCharacter() != null && Players.getLocal().getInteractingCharacter().getName().equals("Rune dragon")), 3000, 100);

        if (Players.getLocal().isInCombat()) {
            Character currentDragon = Players.getLocal().getInteractingCharacter();
            if (currentDragon != null) {
                GlobalVariables.currentDragonIndex = currentDragon.getIndex();
                if (currentDragon.getHealthPercent() <= 50) {
                    GlobalVariables.lastDragonIndex = currentDragon.getIndex();
                }
            } else{
                Character dragon = Players.getLocal().getCharacterInteractingWithMe();
                if(dragon != null){
                    API.status = "Attacking dragon";
                    if(dragon.interact()){
                        Sleep.sleepUntil(() -> Players.getLocal().getInteractingCharacter() != null, 2000, 100);
                    }
                }
            }
        } else {
            NPC dragon = NPCs.closest(d -> d.getName().equals("Rune dragon") && d.getInteractingCharacter() == null && !d.isInCombat());
            if(dragon != null){
                if(dragon.interact("Attack")){
                    Sleep.sleepUntil(() -> dragon.isInCombat() && dragon.isInteracting(Players.getLocal()), 1500, 100);
                }
            }
        }
        /*if(!Players.getLocal().isInCombat()){
            NPC dragon = NPCs.closest(d -> d.getName().equals("Rune dragon") && d.getInteractingCharacter() == null && !d.isInCombat());
            if(dragon != null){
                if(dragon.interact("Attack")){
                    GlobalVariables.currentDragonIndex = dragon.getIndex();
                    Sleep.sleepUntil(() -> dragon.isInCombat() && dragon.isInteracting(Players.getLocal()), 1500, 100);
                }
            }
        }
        Character currentDragon = Players.getLocal().getInteractingCharacter();
        if(currentDragon != null){
            GlobalVariables.currentDragonIndex = currentDragon.getIndex();
            if(currentDragon.getHealthPercent() == 0){
                GlobalVariables.lastDragonIndex = currentDragon.getIndex();
                GlobalVariables.killcount++;
            }
        }*/
        return Timing.loopReturn();
    }
}
