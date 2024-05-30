package com.TimersCA.Bosses;

import com.TimersCA.Boss;
import com.TimersCA.TimersCAConfig;
import com.TimersCA.TimersCAPlugin;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.NpcID;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.NpcChanged;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;
import java.util.List;

public class Grotesque extends Boss {
    private static final List<Integer> DAWN_IDS = List.of(NpcID.DAWN, NpcID.DAWN_7853, NpcID.DAWN_7884, NpcID.DAWN_7885);
    private static final List<Integer> DUSK_IDS = List.of(NpcID.DUSK, NpcID.DUSK_7855, NpcID.DUSK_7854, NpcID.DUSK_7882,NpcID.DUSK_7883);


    @Inject
    public Grotesque(Client client, TimersCAPlugin plugin, TimersCAConfig config) {
        super(client, plugin, config);
        this.name = "Grotesque";
        this.regionIDs = List.of(6727);
    }


    @Subscribe
    private void onNpcSpawned(NpcSpawned event) {
        if (event.getNpc() == null) {
            return;
        }

        if (event.getNpc().getId() == 7852) {
            startTick = client.getTickCount();
            onFight = true;
        }

    }

    @Subscribe
    private void onNpcChanged(NpcChanged event) {
        if (event.getNpc() == null) {
            return;
        }

        if (event.getOld().getId() == 7888 && event.getNpc().getId() == 7889) {
            onFight = false;
        }
    }

    @Subscribe
    private void onAnimationChanged(AnimationChanged event) {
        if (event.getActor() instanceof NPC) {
            NPC npc = (NPC) event.getActor();
            if (npc.getId() == 7888) {
                if (npc.getAnimation() == 7803) {
                    onFight = false;
                }
            }
        }
    }

    @Subscribe
    private void onNpcDespawned(NpcDespawned event) {
        if (event.getNpc() == null) {
            return;
        }

        if (event.getNpc().getName() != null && event.getNpc().getName().equalsIgnoreCase("dusk")) {
            onFight = false;
        }

    }

}
