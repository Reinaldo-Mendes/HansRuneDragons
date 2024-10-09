package behaviour.wearEquipment;

import config.ScriptConfiguration;
import framework.Branch;
import utilities.handlers.EquipmentHandler;

public class WearEquipmentBranch extends Branch {
    @Override
    public boolean isValid() {
        return !EquipmentHandler.isWearingItemList(ScriptConfiguration.getScriptConfiguration().getEquipmentToWear());
    }
}
