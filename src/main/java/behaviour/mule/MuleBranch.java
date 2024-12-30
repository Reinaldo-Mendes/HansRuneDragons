package behaviour.mule;

import config.GlobalVariables;
import config.ScriptConfiguration;
import framework.Branch;
import mule.MulingInformation;
public class MuleBranch extends Branch {

    public static boolean hasSentNotificationToMule = false;
    public static MulingInformation muleInformation;
    @Override
    public boolean isValid() {
        return GlobalVariables.isSellingLootToMule;

    }


}
