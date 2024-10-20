package behaviour.killDragon;

import config.ScriptConfiguration;
import framework.Leaf;
import org.dreambot.api.methods.combat.Combat;
import utilities.Timing;

import static org.dreambot.api.utilities.Logger.log;

public class ToggleAutoRetaliateLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return shouldHandleRetaliate();
    }

    @Override
    public int onLoop() {
        boolean enableRetaliate = ScriptConfiguration.getScriptConfiguration().getCombatSettings().isEnableAutoRetaliate();
        if(enableRetaliate && !Combat.isAutoRetaliateOn()){
            if(Combat.toggleAutoRetaliate(true)){
                log("Enabled auto retaliate on...");
            }
        }
        if(!enableRetaliate && Combat.isAutoRetaliateOn()){
            if(Combat.toggleAutoRetaliate(false)){
                log("Disabled auto retaliate");
            }
        }
        return Timing.loopReturn();
    }

    private boolean shouldHandleRetaliate(){
        boolean enableRetaliate = ScriptConfiguration.getScriptConfiguration().getCombatSettings().isEnableAutoRetaliate();
        return (enableRetaliate && !Combat.isAutoRetaliateOn()) ||
                (!enableRetaliate && Combat.isAutoRetaliateOn());
    }
}
