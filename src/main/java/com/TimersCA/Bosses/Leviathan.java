package com.TimersCA.Bosses;

import com.TimersCA.Boss;
import com.TimersCA.TimersCAConfig;
import com.TimersCA.TimersCAPlugin;
import net.runelite.api.Client;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;
import java.util.List;

public class Leviathan extends Boss {

    @Inject
    public Leviathan(Client client, TimersCAPlugin plugin, TimersCAConfig config) {
        super(client, plugin, config);
        this.regionIDs = List.of(8291);
        this.name = "Leviathan";
    }

    @Subscribe
    void onNpcSpawned(NpcSpawned event) {
        if (event.getNpc().getId() == 12214) {
            startTick = client.getTickCount();
            onFight = true;
        }
    }

    @Subscribe
    void onNpcDespawned(NpcDespawned event) {
        if (event.getNpc().getId() == 12214) {
            onFight = false;
        }
    }




}
