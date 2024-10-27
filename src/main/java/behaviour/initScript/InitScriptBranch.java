package behaviour.initScript;

import config.GlobalVariables;
import framework.Branch;

public class InitScriptBranch extends Branch {
    @Override
    public boolean isValid() {
        return !GlobalVariables.hasInitiatedScript;
    }
}
