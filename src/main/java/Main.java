import behaviour.attachDigsitePendant.AttachDigsitePendantBranch;
import behaviour.attachDigsitePendant.AttachDigsitePendantLeaf;
import behaviour.loadInventory.LoadInventoryBranch;
import behaviour.loadInventory.LoadInventoryLeaf;
import behaviour.walkToDragons.WalkToDragonsBranch;
import behaviour.walkToDragons.WalkToDragonsLeaf;
import behaviour.wearEquipment.WearEquipmentBranch;
import behaviour.wearEquipment.WearEquipmentLeaf;
import config.ScriptConfiguration;
import framework.Tree;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import paint.CustomPaint;
import paint.PaintInfo;
import utilities.API;
import utilities.handlers.WalkHandler;

import java.awt.*;

@ScriptManifest(category = Category.MONEYMAKING, author = "Hans Zimmer", name = "Hans Rune Dragons", description = "Kills rune dragons", version = 1)
public class Main extends AbstractScript implements PaintInfo {

    /**
     * @param args script quick launch arguments
     */

    @Override
    public void onStart(String... args) {
        instantiateTree();
    }

    /**
     * On start from script launcher
     */
    @Override
    public void onStart() {
        WalkHandler.addNodes();
        ScriptConfiguration.getScriptConfiguration().initInventoryLoadout();
        instantiateTree();
    }


    private final Tree<Main> tree = new Tree<>();
    private void instantiateTree() {
        tree.addBranches(
                new AttachDigsitePendantBranch().addLeafs(new AttachDigsitePendantLeaf()),
                new WearEquipmentBranch().addLeafs(new WearEquipmentLeaf()),
                new LoadInventoryBranch().addLeafs(new LoadInventoryLeaf()),
                new WalkToDragonsBranch().addLeafs(new WalkToDragonsLeaf())
        );
    }


    /**
     * onLoop is a infinite loop
     * @return gets the leaf and executes it
     */

    @Override
    public int onLoop() {

        return this.tree.onLoop();
    }

    /**
     * @return the information for the paint
     */
    @Override
    public String[] getPaintInfo() {
        return new String[] {
                getManifest().name() + " V" + getManifest().version(),
                "Current Branch: " + API.currentBranch,
                "Current Leaf: " + API.currentLeaf
        };
    }

    /**
     * Instantiate the paint object, can be customized to liking.
     */
    private final CustomPaint CUSTOM_PAINT = new CustomPaint(this,
            CustomPaint.PaintLocations.TOP_LEFT_PLAY_SCREEN, new Color[]{new Color(255, 251, 255)},
            "Trebuchet MS",
            new Color[]{new Color(50, 50, 50, 175)},
            new Color[]{new Color(28, 28, 29)},
            1, false, 5, 3, 0);

    private final RenderingHints aa = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


    /**
     * paint for the script
     */
    @Override
    public void onPaint(Graphics g) {
        Graphics2D gg = (Graphics2D) g;
        gg.setRenderingHints(aa);

        CUSTOM_PAINT.paint(gg);
    }

}