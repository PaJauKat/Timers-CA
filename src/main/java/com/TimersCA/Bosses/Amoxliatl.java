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

public class Amoxliatl extends Boss {

    @Inject
    public Amoxliatl(Client client, TimersCAPlugin plugin, TimersCAConfig config) {
        super(client, plugin, config);
        this.name = "Amoxliatl";
        this.regionIDs = List.of(5446);
    }

    @Subscribe
    private void onNpcSpawned(NpcSpawned event) {
        if (event.getNpc().getId() == NpcID.AMOXLIATL) {
            this.startTick = client.getTickCount();
            this.onFight = true;
        }
    }

}
