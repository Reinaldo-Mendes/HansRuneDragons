package behaviour.initScript;

import config.GlobalVariables;
import framework.Leaf;
import org.dreambot.api.Client;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.SkillTracker;
import org.dreambot.api.utilities.Sleep;
import utilities.Timing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.dreambot.api.utilities.Logger.log;

public class InitScriptLeaf extends Leaf {
    List<Skill> skillsToTrack = new ArrayList<>(Arrays.asList(Skill.HITPOINTS, Skill.ATTACK, Skill.DEFENCE, Skill.STRENGTH, Skill.RANGED));
    @Override
    public boolean isValid() {
        return !GlobalVariables.hasInitiatedScript;
    }

    @Override
    public int onLoop() {
        if(Client.isLoggedIn()){
            for(Skill skill : skillsToTrack){
                if(!SkillTracker.hasStarted(skill)){
                    SkillTracker.start(skill);
                    log("Exp tracker for "+skill.getName()+" has started");
                }
            }
        }

        if(Bank.open()){
            if(Sleep.sleepUntil(Bank::isOpen,15000)){
                GlobalVariables.hasInitiatedScript = true;
            }
        }
        return Timing.loopReturn();
    }
}
