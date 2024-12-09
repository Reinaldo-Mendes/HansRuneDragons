package behaviour.killDragon;

import config.ScriptConfiguration;
import framework.Leaf;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.methods.world.Worlds;
import org.dreambot.api.methods.worldhopper.WorldHopper;
import utilities.Areas;
import utilities.Timing;

import java.util.List;

import static org.dreambot.api.utilities.Logger.log;

public class HopWorldLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return Players.all(p -> !p.equals(Players.getLocal()) && Areas.RUNE_DRAGONS.contains(p)).size() >= ScriptConfiguration.getScriptConfiguration().getPlayersToHop();
    }

    @Override
    public int onLoop() {
        if(Areas.RUNE_DRAGONS.contains(Players.getLocal())){
          Area hopWorldArea =  new Area(1565, 5077, 1570, 5072);
            Walking.walk(hopWorldArea.getRandomTile());
        } else{
            List<World> availableWorlds = Worlds.all(w -> w.isMembers() && !w.isTournamentWorld() && !w.isBeta()
            && !w.isDeadmanMode() && !w.isF2P() && !w.isPVP() && !w.isPvpArena() && !w.isLeagueWorld() && !w.isSuspicious()
            && w.getMinimumLevel() == 0);
            World randomWorld = availableWorlds.get(Calculations.random(0, availableWorlds.size() - 1));
            log("Hopping to random world: "+randomWorld.getWorld());
            WorldHopper.hopWorld(randomWorld);
        }
        return Timing.loopReturn();
    }




}
