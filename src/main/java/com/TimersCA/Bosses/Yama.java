package com.TimersCA.Bosses;

import com.TimersCA.Boss;
import com.TimersCA.TimersCAConfig;
import com.TimersCA.TimersCAPlugin;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.GameTick;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.ui.overlay.components.LayoutableRenderableEntity;
import net.runelite.client.ui.overlay.components.LineComponent;

import javax.inject.Inject;
import javax.sound.sampled.Line;
import java.util.*;
import java.util.regex.Pattern;

public class Yama extends Boss {

    private final static int YAMA_NPC_ID = 14176;
    private final static Pattern YAMA_KILL_PATTERN = Pattern.compile("Your Yama success count is: (?<kc>[,\\d]+)\\.");
    private final static WorldArea LEFT_JUMP_AREA = new WorldArea(1476, 10076, 8, 26, 0);
    private final static WorldArea RIGHT_JUMP_AREA = new WorldArea(1525, 10076, 8, 26, 0);
    private final static int DEATH_YAMA_ANIMATION = 12161;
    private final static int SPAWN_YAMA_ANIMATION = 12156;




    private NPC yama = null;
    private int phase = 0;
    private int minionPhase = 0;
    private LinkedHashMap<Integer, String> phaseTimes = new LinkedHashMap<>();
    private LinkedHashMap<Integer, String> minionTimes = new LinkedHashMap<>();

    private LinkedHashMap<Integer, Integer> phasesStartTick = new LinkedHashMap<>();
    private LinkedHashMap<Integer, Integer> minionsStartTick = new LinkedHashMap<>();

    private Fighting currentFighting = null;
    private Fighting lastFighting = null;


    @Inject
    public Yama(Client client, TimersCAPlugin plugin, TimersCAConfig config) {
        super(client, plugin, config);
        this.name = "Yama";
        this.regionIDs = List.of(6045);
    }

    @Override
    public void reset() {
        super.reset();
        yama = null;
        phase = 0;
        minionPhase = 0;
        lastFighting = null;
        currentFighting = null;
    }

    @Subscribe(priority = 100)
    private void onGameTick(GameTick event) {

        WorldPoint localRealTile = WorldPoint.fromLocalInstance(client, client.getLocalPlayer().getLocalLocation());
        if (localRealTile.getRegionID() != 6045) {
            return;
        }

        if (yama == null) {
            yama = client.getTopLevelWorldView().npcs().stream().filter(x -> x.getId() == YAMA_NPC_ID).findFirst().orElse(null);
            if (yama != null && phase == 0) {
                startTick = client.getTickCount()-3;
                onFight = true;
                phaseTimes.clear();
                minionTimes.clear();
                phasesStartTick.clear();
                minionsStartTick.clear();
                currentFighting = Fighting.YAMA;
            }
        }

        if (yama != null) {
            if (yama.getId() != 14176) {
                onFight = false;
                reset();
                return;
            }
        }

        if (phase != 0) {
            if (localRealTile.isInArea(RIGHT_JUMP_AREA)) {
                currentFighting = Fighting.RIGHT_MINION;
            } else if (localRealTile.isInArea(LEFT_JUMP_AREA)) {
                currentFighting = Fighting.LEFT_MINION;
            } else {
                currentFighting = Fighting.YAMA;
            }
        }

        if (!Objects.equals(currentFighting, lastFighting)) {
            switch (currentFighting) {
                case YAMA:
                    phase++;
                    if (phase == 1) {
                        phasesStartTick.put(phase, client.getTickCount() - 3);
                    } else {
                        phasesStartTick.put(phase, client.getTickCount());
                    }
                    break;
                case LEFT_MINION:
                case RIGHT_MINION:
                    minionPhase++;
                    minionsStartTick.put(minionPhase, client.getTickCount());
                    break;
            }
            lastFighting = currentFighting;
        }
    }

    @Subscribe
    private void onAnimationChanged(AnimationChanged event) {

        if (event.getActor().equals(yama)) {
            if (event.getActor().getAnimation() == DEATH_YAMA_ANIMATION) {
                onFight = false;
            }
        }
    }

    @Override
    public void updateTime() {
        if (onFight) {
            this.timeFighting = formatTime(Math.max(0, client.getTickCount() - this.startTick));
            Integer st;
            switch (this.currentFighting) {
                case YAMA:
                    st = phasesStartTick.get(this.phase);
                    if (st != null) {
                        phaseTimes.put(this.phase, formatTime(Math.max(0, client.getTickCount() - st)));
                    }
                    break;
                case LEFT_MINION:
                case RIGHT_MINION:
                    st = minionsStartTick.get(this.minionPhase);
                    if (st != null) {
                        minionTimes.put(this.minionPhase, formatTime(Math.max(0, client.getTickCount() - st)));
                    }
                    break;
            }

        }
    }

    @Override
    public List<LayoutableRenderableEntity> getLines() {
        List<LayoutableRenderableEntity> lines = new ArrayList<>();

        if (!config.showYamaSplits()) {
            return super.getLines();
        } else {
            lines.add(LineComponent.builder().left("Total").right(this.timeFighting).build());
            if (config.showYamaSplits()) {
                if (phaseTimes != null) {
                    for (Map.Entry<Integer, String> entry : phaseTimes.entrySet()) {
                        lines.add(LineComponent.builder().left("Phase " + entry.getKey()).right(entry.getValue()).build());
                        if (config.showMinionSplits()) {
                            String s = minionTimes.get(entry.getKey());
                            if (s != null) {
                                lines.add(LineComponent.builder().left("Minion " + entry.getKey()).right(s).build());
                            }
                        }
                    }
                }
            }
        }

        return lines;
    }

    enum Fighting{
        YAMA,
        RIGHT_MINION,
        LEFT_MINION
    }


}
