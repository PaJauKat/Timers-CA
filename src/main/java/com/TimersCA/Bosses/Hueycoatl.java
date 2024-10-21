package com.TimersCA.Bosses;

import com.TimersCA.Boss;
import com.TimersCA.TimersCAConfig;
import com.TimersCA.TimersCAPlugin;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.NPC;
import net.runelite.api.NpcID;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.*;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.ui.overlay.components.LayoutableRenderableEntity;
import net.runelite.client.ui.overlay.components.LineComponent;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class Hueycoatl extends Boss {

    private String lastText = null;
    private int lastPoseAnim = -1;
    private final WorldPoint seerInitialLoc = new WorldPoint(1530, 3284, 0);
    private boolean onTail = false;
    private String tailTime = "";
    private String huecoTime = "";
    private int huecoStart = -1;

    @Inject
    public Hueycoatl(Client client, TimersCAPlugin plugin, TimersCAConfig config) {
        super(client, plugin, config);
        this.name = "Hueycoatl";
        this.regionIDs = List.of(5939);
    }

    @Subscribe
    private void onNpcChanged(NpcChanged event) {
        if (event.getOld().getId() == NpcID.THE_HUEYCOATL && event.getNpc().getId() == NpcID.THE_HUEYCOATL_14012) {
            onFight = false;
        }
    }


    @Subscribe
    private void onNpcSpawned(NpcSpawned event) {
        if (event.getNpc() == null) {
            return;
        }

        if (event.getNpc().getId() == 14017) {
            this.startTick = client.getTickCount();
            this.onTail = true;
            this.onFight = true;
        }
    }


    @Override
    public void updateTime() {
        if (onFight) {
            if (startTick > 0) {
                int ticks = client.getTickCount() - startTick + 1;
                if (ticks > 0) {
                    timeFighting = formatTime(ticks);
                }
            } else {
                timeFighting = formatTime(0);
            }

            if (onTail) {
                if (startTick > 0) {
                    int ticks = client.getTickCount() - startTick + 1;
                    tailTime = formatTime(Math.max(ticks, 0));
                }
            } else {
                if (huecoStart > 0) {
                    int ticks = client.getTickCount() - huecoStart + 1;
                    huecoTime = formatTime(Math.max(ticks, 0));
                }
            }
        }

    }

    @Override
    public List<LayoutableRenderableEntity> getLines() {
        List<LayoutableRenderableEntity> lines = new ArrayList<>();
        lines.add(LineComponent.builder().left("Total").right(this.timeFighting).build());
        if (config.hueycoatlSplit()) {
            lines.add(LineComponent.builder().left("Body").right(this.tailTime).build());
            lines.add(LineComponent.builder().left(this.name).right(this.huecoTime).build());
        }
        return lines;
    }

    @Subscribe
    private void onPostAnimation(PostAnimation event) {
        if (event.getAnimation().getId() == 11700) {
            onTail = false;
            huecoStart = client.getTickCount();
        }
    }



}
