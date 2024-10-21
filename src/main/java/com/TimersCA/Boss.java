package com.TimersCA;

import net.runelite.api.Client;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.components.LayoutableRenderableEntity;
import net.runelite.client.ui.overlay.components.LineComponent;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public abstract class Boss {

    protected static final Pattern KILL_PATTERN = Pattern.compile("Your (?<bossName>[\\w\\s]+) kill count is: (?<kc>\\d+)\\.");

    protected final Client client;
    protected final TimersCAPlugin plugin;
    protected final TimersCAConfig config;

    @Inject
    public Boss(Client client, TimersCAPlugin plugin, TimersCAConfig config) {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
    }

    protected boolean show = true;
    protected String name = "";
    protected int startTick = -1;
    protected String timeFighting = "";
    protected boolean onFight = false;
    protected List<Integer> regionIDs = new ArrayList<>();
    protected boolean isInstance = true;


    public void reset(){};


    public boolean display(){
        return this.regionIDs.stream().anyMatch(x -> client.getLocalPlayer().getWorldLocation().getRegionID() == x) && (!isInstance || client.getTopLevelWorldView().isInstance());
    }

    public List<LayoutableRenderableEntity> getLines() {
        List<LayoutableRenderableEntity> lines = new ArrayList<>();
        lines.add(LineComponent.builder().left(this.name).right(timeFighting).build());
        return lines;
    }

    public void updateTime() {
        WorldPoint localRealTile = WorldPoint.fromLocalInstance(client, client.getLocalPlayer().getLocalLocation());
        if (this.onFight && this.regionIDs.contains(localRealTile.getRegionID())) {
            this.timeFighting = formatTime(Math.max(0, client.getTickCount() - this.startTick + 1));
        }
    }

    public String formatTime(int ticks) {
        int millis = ticks * 600;
        return String.format("%2d:%02d:%s",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) % 60,
                String.valueOf(millis%1000).charAt(0));
    }

}
