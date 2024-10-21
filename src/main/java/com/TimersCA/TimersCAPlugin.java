package com.TimersCA;

import com.TimersCA.Bosses.*;
import com.google.inject.Provides;
import javax.inject.Inject;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.Text;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@PluginDescriptor(
		name = "Timers CA",
		description = "Shows the current time on some bosses listed in Combat Achievements",
		tags = {"pajau", "nex", "muspah", "zulrah", "vardorvis", "leviathan", "hespori", "vorkath", "whisperer", "duke", "timers", "ca", "combat achievement"}
)
public class TimersCAPlugin extends Plugin
{

	protected static final Pattern KILL_PATTERN = Pattern.compile("Your (?<bossName>[\\w\\s]+) kill count is: (?<kc>[,\\d]+)\\.");

	@Inject
	private Client client;

	@Inject
	private Nex nex;

	@Inject
	private Leviathan leviathan;

	@Inject
	private Muspah muspah;

	@Inject
	private Whisperer whisperer;

	@Inject
	@Getter
	private Duke duke;

	@Inject
	private Vardorvis vardorvis;

	@Inject
	private Zulrah zulrah;

	@Inject
	private Vorkath vorkath;

	@Inject
	private Hespori hespori;

	@Inject
	private Hydra hydra;

	@Inject
	private Grotesque grotesque;

	@Inject
	private Araxxor araxxor;

	@Inject
	private Hueycoatl hueycoatl;

	@Inject
	private Amoxliatl amoxliatl;

	@Inject
	private EventBus eventBus;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private TimersCAPanelOverlay timersCAPanelOverlay;

	@Inject
	private TimersCAOverlay overlay;

	@Inject
	private TimersCAConfig config;

	private final Set<Boss> bosses = new HashSet<>();
	protected int displayTicksRemaining = 0;
	private static final HashMap<Integer, Boss> bossMap = new HashMap<>();
	@Getter
	private Boss actualBoss = null;
	@Getter
	private Boss lastBoss = null;


	@Provides
	TimersCAConfig getConfig(ConfigManager configManager) {
		return configManager.getConfig(TimersCAConfig.class);
	}

	@Override
	protected void startUp() throws Exception {
		bosses.clear();
		bosses.add(nex);
		bosses.add(leviathan);
		bosses.add(muspah);
		bosses.add(whisperer);
		bosses.add(duke);
		bosses.add(zulrah);
		bosses.add(vorkath);
		bosses.add(vardorvis);
		bosses.add(hespori);
		bosses.add(grotesque);
		bosses.add(hydra);
		bosses.add(araxxor);
		bosses.add(hueycoatl);
		bosses.add(amoxliatl);

		this.overlayManager.add(overlay);
		this.overlayManager.add(timersCAPanelOverlay);

		bossMap.clear();

		for (Boss boss : bosses) {
			eventBus.register(boss);
			for (Integer regionID : boss.regionIDs) {
				bossMap.put(regionID, boss);
			}
		}
	}

	@Override
	protected void shutDown() throws Exception {
		this.overlayManager.remove(overlay);
		this.overlayManager.remove(timersCAPanelOverlay);

		for (Boss boss : bosses) {
			eventBus.unregister(boss);
		}
	}

	@Subscribe
	private void onGameTick(GameTick event) {
		displayTicksRemaining--;
		WorldPoint localRealTile = WorldPoint.fromLocalInstance(client, client.getLocalPlayer().getLocalLocation());
		actualBoss = bossMap.get(localRealTile.getRegionID());
		if (actualBoss == null || actualBoss != lastBoss) {
			if (lastBoss != null) {
				lastBoss.onFight = false;
				lastBoss.reset();
			}
		}
		if (actualBoss == null) {
			return;
		}
		lastBoss = actualBoss;
		displayTicksRemaining = 500;
		actualBoss.updateTime();
	}

	@Subscribe
	private void onChatMessage(ChatMessage event) {
		Matcher matcher = KILL_PATTERN.matcher(Text.removeTags(event.getMessage()));
		if (matcher.matches()) {
			if (lastBoss != null) {
				lastBoss.onFight = false;
			}
			if (lastBoss instanceof Duke) {
				duke.setTicks2revive(20);
			}
		}
	}

	@Subscribe
	private void onGameStateChanged(GameStateChanged event) {
		if (event.getGameState().getState() == GameState.LOGIN_SCREEN.getState()) {
			for (Boss boss : bosses) {
				boss.onFight = false;
			}
		}
	}

	public static WorldPoint toRealWorld(WorldPoint worldPoint,Client client) {
		LocalPoint lp = LocalPoint.fromWorld(client.getTopLevelWorldView(), worldPoint);
		if (lp == null) {
			return null;
		}
		return WorldPoint.fromLocalInstance(client, lp);
	}


}
