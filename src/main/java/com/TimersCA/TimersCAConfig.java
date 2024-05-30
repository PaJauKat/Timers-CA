package com.TimersCA;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

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
    )default boolean showDukeTicksToRespawn(){
        return true;
    }

    @ConfigItem(
            name = "Show",
            description = "",
            keyName = "dukeShow",
            section = dukeSection
    )default boolean dukeShow(){
        return true;}

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
            position = 4
    )
    String leviathanSection = "Leviathan";

    @ConfigItem(
            name = "Show",
            description = "",
            keyName = "leviathanShow",
            section = leviathanSection
    )default boolean leviathanShow(){
        return true;}
    //------------------------------------

    @ConfigSection(
            name = "Vardorvis",
            description = "",
            position = 7
    )
    String vardorvisSection = "Vardorvis";

    @ConfigItem(
            name = "Show",
            description = "",
            keyName = "vardorvisShow",
            section = vardorvisSection
    )default boolean vardorvisShow(){
        return true;}

    //-------------------------------------------------------------
    @ConfigSection(
            name = "Whisperer",
            description = "",
            position = 5
    )
    String whispererSection = "Whisperer";

    @ConfigItem(
            name = "Show",
            description = "",
            keyName = "whispererShow",
            section = whispererSection
    )default boolean whispererShow(){
        return true;}

    //-------------------------------------------------------------
    @ConfigSection(
            name = "Muspah",
            description = "",
            position = 7
    )
    String muspahSection = "Muspah";

    @ConfigItem(
            name = "Show",
            description = "",
            keyName = "muspahShow",
            section = muspahSection
    )default boolean muspahShow(){
        return true;}

    //-------------------------------------------------------------
    @ConfigSection(
            name = "Nex",
            description = "",
            position = 7
    )
    String nexSection = "Nex";

    @ConfigItem(
            name = "Show",
            description = "",
            keyName = "nexShow",
            section = nexSection
    )default boolean nexShow(){
        return true;}

    //------------------------------------

    @ConfigSection(
            name = "Zulrah",
            description = "",
            position = 7
    )
    String zulrahSection = "Zulrah";

    @ConfigItem(
            name = "Show",
            description = "",
            keyName = "zulrahShow",
            section = zulrahSection
    )default boolean zulrahShow(){
        return true;}

    //------------------------------------

    @ConfigSection(
            name = "Vorkath",
            description = "",
            position = 7
    )
    String vorkathSection = "Vorkath";

    @ConfigItem(
            name = "Show",
            description = "",
            keyName = "vorkathShow",
            section = vorkathSection
    )default boolean vorkathShow(){
        return true;}

    //------------------------------------------------------------

    @ConfigSection(
            name = "Hespori",
            description = "",
            position = 4
    )
    String hesporiSection = "Hespori";

    @ConfigItem(
            name = "Show",
            description = "",
            keyName = "hesporiShow",
            section = hesporiSection
    )default boolean hesporiShow(){
        return true;}



    //-------------------------------------------------------------

    @ConfigSection(
            name = "Grotesque",
            description = "",
            position = 7
    )
    String grotesqueSection = "Grotesque";

    @ConfigItem(
            name = "Show",
            description = "",
            keyName = "grotesqueShow",
            section = grotesqueSection
    )
    default boolean grotesqueShow() {
        return true;}

}
