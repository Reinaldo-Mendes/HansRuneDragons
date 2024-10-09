package behaviour.attachDigsitePendant;

import framework.Branch;
import org.dreambot.api.methods.settings.PlayerSettings;

public class AttachDigsitePendantBranch extends Branch {
    @Override
    public boolean isValid() {
        return PlayerSettings.getBitValue(6142) == 0;
    }
}
