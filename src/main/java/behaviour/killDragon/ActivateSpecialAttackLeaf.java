package behaviour.killDragon;

import framework.Leaf;
import utilities.Timing;

public class ActivateSpecialAttackLeaf extends Leaf {
    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public int onLoop() {
        return Timing.loopReturn();
    }
}
