package com.TimersCA.Bosses;

import com.TimersCA.Boss;
import com.TimersCA.TimersCAConfig;
import com.TimersCA.TimersCAPlugin;
import net.runelite.api.Client;

import javax.inject.Inject;

public class Nex extends Boss {
    @Inject
    public Nex(Client client, TimersCAPlugin plugin, TimersCAConfig config) {
        super(client, plugin, config);
    }
}
