package behaviour.initScript;

import config.GlobalVariables;
import framework.Leaf;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.utilities.Sleep;
import utilities.Timing;

public class InitScriptLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return !GlobalVariables.hasInitiatedScript;
    }

    @Override
    public int onLoop() {
        if(Bank.open()){
            if(Sleep.sleepUntil(Bank::isOpen,15000)){
                GlobalVariables.hasInitiatedScript = true;
            }
        }
        return Timing.loopReturn();
    }
}
