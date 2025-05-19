package com.TimersCA;

import com.TimersCA.Bosses.Araxxor;
import lombok.AllArgsConstructor;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.ui.overlay.infobox.Timer;

@ConfigGroup("timersCaKat")
public interface TimersCAConfig extends Config {

    String GROUP_NAME = "timersCaKat";

    @ConfigSection(
            name = "Duke Sucellus",
            description = "",
            position = 3
    )
    String dukeSection = "Duke Sucellus";

    @ConfigItem(
            name = "Ticks to respawn",
            description = "",
            keyName = "showDukeTicksToRespawn",
            section = dukeSection
    )
    default boolean showDukeTicksToRespawn() {
        return true;
    }

    @ConfigItem(
            name = "Show",
            description = "",
            keyName = "dukeShow",
            section = dukeSection
    )
    default boolean dukeShow() {
        return true;
    }

    @ConfigItem(
            name = "Duke Time",
            description = "",
            keyName = "dukeTime",
            section = dukeSection
    )
    default DisplayTimer dukeTime() {
        return DisplayTimer.ALWAYS;
    }

    @ConfigItem(
            name = "Prep Time",
            description = "",
            keyName = "prepTime",
            section = dukeSection
    )
    default DisplayTimer prepTime() {
        return DisplayTimer.ALWAYS;
    }


    enum DisplayTimer {
        ALWAYS,
        ON_DEATH,
        NEVER
    }

    //------------------------------------------------------------

    @ConfigSection(
            name = "Leviathan",
            description = "",
            position = 4,
            closedByDefault = true
    )
    String leviathanSection = "Leviathan";

    @ConfigItem(
            name = "Show",
            description = "",
            keyName = "leviathanShow",
            section = leviathanSection
    )
    default boolean leviathanShow() {
        return true;
    }
    //------------------------------------

    @ConfigSection(
            name = "Vardorvis",
            description = "",
            position = 7,
            closedByDefault = true
    )
    String vardorvisSection = "Vardorvis";

    @ConfigItem(
            name = "Show",
            description = "",
            keyName = "vardorvisShow",
            section = vardorvisSection
    )
    default boolean vardorvisShow() {
        return true;
    }

    //-------------------------------------------------------------
    @ConfigSection(
            name = "Whisperer",
            description = "",
            position = 5,
            closedByDefault = true
    )
    String whispererSection = "Whisperer";

    @ConfigItem(
            name = "Show",
            description = "",
            keyName = "whispererShow",
            section = whispererSection
    )
    default boolean whispererShow() {
        return true;
    }

    //-------------------------------------------------------------
    @ConfigSection(
            name = "Muspah",
            description = "",
            position = 7,
            closedByDefault = true
    )
    String muspahSection = "Muspah";

    @ConfigItem(
            name = "Show",
            description = "",
            keyName = "muspahShow",
            section = muspahSection
    )
    default boolean muspahShow() {
        return true;
    }

    //-------------------------------------------------------------
    /*@ConfigSection(
            name = "Nex",
            description = "",
            position = 7,
            closedByDefault = true
    )
    String nexSection = "Nex";

    @ConfigItem(
            name = "Show",
            description = "",
            keyName = "nexShow",
            section = nexSection
    )default boolean nexShow(){
        return true;}*/

    //------------------------------------

    @ConfigSection(
            name = "Zulrah",
            description = "",
            position = 7,
            closedByDefault = true
    )
    String zulrahSection = "Zulrah";

    @ConfigItem(
            name = "Show",
            description = "",
            keyName = "zulrahShow",
            section = zulrahSection
    )
    default boolean zulrahShow() {
        return true;
    }

    //------------------------------------

    @ConfigSection(
            name = "Vorkath",
            description = "",
            position = 7,
            closedByDefault = true
    )
    String vorkathSection = "Vorkath";

    @ConfigItem(
            name = "Show",
            description = "",
            keyName = "vorkathShow",
            section = vorkathSection
    )
    default boolean vorkathShow() {
        return true;
    }

