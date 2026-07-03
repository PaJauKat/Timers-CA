package com.TimersCA.Bosses;

import com.TimersCA.Boss;
import com.TimersCA.TimersCAConfig;
import com.TimersCA.TimersCAPlugin;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.ui.overlay.components.LayoutableRenderableEntity;
import net.runelite.client.ui.overlay.components.LineComponent;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class MaggotKing extends Boss {

    private static final int MAGGOT_KING_ALIVE = 15742;
    private static final int MAGGOT_KING_DEAD = 15741;
    private static final int MAGGOT_KING_REGION_ID = 11645;

    private int lastRegionId = -1;
    private boolean newInstance = false;
    private int startTickCA = -1;

    private String timeCA = "";

    private int kills = -1;
    private boolean counting = false;

    @Inject
    public MaggotKing(Client client, TimersCAPlugin plugin, TimersCAConfig config) {
        super(client, plugin, config);
        this.name = "Maggot King";
        this.regionIDs = List.of(MAGGOT_KING_REGION_ID);
    }

    public void incrementKills() {
        this.kills++;
    }

    @Subscribe
    private void onGameTick(GameTick event) {
        WorldPoint localRealTile = WorldPoint.fromLocalInstance(client, client.getLocalPlayer().getLocalLocation());

        if (localRealTile.getRegionID() != lastRegionId) {
            if (localRealTile.getRegionID() == MAGGOT_KING_REGION_ID) {
                newInstance = true;
                kills = 0;
            } else {
                counting = false;
                newInstance = false;
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
            if (newInstance) {
                startTickCA = client.getTickCount();
                newInstance = false;
                counting = true;
            }
        }
    }

    @Override
    public void updateTime() {
        super.updateTime();
        if (counting) {
            this.timeCA = formatTime(Math.max(0, client.getTickCount() - this.startTickCA + 1));
        }
    }

    @Override
    public List<LayoutableRenderableEntity> getLines() {
        List<LayoutableRenderableEntity> lines = new ArrayList<>();
        lines.add(LineComponent.builder().left(this.name).right(timeFighting).build());
        if (config.maggotKingInstanceKills()) {
            lines.add(LineComponent.builder().left("Kills").right(String.valueOf(this.kills)).build());
        }
        return lines;
    }
}
