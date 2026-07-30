package com.TimersCA.Bosses;

import com.TimersCA.Boss;
import com.TimersCA.TimersCAConfig;
import com.TimersCA.TimersCAPlugin;
import net.runelite.api.Client;
import net.runelite.api.events.NpcChanged;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;
import java.util.List;

public class MadAngel extends Boss {

    private static final int MAD_ANGEL_ALIVE = 16305;
    private static final int MAD_ANGEL_DEAD = 16308;
    private static final int MAD_ANGEL_REGION_ID = 10018;

    @Inject
    public MadAngel(Client client, TimersCAPlugin plugin, TimersCAConfig config) {
        super(client, plugin, config);
        this.name = "Mad Angel";
        this.regionIDs = List.of(MAD_ANGEL_REGION_ID);
        this.isInstance = true;
    }

    @Subscribe
    private void onNpcSpawned(NpcSpawned event) {
        if (event.getNpc() == null) {
            return;
        }

        if (event.getNpc().getId() == MAD_ANGEL_ALIVE) {
            startTick = client.getTickCount();
            onFight = true;
        }
    }

    @Subscribe
    private void onNpcChanged(NpcChanged event) {
        if (event.getNpc() == null) {
            return;
        }

        if (event.getNpc().getId() == MAD_ANGEL_DEAD) {
            onFight = false;
        } else if (event.getNpc().getId() == MAD_ANGEL_ALIVE) {
            startTick = client.getTickCount();
            onFight = true;
        }
    }
}
