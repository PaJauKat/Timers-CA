package com.TimersCA.Bosses;

import com.TimersCA.Boss;
import com.TimersCA.TimersCAConfig;
import com.TimersCA.TimersCAPlugin;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.NpcID;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.NpcChanged;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.ui.overlay.components.LayoutableRenderableEntity;
import net.runelite.client.ui.overlay.components.LineComponent;

import javax.inject.Inject;
import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.List;

public class Araxxor extends Boss {

    private int lastRegionId = -1;
    private boolean newInstance = false;
    private int startTickCA = -1;
    private int lastAnimID = -1;

    private String timeCA = "";

    private int kills = -1;
    private boolean counting = false;

    @Inject
    public Araxxor(Client client, TimersCAPlugin plugin, TimersCAConfig config) {
        super(client, plugin, config);
        this.name = "Araxxor";
        this.regionIDs = List.of(14489);
    }

    //11482 anim

    @Subscribe
    private void onGameTick(GameTick event) {
        WorldPoint localRealTile = WorldPoint.fromLocalInstance(client, client.getLocalPlayer().getLocalLocation());

        if (localRealTile.getRegionID() != lastRegionId) {
            if (localRealTile.getRegionID() == 14489) {
                newInstance = true;
                kills = 0;
            } else {
                lastAnimID = -1;
                counting = false;
                newInstance = false;
            }
            lastRegionId = localRealTile.getRegionID();
        }
    }

    @Subscribe
    private void onAnimationChanged(AnimationChanged event) {
        if (!(event.getActor() instanceof NPC)) {
            return;
        }

        if (((NPC) event.getActor()).getId() == NpcID.ARAXXOR) {
            if (event.getActor().getAnimation() != lastAnimID) {
                if (lastAnimID == 11482) {
                    startTick = client.getTickCount()-1;
                    onFight = true;
                    if (newInstance) {
                        startTickCA = client.getTickCount()-1;
                        newInstance = false;
                        counting = true;
                    }
                }
                lastAnimID = event.getActor().getAnimation();
            }
        }
    }

    @Subscribe
    private void onNpcChanged(NpcChanged event) {
        if (event.getOld().getId() == NpcID.ARAXXOR && event.getNpc().getId() == NpcID.ARAXXOR_13669) {
            this.kills++;
            switch (config.araxxorStop()) {
                case SIX_KILLS:
                    if (kills >= 6) {
                        counting = false;
                    }
                    break;
                case FIVE_KILLS:
                    if (kills >= 5) {
                        counting = false;
                    }
                    break;
            }
        }
    }

    @Override
    public void updateTime() {
        super.updateTime();
        if (counting) {
            this.timeCA = formatTime(Math.max(0,client.getTickCount() - this.startTickCA + 1));
        }

    }

    @Override
    public List<LayoutableRenderableEntity> getLines() {
        List<LayoutableRenderableEntity> lines = new ArrayList<>();
        lines.add(LineComponent.builder().left(this.name).right(timeFighting).build());
        if (config.araxxorStop() != TimersCAConfig.AraxxorStop.DONT_SHOW) {
            lines.add(LineComponent.builder().left("C.A.").right(this.timeCA).build());
            lines.add(LineComponent.builder().left("Kills").right(String.valueOf(this.kills)).build());
        }
        return lines;
    }
}
