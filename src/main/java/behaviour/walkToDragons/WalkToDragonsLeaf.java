package behaviour.walkToDragons;

import framework.Leaf;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import utilities.Timing;

import static org.dreambot.api.utilities.Logger.log;

public class WalkToDragonsLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int onLoop() {
        //WalkHandler.walkTo(5, Areas.RUNE_DRAGONS.getRandomTile());
        //WalkHandler.walkTo(5, new Tile(3561,4003,0));
        //log(Walking.walk(new Tile(3667,3846,0)));// Fossil island
        //log(Walking.walk(new Tile(3579,3991))); //Lithkren
        log(Walking.walk(new Tile(1573,5074))); //Lithkren staircase
        return Timing.loopReturn();
    }
}
