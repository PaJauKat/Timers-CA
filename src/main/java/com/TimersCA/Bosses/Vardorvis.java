package com.TimersCA.Bosses;

import com.TimersCA.Boss;
import com.TimersCA.TimersCAConfig;
import com.TimersCA.TimersCAPlugin;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.events.HitsplatApplied;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;
import java.util.List;

public class Vardorvis extends Boss {

    @Inject
    public Vardorvis(Client client, TimersCAPlugin plugin, TimersCAConfig config) {
        super(client, plugin, config);
        this.name = "Vardorvis";
        this.regionIDs = List.of(4405);
    }


    @Subscribe
    private void onHitsplatApplied(HitsplatApplied event) {
        if (!(event.getActor() instanceof NPC)) {
            return;
        }
        NPC npc = (NPC) event.getActor();

        if (!onFight && npc.getId() == 12223) {
            onFight = true;
            startTick = client.getTickCount();
        }
    }
}
