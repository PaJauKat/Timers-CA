package com.TimersCA.Bosses;

import com.TimersCA.Boss;
import com.TimersCA.TimersCAConfig;
import com.TimersCA.TimersCAPlugin;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.NpcChanged;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcSpawned;
import net.runelite.api.gameval.NpcID;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;
import java.util.List;

public class Titans extends Boss {
    private static final int BRANDA_ID = 12596;
    private static final int ELDRIC_ID = 14147;
    public static final int TITANS_REGION_ID = 11669;
    private int lastRegionId = -1;
    private int kills = 0;
    private boolean spawned = false;
    private boolean newInstance = false;

    @Inject
    public Titans(Client client, TimersCAPlugin plugin, TimersCAConfig config) {
        super(client, plugin, config);
        this.name = "Royal Titans";
        this.regionIDs = List.of(11669);
    }

    @Subscribe
    private void onGameTick(GameTick event) {
        WorldPoint localRealTile = WorldPoint.fromLocalInstance(client, client.getLocalPlayer().getLocalLocation());

        if (localRealTile.getRegionID() != lastRegionId) {
            if (localRealTile.getRegionID() == TITANS_REGION_ID) {
                newInstance = true;
            }

            lastRegionId = localRealTile.getRegionID();
        }

        if (spawned) {
            if (newInstance) {
                startTick = client.getTickCount() + 1;
                newInstance = false;
            } else {
                startTick = client.getTickCount() + 1;
            }

            onFight = true;
            kills = 0;

            spawned = false;
        }
    }

    @Subscribe
    private void onNpcChanged(NpcChanged event) {
        if (event.getNpc() == null) {
            return;
        }
        if (event.getNpc().getId() == NpcID.RT_FIRE_QUEEN_INACTIVE || event.getNpc().getId() == NpcID.RT_ICE_KING_INACTIVE) {
            kills++;
        }
        if (kills == 2) {
            onFight = false;
            kills = 0;
        }
    }

    @Subscribe
    private void onNpcSpawned(NpcSpawned event) {
        if (event.getNpc() == null) {
            return;
        }

        int npcId = event.getNpc().getId();
        if (npcId == NpcID.RT_FIRE_QUEEN) {
            spawned = true;
        }
    }
}
