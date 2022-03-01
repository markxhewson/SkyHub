package xyz.lotho.me.queues;

import xyz.lotho.me.SkyHub;
import xyz.lotho.me.managers.HubPlayer;

import java.util.ArrayList;
import java.util.HashMap;

public class Queue {

    private final SkyHub instance;

    public Queue(SkyHub instance) {
        this.instance = instance;
    }

    public void attemptQueue() {
        HashMap<String, ArrayList<HubPlayer>> serverQueue = this.instance.queueManager.getServersQueue();

        serverQueue.forEach((serverName, players) -> {
            if (players.size() <= 0) return;

            HubPlayer hubPlayer = players.get(0);
            hubPlayer.connect(serverName);
        });
    }
}
