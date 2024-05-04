package com.TimersCA.Bosses;

import com.TimersCA.Boss;
import com.TimersCA.TimersCAConfig;
import com.TimersCA.TimersCAPlugin;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.NpcID;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;
import java.util.List;

public class Zulrah extends Boss {
    private static final List<Integer> ZULRAH_IDS = List.of(
            NpcID.ZULRAH,
            NpcID.ZULRAH_2043,
            NpcID.ZULRAH_2044
    );

    private int lastAnimation = -1;

    @Inject
    public Zulrah(Client client, TimersCAPlugin plugin, TimersCAConfig config) {
        super(client, plugin, config);
        this.name = "Zulrah";
        this.regionIDs = List.of(9007, 9008);
    }

    @Subscribe
    private void onAnimationChanged(AnimationChanged event) {
        if (event.getActor() instanceof NPC) {
            NPC npc = (NPC) event.getActor();
            if (ZULRAH_IDS.contains(npc.getId())) {
                if (lastAnimation == 5071 && npc.getAnimation() != lastAnimation) {
                    startTick = client.getTickCount()-1;
                    onFight = true;
                } else if (npc.getAnimation() == 5804) {
                    onFight = false;
                }
                lastAnimation = npc.getAnimation();
            }
            
        }
    }

    @Subscribe
    private void onNpcSpawned(NpcSpawned event) {
        if (event.getNpc() == null) {
            return;
        }
        /*if (ZULRAH_IDS.contains(event.getNpc().getId())) {
            startTick = client.getTickCount();
            onFight = true;
        }*/
    }



}
