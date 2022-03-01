package xyz.lotho.me.queues;

import xyz.lotho.me.SkyHub;
import xyz.lotho.me.managers.HubPlayer;

import java.util.ArrayList;
import java.util.HashMap;

public class QueueManager {

    private final SkyHub instance;

    private HashMap<String, ArrayList<HubPlayer>> serversQueue = new HashMap<>();

    public QueueManager(SkyHub instance) {
        this.instance = instance;
    }

    public HashMap<String, ArrayList<HubPlayer>> getServersQueue() {
        return this.serversQueue;
    }

    public void addServerToQueue(String serverName) {
        this.serversQueue.put(serverName, new ArrayList<>());
    }

    public void addPlayerToQueue(String serverName, HubPlayer hubPlayer) {
        this.serversQueue.get(serverName).add(hubPlayer);
    }

    public boolean isPlayerInQueue(String serverName, HubPlayer hubPlayer) {
        return this.serversQueue.get(serverName).contains(hubPlayer);
    }

    public void removePlayerFromQueues(HubPlayer hubPlayer) {
        this.getServersQueue().forEach((serverName, players) -> {
            if (hubPlayer.isInQueue(serverName)) this.getServersQueue().get(serverName).remove(hubPlayer);
        });
    }

    public boolean isPlayerInAnyQueue(HubPlayer hubPlayer) {
        HashMap<String, Boolean> result = new HashMap<>();

        this.getServersQueue().forEach((serverName, players) -> {
            if (hubPlayer.isInQueue(serverName)) result.put("result", true);
        });

        result.putIfAbsent("result", false);

        return result.get("result");
    }
}
