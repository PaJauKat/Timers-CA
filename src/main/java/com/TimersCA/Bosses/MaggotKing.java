package com.TimersCA.Bosses;

import com.TimersCA.Boss;
import com.TimersCA.TimersCAConfig;
import com.TimersCA.TimersCAPlugin;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.ui.overlay.components.LayoutableRenderableEntity;
import net.runelite.client.ui.overlay.components.LineComponent;

import javax.inject.Inject;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class MaggotKing extends Boss {

    private static final int MAGGOT_KING_ALIVE = 15742;
    private static final int MAGGOT_KING_DEAD = 15741;
    private static final int MAGGOT_KING_REGION_ID = 11645;

    private int lastRegionId = -1;
    private String timeCA = "";

    private int kills = -1;
    private final int[] times = new int[5];
    private int currentKillTime = 0;
    private String expectedKillTime;

    @Inject
    public MaggotKing(Client client, TimersCAPlugin plugin, TimersCAConfig config) {
        super(client, plugin, config);
        this.name = "Maggot King";
        this.regionIDs = List.of(MAGGOT_KING_REGION_ID);
    }

    public void incrementKills() {
        this.kills++;
        if (this.kills > 5 || this.kills <= 0) {
            return;
        }
        try {
            times[kills - 1] = currentKillTime;
            int remainingKills = 5 - kills;
            if (remainingKills > 0) {
                int expectedTick = (900 - Arrays.stream(times).sum()) / remainingKills;
                expectedKillTime = formatTime(expectedTick);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            log.error("ArrayIndexOutOfBoundsException: {}", e.getMessage());
        }
    }

    @Subscribe
    private void onGameTick(GameTick event) {
        WorldPoint localRealTile = WorldPoint.fromLocalInstance(client, client.getLocalPlayer().getLocalLocation());

        if (localRealTile.getRegionID() != lastRegionId) {
            if (localRealTile.getRegionID() == MAGGOT_KING_REGION_ID) {
                kills = 0;
                currentKillTime = 0;
                Arrays.fill(times, 0);
                expectedKillTime = formatTime(900/5);
            }

            lastRegionId = localRealTile.getRegionID();
        }
    }

    @Subscribe
    private void onNpcSpawned(NpcSpawned event) {
        if (event.getNpc() == null) {
            return;
        }

        if (event.getNpc().getId() == MAGGOT_KING_ALIVE) {
            startTick = client.getTickCount();
            onFight = true;
        }
    }

    @Override
    public void updateTime() {
        WorldPoint localRealTile = WorldPoint.fromLocalInstance(client, client.getLocalPlayer().getLocalLocation());

        if (this.onFight && this.regionIDs.contains(localRealTile.getRegionID())) {
            currentKillTime = Math.max(0,client.getTickCount() - this.startTick + 1);
            this.timeFighting = formatTime(currentKillTime);

            int sumTime = Arrays.stream(times).sum() + currentKillTime;
            this.timeCA = formatTime(sumTime);
        }
    }

    @Override
    public List<LayoutableRenderableEntity> getLines() {
        List<LayoutableRenderableEntity> lines = new ArrayList<>();
        lines.add(LineComponent.builder().left(this.name).right(timeFighting).build());
        if (config.maggotKingInstanceKills()) {
            lines.add(LineComponent.builder().left("Kills").right(String.valueOf(this.kills)).build());
            switch (config.maggotCaKills()) {
                case DETAILED:
                    for (int i = 0; i < times.length; i++) {
                        Color color = times[i] > 180 ? Color.GREEN : Color.RED;
                        lines.add(LineComponent.builder().left("Kill " + (i + 1)).right(formatTime(times[i])).rightColor(color).build());
                    }
                    lines.add(LineComponent.builder().left("C.A. (" + Arrays.stream(times).filter(t -> t > 0).count() + ")").right(this.timeCA).build());
                    break;
                case CONDENSED:
                    lines.add(LineComponent.builder().left("C.A. (" + Arrays.stream(times).filter(t -> t > 0).count() + ")").right(this.timeCA).build());
                    break;
            }
            if (config.maggotShowExpectedKillTime() && this.kills < 5 && this.expectedKillTime != null) {
                lines.add(LineComponent.builder().left("E.K.T.").right(this.expectedKillTime).build());
            }
        }
        return lines;
    }
}
