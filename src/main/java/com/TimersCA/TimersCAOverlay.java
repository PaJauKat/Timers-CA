package com.TimersCA;

import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

import javax.inject.Inject;
import java.awt.*;

public class TimersCAOverlay extends Overlay {

    private final Client client;
    private final TimersCAPlugin plugin;
    private final TimersCAConfig config;

    @Inject
    public TimersCAOverlay(Client client, TimersCAPlugin plugin, TimersCAConfig config) {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        setLayer(OverlayLayer.ABOVE_SCENE);
        setPosition(OverlayPosition.DYNAMIC);
    }

    @Override
    public Dimension render(Graphics2D graphics) {

        if (config.showDukeTicksToRespawn() && plugin.getDuke().getTicks2revive() > 0 && plugin.getDuke().regionIDs.contains(WorldPoint.fromLocalInstance(client,client.getLocalPlayer().getLocalLocation()).getRegionID())) {
            graphics.setFont(new Font("Arial",Font.BOLD,16));
            Point pt = Perspective.getCanvasTextLocation(client, graphics, client.getLocalPlayer().getLocalLocation(), String.valueOf(plugin.getDuke().getTicks2revive()), 0);
            if (pt == null) {
                return null;
            }

            OverlayUtil.renderTextLocation(graphics, pt, String.valueOf(plugin.getDuke().getTicks2revive()), Color.ORANGE);
        }
        return null;
    }
}
