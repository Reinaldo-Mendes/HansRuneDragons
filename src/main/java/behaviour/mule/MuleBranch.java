package behaviour.mule;

import config.GlobalVariables;
import config.ScriptConfiguration;
import framework.Branch;
import static utilities.WealthTracker.lootIsOverXAmount;

public class MuleBranch extends Branch {
    @Override
    public boolean isValid() {
        return lootIsOverXAmount(ScriptConfiguration.getScriptConfiguration().getMuleSettings().getMuleAtXAmountOfLoot()) || GlobalVariables.isSellingLootToMule;

    }


}
