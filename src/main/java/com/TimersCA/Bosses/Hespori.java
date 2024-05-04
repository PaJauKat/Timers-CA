package com.TimersCA.Bosses;

import com.TimersCA.Boss;
import com.TimersCA.TimersCAConfig;
import com.TimersCA.TimersCAPlugin;
import net.runelite.api.Client;
import net.runelite.api.NpcID;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;
import java.util.List;

public class Hespori extends Boss {

    @Inject
    public Hespori(Client client, TimersCAPlugin plugin, TimersCAConfig config) {
        super(client, plugin, config);
        this.name = "Hespori";
        this.regionIDs = List.of(5021, 4765);
    }

    @Subscribe
    private void onNpcSpawned(NpcSpawned event) {
        if (event.getNpc() == null) {
            return;
        }

        if (event.getNpc().getId() == NpcID.HESPORI) {
            startTick = client.getTickCount() + 1;
            onFight = true;
        }
    }

}
