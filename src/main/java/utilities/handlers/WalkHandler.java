package utilities.handlers;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.methods.walking.pathfinding.impl.web.WebFinder;
import org.dreambot.api.methods.walking.web.node.AbstractWebNode;
import org.dreambot.api.methods.walking.web.node.impl.BasicWebNode;
import org.dreambot.api.methods.walking.web.node.impl.EntranceWebNode;

public class WalkHandler {
    public static boolean walkTo(int distance, Tile tile) {
        if (!Walking.isRunEnabled()) {
            if (Walking.getRunEnergy() >= Calculations.random(15, 50)) {
                Walking.toggleRun();
            }
        }
        if (Walking.shouldWalk(distance)) {
            return Walking.walk(tile);
        }
        return false;
    }


    public static void addNodes() {
        EntranceWebNode rowboatToLithkren = new EntranceWebNode(3658, 3848);
        rowboatToLithkren.setEntityName("Rowboat");
        rowboatToLithkren.setAction("Travel");
        rowboatToLithkren.addDualConnections(WebFinder.getWebFinder().getNearestGlobal(rowboatToLithkren.getTile(), 15));

        EntranceWebNode rowboatToFossil = new EntranceWebNode(3582, 3971);
        rowboatToFossil.setEntityName("Rowboat");
        rowboatToFossil.setAction("Travel");
        rowboatToFossil.addDualConnections(WebFinder.getWebFinder().getNearestGlobal(rowboatToFossil.getTile(), 15));
        rowboatToFossil.addDualConnections(rowboatToLithkren);


        EntranceWebNode lithkrenStairs1 = new EntranceWebNode(3558, 4003);
        lithkrenStairs1.setEntityName("Stairs");
        lithkrenStairs1.setAction("Climb-up");
        lithkrenStairs1.addDualConnections(WebFinder.getWebFinder().getNearestGlobal(lithkrenStairs1.getTile(), 15));

        AbstractWebNode webNode0 = new BasicWebNode(3556, 4004, 1);
        webNode0.addDualConnections(lithkrenStairs1);
        WebFinder.getWebFinder().addWebNode(webNode0);

        EntranceWebNode lithkrenTrapdoor1 = new EntranceWebNode(3554, 3998, 1);
        lithkrenTrapdoor1.setEntityName("Trapdoor");
        lithkrenTrapdoor1.setAction("Climb-down");
        lithkrenTrapdoor1.addDualConnections(webNode0);

        AbstractWebNode webnode1 = new BasicWebNode(3554, 4000);
        webnode1.addDualConnections(lithkrenTrapdoor1);
        WebFinder.getWebFinder().addWebNode(webnode1);

        EntranceWebNode lithkrenStaircase1 = new EntranceWebNode(3554, 4003);
        lithkrenStaircase1.setEntityName("Staircase");
        lithkrenStaircase1.setAction("Climb-down");
        lithkrenStaircase1.addDualConnections(webnode1);

        AbstractWebNode webNode2 = new BasicWebNode(3549, 10448, 0);
        webNode2.addDualConnections(lithkrenStaircase1);
        WebFinder.getWebFinder().addWebNode(webNode2);
        webNode2.addDualConnections(WebFinder.getWebFinder().getNearest(new Tile(3550, 10453), 15));



        /*AbstractWebNode webNode3 = new BasicWebNode(3551,10460);
        webNode3.addDualConnections(webNode2);
        WebFinder.getWebFinder().addWebNode(webNode3);*/

        EntranceWebNode lithkrenStaircase2 = new EntranceWebNode(3549, 10469);
        lithkrenStaircase2.setEntityName("Staircase");
        lithkrenStaircase2.setAction("Climb");
        lithkrenStaircase2.addDualConnections(WebFinder.getWebFinder().getNearestGlobal(lithkrenStaircase2.getTile(), 15));

        AbstractWebNode webNode4 = new BasicWebNode(3550, 10473);
        webNode4.addDualConnections(lithkrenStaircase2);
        WebFinder.getWebFinder().addWebNode(webNode4);

        EntranceWebNode lithkrenBrokenDoors = new EntranceWebNode(3547, 10482);
        lithkrenBrokenDoors.setEntityName("Broken Doors");
        lithkrenBrokenDoors.setAction("Enter");
        lithkrenBrokenDoors.addDualConnections(webNode4);

        AbstractWebNode webNode5 = new BasicWebNode(1568, 5061);
        webNode5.addDualConnections(lithkrenBrokenDoors);
        WebFinder.getWebFinder().addWebNode(webNode5);

        AbstractWebNode webNode6 = new BasicWebNode(1567, 5070);
        webNode6.addDualConnections(webNode5);
        WebFinder.getWebFinder().addWebNode(webNode6);

        AbstractWebNode webNode7 = new BasicWebNode(1569, 5079);
        webNode7.addDualConnections(webNode6);
        WebFinder.getWebFinder().addWebNode(webNode7);

        AbstractWebNode webNode8 = new BasicWebNode(1573, 5074);
        webNode8.addDualConnections(webNode7);
        WebFinder.getWebFinder().addWebNode(webNode8);

        EntranceWebNode runeDragonsBarrier = new EntranceWebNode(1574,5074);
        runeDragonsBarrier.setEntityName("Barrier");
        runeDragonsBarrier.setAction("Pass");
        runeDragonsBarrier.addDualConnections(webNode8);

        AbstractWebNode webNode9 = new BasicWebNode(1580,5074);
        webNode9.addDualConnections(runeDragonsBarrier);
        WebFinder.getWebFinder().addWebNode(webNode9);


    }
}