    //------------------------------------------------------------

    @ConfigSection(
            name = "Hespori",
            description = "",
            position = 4,
            closedByDefault = true
    )
    String hesporiSection = "Hespori";

    @ConfigItem(
            name = "Show",
            description = "",
            keyName = "hesporiShow",
            section = hesporiSection
    )
    default boolean hesporiShow() {
        return true;
    }


    //-------------------------------------------------------------

    @ConfigSection(
            name = "Grotesque",
            description = "",
            position = 7,
            closedByDefault = true
    )
    String grotesqueSection = "Grotesque";

    @ConfigItem(
            name = "Show",
            description = "",
            keyName = "grotesqueShow",
            section = grotesqueSection
    )
    default boolean grotesqueShow() {
        return true;
    }

    //----------------------------------------------------------------

    @ConfigSection(
            name = "Hydra",
            description = "",
            position = 8,
            closedByDefault = true
    )
    String hydraSection = "Hydra";

    @ConfigItem(
            name = "Show",
            description = "",
            keyName = "hydraShow",
            section = hydraSection
    )
    default boolean hydraShow() {
        return true;
    }

    //----------------------------------------------------------------

    @ConfigSection(
            name = "Hueycoatl",
            description = "",
            position = 9,
            closedByDefault = true
    )
    String hueycoatlSection = "Hueycoatl";

    @ConfigItem(
            name = "Show",
            description = "",
            keyName = "hueycoatlShow",
            section = hueycoatlSection
    )
    default boolean hueycoatlShow() {
        return true;
    }

    @ConfigItem(
            name = "Show split",
            description = "",
            keyName = "hueycoatl",
            section = hueycoatlSection
    )
    default boolean hueycoatlSplit() {
        return true;
    }

    //----------------------------------------------------------------

    @ConfigSection(
            name = "Yama",
            description = "",
            position = 10,
            closedByDefault = true
    )
    String yamaSection = "Yama";

    @ConfigItem(
            name = "Show",
            description = "",
            keyName = "yamaShow",
            section = yamaSection
    )
    default boolean yamaShow() {
        return true;
    }

    @ConfigItem(
            name = "Show splits",
            description = "",
            keyName = "showYamaSplits",
            section = yamaSection
    )
    default boolean showYamaSplits() {
        return true;
    }

    @ConfigItem(
            name = "Show Minion Splits",
            description = "",
            keyName = "showMinionSplits",
            section = yamaSection
    )
    default boolean showMinionSplits() {
        return true;
    }

    //----------------------------------------------------------------

    @ConfigSection(
            name = "Amoxliatl",
            description = "",
            position = 9,
            closedByDefault = true
    )
    String amoxliatlSection = "Amoxliatl";

    @ConfigItem(
            name = "Show",
            description = "",
            keyName = "amoxliatlShow",
            section = amoxliatlSection
    )
    default boolean amoxliatlShow() {
        return true;
    }

    //----------------------------------------------------------------

    @ConfigSection(
            name = "Araxxor",
            description = "",
            position = 9,
            closedByDefault = true
    )
    String araxxorSection = "Araxxor";

    @ConfigItem(
            name = "Show",
            description = "",
            keyName = "araxxorShow",
            section = araxxorSection
    )
    default boolean araxxorShow() {
        return true;
    }

    @ConfigItem(
            name = "Stop at",
            description = "",
            keyName = "araxxorStop",
            section = araxxorSection
    )
    default AraxxorStop araxxorStop() {
        return AraxxorStop.SIX_KILLS;
    }

    @AllArgsConstructor
    enum AraxxorStop{
        SIX_KILLS("6 kills"),
        FIVE_KILLS("5 kills"),
        DONT_SHOW("Don't show"),
        NEVER("Never");

        private final String des;

        @Override
        public String toString() {
            return des;
        }
    }


    @AllArgsConstructor
    enum TimerType{
        TIMER("Timer"),
        CHRONOMETER("Chronometer");

        private final String des;

        @Override
        public String toString() {
            return this.des;
        }
    }
}
