package main;

import behaviour.attachDigsitePendant.AttachDigsitePendantBranch;
import behaviour.attachDigsitePendant.AttachDigsitePendantLeaf;
import behaviour.disablePrayer.DisablePrayerBranch;
import behaviour.disablePrayer.DisablePrayerLeaf;
import behaviour.initScript.InitScriptBranch;
import behaviour.initScript.InitScriptLeaf;
import behaviour.killDragon.*;
import behaviour.loadInventory.BankNotedItemsLeaf;
import behaviour.loadInventory.BuyInventoryLoadoutItemsLeaf;
import behaviour.loadInventory.LoadInventoryBranch;
import behaviour.loadInventory.LoadInventoryLeaf;
import behaviour.makeDigsitePendant.BuyDigsitePendantIngredients;
import behaviour.makeDigsitePendant.MakeDigsitePendantBranch;
import behaviour.makeDigsitePendant.MakeDigsitePendantLeaf;
import behaviour.refreshStats.RefreshStatsBranch;
import behaviour.refreshStats.RefreshStatsLeaf;
import behaviour.mule.MuleBranch;
import behaviour.mule.SellLootLeaf;
import behaviour.mule.WalkToGeLeaf;
import behaviour.sendDiscordMessage.SendDiscordMessageBranch;
import behaviour.sendDiscordMessage.SendDiscordMessageLeaf;
import behaviour.walkToDragons.WalkToDragonsBranch;
import behaviour.walkToDragons.WalkToDragonsLeaf;
import behaviour.wearEquipment.BuyEquipmentLeaf;
import behaviour.wearEquipment.WearEquipmentBranch;
import behaviour.wearEquipment.WearEquipmentLeaf;
import config.GlobalVariables;
import config.LootItem;
import config.ScriptConfiguration;
import framework.Tree;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.ItemContainerListener;
import org.dreambot.api.script.listener.SpawnListener;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.items.Item;
import paint.CustomPaint;
import paint.PaintInfo;
import utilities.API;
import utilities.FileUtility;
import utilities.WealthTracker;
import utilities.handlers.WalkHandler;

import java.awt.*;
import java.text.DecimalFormat;

@ScriptManifest(category = Category.MONEYMAKING, author = "Hans Zimmer", name = "Hans Rune Dragons", description = "Kills rune dragons", version = 1)
public class Main extends AbstractScript implements PaintInfo, SpawnListener, ItemContainerListener {

    public static String hansRuneDragonFilePath = System.getProperty("user.dir")+"\\Hans Zimmer"+"\\Hans Rune Dragon";
    public static Timer timer = new Timer();
    public static Timer discordTimer = new Timer();
    private WealthTracker wealthTracker = new WealthTracker();
    DecimalFormat df = new DecimalFormat("#");
    //private Image scriptBanner;

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
        if(!FileUtility.readProfile(hansRuneDragonFilePath+"\\default.txt")){
            FileUtility.initializeDefaultProfile();
        } else{
            log("We read the file default hehehe");

        }
        //scriptBanner = Images.loadImage("https://i.imgur.com/V9KpN9Y.png");
        timer.start();
        GlobalVariables.cacheLootedItemsList();
        log("Discord timer in minutes: "+ScriptConfiguration.getScriptConfiguration().getWebhookSettings().getMinutesBetweenMessages());
        discordTimer = new Timer(ScriptConfiguration.getScriptConfiguration().getWebhookSettings().getMinutesBetweenMessages() * 60000);
        WalkHandler.addNodes();
        //ScriptConfiguration.getScriptConfiguration().initInventoryLoadout();
        //ScriptConfiguration.getScriptConfiguration().initInventoryLoadoutBuyList();
        //ScriptConfiguration.getScriptConfiguration().initDigsitePendantIngredientsMap();

