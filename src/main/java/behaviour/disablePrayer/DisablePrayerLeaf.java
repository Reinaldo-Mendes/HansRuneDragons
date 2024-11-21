package behaviour.disablePrayer;

import framework.Leaf;
import org.dreambot.api.methods.prayer.Prayer;
import org.dreambot.api.methods.prayer.Prayers;
import org.dreambot.api.utilities.Sleep;
import utilities.API;
import utilities.Timing;

public class DisablePrayerLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int onLoop() {
        for(Prayer prayer: Prayer.values()){
            if(Prayers.isActive(prayer)){
                API.status = "Toggling prayer";
                if(Prayers.toggle(false, prayer)){
                    Sleep.sleepUntil(() -> Prayers.isActive(prayer), 1000, 100);
                }
            }
        }
        return Timing.loopReturn();

    }
}
