package com.TimersCA.Bosses;

import com.TimersCA.Boss;
import com.TimersCA.TimersCAConfig;
import com.TimersCA.TimersCAPlugin;
import net.runelite.api.Client;
import net.runelite.api.NpcID;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;
import java.util.List;

public class Muspah extends Boss {

    private static final List<Integer> MUSPAH_IDS = List.of(
            NpcID.MUSPAH,
            NpcID.PHANTOM_MUSPAH,
            NpcID.PHANTOM_MUSPAH_12078,
            NpcID.PHANTOM_MUSPAH_12079,
            NpcID.PHANTOM_MUSPAH_12080,
            NpcID.PHANTOM_MUSPAH_12082
    );


    @Inject
    public Muspah(Client client, TimersCAPlugin plugin, TimersCAConfig config) {
        super(client, plugin, config);
        this.name = "Muspah";
        this.regionIDs = List.of(11330);
    }

    @Subscribe
    private void onNpcSpawned(NpcSpawned event) {
        if (event.getNpc() == null || event.getNpc().getName() == null) {
            return;
        }

        if (event.getNpc().getId()==12077 || event.getNpc().getId()==12078 || event.getNpc().getName().contains("Muspah")) {
            this.startTick = client.getTickCount();
            this.onFight = true;
        }
    }

    @Subscribe
    private void onNpcDespawned(NpcDespawned event) {
        if (event.getNpc() == null || event.getNpc().getName() == null) {
            return;
        }

        if (MUSPAH_IDS.contains(event.getNpc().getId())) {
            onFight = false;
        }

    }



}
