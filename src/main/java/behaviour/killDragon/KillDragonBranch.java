package behaviour.killDragon;

import framework.Branch;
import org.dreambot.api.methods.interactive.Players;
import utilities.Areas;

public class KillDragonBranch extends Branch {
    @Override
    public boolean isValid() {
        //return Areas.RUNE_DRAGONS.contains(Players.getLocal());
        return true;
    }
}
