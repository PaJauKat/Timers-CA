package com.TimersCA.Bosses;

import com.TimersCA.Boss;
import com.TimersCA.TimersCAConfig;
import com.TimersCA.TimersCAPlugin;
import net.runelite.api.Client;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.NpcChanged;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;
import java.util.List;

public class Hydra extends Boss {

    private static final int HYDRA_POISON_NPC_ID = 8615;
    private static final int HYDRA_DEATH_NPC_ID = 8622;
    private static final WorldArea AREA_HYDRA_ROOM = new WorldArea(1356, 10257, 22, 22, 0);

    private boolean onHydraRoom = false;

    @Inject
    public Hydra(Client client, TimersCAPlugin plugin, TimersCAConfig config) {
        super(client, plugin, config);
        this.name = "Hydra";
        this.regionIDs = List.of(5536);
    }

    @Subscribe
    private void onNpcSpawned(NpcSpawned event) {
        if (event.getNpc() == null) {
            return;
        }

        WorldPoint realLocalTile = TimersCAPlugin.toRealWorld(client.getLocalPlayer().getWorldLocation(), client);

        if (realLocalTile != null && realLocalTile.isInArea(AREA_HYDRA_ROOM)) {
            if (event.getNpc().getId() == HYDRA_POISON_NPC_ID) {
                startTick = client.getTickCount()+1;
                onFight = true;
            }
        }
    }

    /*@Subscribe
    private void onNpcChanged(NpcChanged event) {
        if (event.getNpc() == null) {
            return;
        }

        if (event.getNpc().getId() == HYDRA_DEATH_NPC_ID) {
            onFight = false;
        }

    }*/

    @Subscribe(priority = 10)
    private void onGameTick(GameTick event) {
        WorldPoint realLocalTile = TimersCAPlugin.toRealWorld(client.getLocalPlayer().getWorldLocation(), client);
        if (realLocalTile == null) {
            return;
        }

        if (realLocalTile.isInArea(AREA_HYDRA_ROOM)) {
            if (!onHydraRoom) {
                startTick = client.getTickCount()+1;
                onFight = true;
                onHydraRoom = true;
            }
        } else {
            onHydraRoom = false;
        }

    }



    @Subscribe
    private void onNpcDespawned(NpcDespawned event) {
        if (event.getNpc() == null || event.getNpc().getName() == null) {
            return;
        }

        if (event.getNpc().getName().equalsIgnoreCase("alchemical hydra")) {
            onFight = false;
        }

    }


}