        instantiateTree();
    }


    private final Tree<Main> tree = new Tree<>();

    private void instantiateTree() {
        tree.addBranches(
                //new InitScriptBranch().addLeafs(new InitScriptLeaf()),
                //new SendDiscordMessageBranch().addLeafs(new SendDiscordMessageLeaf())
                new InitScriptBranch().addLeafs(new InitScriptLeaf()),
                new SendDiscordMessageBranch().addLeafs(new SendDiscordMessageLeaf()),
                new MakeDigsitePendantBranch().addLeafs(new BuyDigsitePendantIngredients(), new MakeDigsitePendantLeaf()),
                new DisablePrayerBranch().addLeafs(new DisablePrayerLeaf()),
                new AttachDigsitePendantBranch().addLeafs(new AttachDigsitePendantLeaf()),
                new MuleBranch().addLeafs(new WalkToGeLeaf(), new SellLootLeaf()),
                new KillDragonBranch().addLeafs(new TeleportOutLeaf(), new DrinkPrayerPotLeaf(), new HopWorldLeaf(), new ActivatePrayerLeaf(), new DrinkAntifireLeaf(), new EatFoodLeaf(), new DrinkCombatPotionLeaf(),
                        new ToggleAutoRetaliateLeaf(), new LootItemsLeaf(), new LootFoodLeaf(), new AttackDragonLeaf()),
                new RefreshStatsBranch().addLeafs(new RefreshStatsLeaf()),
                new WearEquipmentBranch().addLeafs(new BuyEquipmentLeaf(),new WearEquipmentLeaf()),
                new WalkToDragonsBranch().addLeafs(new WalkToDragonsLeaf()),
                new LoadInventoryBranch().addLeafs(new BuyInventoryLoadoutItemsLeaf(), new BankNotedItemsLeaf(),new LoadInventoryLeaf())
                //new BuyItemsBranch().addLeafs(new WalkToGeLeaf(), new BuyItemsLeaf())
        );
    }


    /**
     * onLoop is a infinite loop
     *
     * @return gets the leaf and executes it
     */

    @Override
    public int onLoop() {
        return this.tree.onLoop();
    }

    @Override
    public void onExit() {
        log("Runtime: " + timer.elapsed());
        log("Wealth generated: " + wealthTracker.getWealthGenerated() / 1000 + "k");
        log("Wealth per hour: " + wealthTracker.getWealthGeneratedPerHour() / 1000 + "k");
        log("Loot: " + GlobalVariables.lootedItems);

    }

    /**
     * @return the information for the paint
     */
    @Override
    public String[] getPaintInfo() {
        return new String[]{
                getManifest().name() + " V" + getManifest().version(),
                "Discord timer: "+discordTimer.formatTime(discordTimer.remaining()),
                "Discord timer finished: "+discordTimer.finished(),
                "Runtime: " + timer.formatTime(timer.elapsed()),
                "Current Branch: " + API.currentBranch,
                "Current Leaf: " + API.currentLeaf,
                "Next eat: " + GlobalVariables.nextFoodEat,
                "Next pray sip: " + GlobalVariables.nextPraySip,
                "Killcount: " + GlobalVariables.killcount,
                "Trips to restock: "+GlobalVariables.tripsToRestock,
                "Wealth generated: " + df.format(wealthTracker.getWealthGenerated() / 1000) + "k (" + df.format(wealthTracker.getWealthGeneratedPerHour() / 1000) + "k per hour)"
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
        /*if(scriptBanner != null){
            g.drawImage(scriptBanner,0,337,null);
            g.setFont(new Font("Tahoma", Font.BOLD, 12));
            g.setColor(Color.YELLOW);
            g.drawString(timer.formatTime(timer.elapsed()),120,404);
            g.drawString(API.currentLeaf,90,443);
            g.drawString("Click here",380,404);
            g.drawString(API.status,351,443);

        }*/
        Graphics2D gg = (Graphics2D) g;
        gg.setRenderingHints(aa);
        CUSTOM_PAINT.paint(gg);
    }

    @Override
    public void onInventoryItemAdded(Item i) {
        if (API.currentBranch.equals("KillDragonBranch")) {
            for (LootItem item : GlobalVariables.lootedItems) {
                if (item.getName().equals(i.getName())) {
                    GlobalVariables.lootedItem = true;
                    int currentAmount = item.getAmount();
                    item.setAmount(currentAmount + i.getAmount());
                    log(item.getName() + " amount is " + item.getAmount() + ". Total value of this item looted is: " + item.getAmount() * item.getPrice());
                }
            }
            if (i.hasAction("Eat")) {
                GlobalVariables.lootedFood = true;
            }
        }

    }

    @Override
    public void onInventoryItemRemoved(Item i) {

    }

    @Override
    public void onInventoryItemChanged(Item incoming, Item existing) { //This method is for stackable items like runes, bolts, etc.
        if (API.currentBranch.equals("KillDragonBranch")) {
            for (LootItem item : GlobalVariables.lootedItems) {
                if (item.getName().equals(incoming.getName())) {
                    GlobalVariables.lootedItem = true;
                    int currentAmount = item.getAmount();
                    int difference = incoming.getAmount() - existing.getAmount();
                    item.setAmount(currentAmount + difference);
                }
            }
        }
    }

    @Override
    public void onNpcDespawn(NPC npc) {
        if (npc != null) {
            if (npc.getIndex() == GlobalVariables.lastDragonIndex) {
                GlobalVariables.killcount++;
            }
        }
    }

}