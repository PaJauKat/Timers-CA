package com.TimersCA.Bosses;

import com.TimersCA.Boss;
import com.TimersCA.TimersCAConfig;
import com.TimersCA.TimersCAPlugin;
import net.runelite.api.Client;
import net.runelite.api.events.NpcChanged;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;
import java.util.List;

public class Whisperer extends Boss {

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




}
