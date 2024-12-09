package behaviour.disablePrayer;

import framework.Branch;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.prayer.Prayer;
import org.dreambot.api.methods.prayer.Prayers;
import utilities.Areas;

public class DisablePrayerBranch extends Branch {
    @Override
    public boolean isValid() {
        return !Areas.RUNE_DRAGONS.contains(Players.getLocal())
                && isAnyPrayerActive();
    }

    private boolean isAnyPrayerActive(){
        for(Prayer p: Prayer.values()){
            if(Prayers.isActive(p)){
                return true;
            }
        }
        return false;
    }
}
