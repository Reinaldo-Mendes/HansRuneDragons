package behaviour.refreshStats;

import framework.Branch;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import utilities.Areas;

public class RefreshStatsBranch extends Branch {
    @Override
    public boolean isValid() {
        return needRefresh() && Areas.FEROX_ENCLAVE.contains(Players.getLocal());
    }

    private boolean needRefresh(){
        return Skills.getBoostedLevel(Skill.PRAYER) < Skills.getRealLevel(Skill.PRAYER) &&
                Skills.getBoostedLevel(Skill.HITPOINTS) < Skills.getRealLevel(Skill.HITPOINTS);
    }
}
