package com.TimersCA.Bosses;

import com.TimersCA.Boss;
import com.TimersCA.TimersCAConfig;
import com.TimersCA.TimersCAPlugin;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.Client;
import net.runelite.api.events.NpcChanged;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.ui.overlay.components.LayoutableRenderableEntity;
import net.runelite.client.ui.overlay.components.LineComponent;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class Duke extends Boss {

    private static final int SLEEPING_DUKE = 12167;
    private static final int ALIVE_DUKE = 12191;
    protected static final int DEATH_DUKE = 12192;

    @Getter
    @Setter
    int ticks2revive = 0;
    private int dukeStart = -1;
    String prepTime = "";
    String dukeTime = "";
    boolean onPrep = false;
    boolean dead = false;

    @Inject
    public Duke(Client client, TimersCAPlugin plugin, TimersCAConfig config) {
        super(client, plugin, config);
        this.name = "Duke";
        this.regionIDs = List.of(12132);
    }



    @Subscribe
    private void onNpcSpawned(NpcSpawned event) {
        if (event.getNpc() == null) {
            return;
        }

        if (event.getNpc().getName() != null && event.getNpc().getName().equalsIgnoreCase("duke sucellus")) {
            startTick = client.getTickCount();
            onFight = true;
            ticks2revive = 0;
        }
    }

    @Subscribe
    private void onNpcDespawned(NpcDespawned event) {
        if (event.getNpc() == null) {
            return;
        }

        if (event.getNpc().getName() != null && event.getNpc().getName().equalsIgnoreCase("duke sucellus")) {
            ticks2revive = 0;
            onFight = false;
        }
    }

    @Subscribe
    private void onNpcChanged(NpcChanged event) {
        if (event.getNpc() == null) {
            return;
        }

        switch (event.getNpc().getId()) {
            case ALIVE_DUKE:
                prepTime = timeFighting;
                dukeStart = client.getTickCount();
                onPrep = false;
                break;
            case DEATH_DUKE:
                dead = true;
                break;
            case SLEEPING_DUKE:
                onPrep = true;
                dead = false;
                startTick =  client.getTickCount();
                prepTime = "";
                dukeTime = "";
                onFight = true;
                break;
        }
    }

    @Override
    public List<LayoutableRenderableEntity> getLines() {
        List<LayoutableRenderableEntity> lines = new ArrayList<>();
        lines.add(LineComponent.builder().left("Total").right(this.timeFighting).build());
        switch (config.prepTime()) {
            case ALWAYS:
                lines.add(LineComponent.builder().left("Prep").right(this.prepTime).build());
                break;
            case ON_DEATH:
                if (dead) {
                    lines.add(LineComponent.builder().left("Prep").right(this.prepTime).build());
                }
                break;
        }

        switch (config.dukeTime()) {
            case ALWAYS:
                lines.add(LineComponent.builder().left(this.name).right(this.dukeTime).build());
                break;
            case ON_DEATH:
                if (dead) {
                    lines.add(LineComponent.builder().left(this.name).right(this.dukeTime).build());
                }
                break;
        }
        return lines;
    }

    @Override
    public void updateTime() {
        ticks2revive--;
        if (onFight) {
            if (startTick > 0) {
                int ticks = client.getTickCount() - startTick + 1;
                if (ticks > 0) {
                    timeFighting = formatTime(ticks);
                }
            } else {
                timeFighting = formatTime(0);
            }

            if (onPrep) {
                if (startTick > 0) {
                    int ticks = client.getTickCount() - startTick + 1;
                    prepTime = formatTime(Math.max(ticks, 0));
                }
            } else {
                if (dukeStart > 0) {
                    int ticks = client.getTickCount() - dukeStart + 1;
                    dukeTime = formatTime(Math.max(ticks, 0));
                }
            }
        }

    }
}
