package com.TimersCA.Bosses;

import com.TimersCA.Boss;
import com.TimersCA.TimersCAConfig;
import com.TimersCA.TimersCAPlugin;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.NpcID;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.NpcChanged;
import net.runelite.api.events.NpcDespawned;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;
import java.util.List;

public class Vorkath extends Boss {
    private static final List<Integer> VORKATH_IDS = List.of(
            NpcID.VORKATH_8061,
            NpcID.VORKATH,
            NpcID.VORKATH_8058,
            NpcID.VORKATH_11959,
            NpcID.VORKATH_8060,
            NpcID.VORKATH_8059);

    @Inject
    public Vorkath(Client client, TimersCAPlugin plugin, TimersCAConfig config) {
        super(client, plugin, config);
        name = "Vorkath";
        regionIDs = List.of(9023);
    }

    NPC vorkath = null;

    @Subscribe
    private void onNpcChanged(NpcChanged event) {
        if (event.getNpc() == null) {
            return;
        }

        if (event.getOld().getId() == 8058 && event.getNpc().getId() == 8061) {
            startTick = client.getTickCount();
            onFight = true;
            vorkath = event.getNpc();
        }
    }

    @Subscribe
    private void onNpcDespawned(NpcDespawned event) {
        if (event.getNpc() == null || event.getNpc().getName() == null) {
            return;
        }
        if (VORKATH_IDS.stream().anyMatch(x->x == event.getNpc().getId())) {
            onFight = false;
        }
    }

    @Subscribe(priority = -10)
    private void onGameTick(GameTick event) {
        if (vorkath != null && vorkath.isDead()) {
            onFight = false;
        }
    }



}
