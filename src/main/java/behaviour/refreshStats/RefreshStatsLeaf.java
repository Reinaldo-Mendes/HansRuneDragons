package behaviour.refreshStats;

import framework.Leaf;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;
import utilities.Areas;
import utilities.Timing;

import static org.dreambot.api.utilities.Logger.log;

public class RefreshStatsLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int onLoop() {
        GameObject poolOfRefreshment = GameObjects.closest("Pool of Refreshment");
        if (poolOfRefreshment != null && poolOfRefreshment.canReach()) {
            if (poolOfRefreshment.interact("Drink")) {
                Sleep.sleepUntil(() -> Skills.getBoostedLevel(Skill.HITPOINTS) >= Skills.getRealLevel(Skill.HITPOINTS), 2000, 100);
            }

        } else {
            log("Walking closer to pool of refreshment");
            Walking.walk(Areas.FEROX_ENCLAVE_CHEST.getRandomTile());
        }
        return Timing.loopReturn();
    }
}
