package com.TimersCA;

import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;

import javax.inject.Inject;
import java.awt.*;

public class TimersCAPanelOverlay extends OverlayPanel {
    private final Client client;
    private final TimersCAConfig config;
    private final TimersCAPlugin plugin;
    private final ConfigManager configManager;

    @Inject
    public TimersCAPanelOverlay(Client client, TimersCAConfig config, TimersCAPlugin plugin, ConfigManager configManager) {
        this.client = client;
        this.config = config;
        this.plugin = plugin;
        this.configManager = configManager;

        setLayer(OverlayLayer.UNDER_WIDGETS);
        setPosition(OverlayPosition.TOP_LEFT);
    }


    @Override
    public Dimension render(Graphics2D graphics) {
        if (plugin.displayTicksRemaining > 0) {
            if (plugin.getLastBoss() != null) {
                if (configManager.getConfiguration(TimersCAConfig.GROUP_NAME,plugin.getLastBoss().getClass().getSimpleName().toLowerCase()+"Show",Boolean.class)) {
                    panelComponent.getChildren().addAll(plugin.getLastBoss().getLines());
                }

            }
        }

        return super.render(graphics);
    }
}
