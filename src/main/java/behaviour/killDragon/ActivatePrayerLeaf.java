package behaviour.killDragon;

import config.ScriptConfiguration;
import framework.Leaf;
import org.dreambot.api.methods.prayer.Prayer;
import org.dreambot.api.methods.prayer.Prayers;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.Skills;
import org.dreambot.api.utilities.Hash;
import org.dreambot.api.utilities.Sleep;
import utilities.Timing;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.dreambot.api.utilities.Logger.log;

public class ActivatePrayerLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return !isDesiredPrayersActive(ScriptConfiguration.getScriptConfiguration().getPrayerSettings().getSelectedPrayers());
    }

    @Override
    public int onLoop() {
        if(Skills.getBoostedLevel(Skill.PRAYER) > 0){
            if(ScriptConfiguration.getScriptConfiguration().getPrayerSettings().isUseQuickPrayer()){
                if(!areActivePrayersCorrect()){
                    Prayers.toggleQuickPrayer(false);
                    if(comparePrayerLists(Prayers.getQuickPrayers(),ScriptConfiguration.getScriptConfiguration().getPrayerSettings().getSelectedPrayers())){
                        if(!Prayers.isQuickPrayerActive()){
                            if(Prayers.toggleQuickPrayer(true)){
                                log("Activated quick prayers...");
                                Sleep.sleepUntil(() -> Prayers.isQuickPrayerActive(), 1000, 100);
                            }
                        }
                    } else{
                        log("Setting up quick prayers...");
                        Prayers.setupQuickPrayers(ScriptConfiguration.getScriptConfiguration().getPrayerSettings().getSelectedPrayers().toArray(new Prayer[0]));
                    }
                }
            }
        }

        return Timing.loopReturn();
    }

    private boolean isDesiredPrayersActive(List<Prayer> prayers) {
        boolean isActive = true;
        for (Prayer p : prayers) {
            if (!Prayers.isActive(p))
                isActive = false;
        }
        return isActive;
    }

    private boolean comparePrayerLists(List<Prayer> list1, List<Prayer> list2){
        Set<Prayer> set1 = new HashSet<>(list1);
        Set<Prayer> set2 = new HashSet<>(list2);

        return set1.equals(set2);
    }

    private boolean areActivePrayersCorrect(){
        List<Prayer> activePrayers = Arrays.asList(Prayers.getActive());
        Set<Prayer> setActivePrayers = new HashSet<>(activePrayers);
        Set<Prayer> setDesiredPrayers = new HashSet<>(ScriptConfiguration.getScriptConfiguration().getPrayerSettings().getSelectedPrayers());
        return setActivePrayers.equals(setDesiredPrayers);
    }
}
