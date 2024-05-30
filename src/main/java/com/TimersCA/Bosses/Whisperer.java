package com.TimersCA.Bosses;

import com.TimersCA.Boss;
import com.TimersCA.TimersCAConfig;
import com.TimersCA.TimersCAPlugin;
import net.runelite.api.Client;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.NpcChanged;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;
import java.util.List;

public class Whisperer extends Boss {

    private static final WorldArea AREA_FIGHT = new WorldArea(2640, 6350, 37, 35, 0);
    private static final WorldArea AREA_FIGHT_SHADOW = new WorldArea(2383, 6349, 35, 36, 0);

    @Inject
    public Whisperer(Client client, TimersCAPlugin plugin, TimersCAConfig config) {
        super(client, plugin, config);
        this.regionIDs = List.of(10595, 9571);
        this.name = "Whisperer";
    }

    @Subscribe
    private void onNpcChanged(NpcChanged event) {
        if (event.getOld().getId() == 12203 && event.getNpc().getId() == 12204) {
            startTick = client.getTickCount();
            onFight = true;
        }
    }

    @Subscribe
    private void onGameTick(GameTick event) {
        if (!this.onFight) {
            return;
        }

        WorldPoint realLocalTile = WorldPoint.fromLocalInstance(client, client.getLocalPlayer().getLocalLocation());
        if (realLocalTile != null) {
            if (!realLocalTile.isInArea(AREA_FIGHT) && !realLocalTile.isInArea(AREA_FIGHT_SHADOW)) {
                this.onFight = false;
            }
        }
    }



}
