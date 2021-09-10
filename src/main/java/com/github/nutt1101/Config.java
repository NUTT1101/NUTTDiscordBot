package com.github.nutt1101;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Config {
    private final static Plugin plugin = NUTTDiscordBot.getPlugin(NUTTDiscordBot.class);
    public static String botToken;
    public static String channelID;
    public static String bootEmAuthor;
    public static String bootEmAuthorTextLink;
    public static String bootEmAuthorImageLink;
    public static String bootEmHexColor;
    public static String bootEmDescription;

    public static String unbootEmAuthor;
    public static String unbootEmAuthorTextLink;
    public static String unbootEmAuthorImageLink;
    public static String unbootEmHexColor;
    public static String unbootEmDescription;

    public static void pluginInit() {
        FileConfiguration config = plugin.getConfig();
        if (!new File(plugin.getDataFolder(), "config.yml").exists()) { plugin.saveResource("config.yml", false); }
        
        config.options().copyDefaults();

        botToken = config.getString("Bot-Token");
        channelID = config.getString("Discord-Channel");
        bootEmAuthor = config.getString("Boot-EmbedBuilder.Author.Text");
        bootEmAuthorImageLink = config.getString("Boot-EmbedBuilder.Author.Link");
        bootEmAuthorImageLink = config.getString("Boot-EmbedBuilder.Author.Image");
        bootEmHexColor = config.getString("Boot-EmbedBuilder.HexColor");
        bootEmDescription = config.getString("Boot-EmbedBuilder.Description");
        
        unbootEmAuthor = config.getString("Unboot-EmbedBuilder.Author.Text");
        unbootEmAuthorTextLink = config.getString("Unboot-EmbedBuilder.Author.Link");
        unbootEmAuthorImageLink = config.getString("Unboot-EmbedBuilder.Author.Image");
        unbootEmHexColor = config.getString("Unboot-EmbedBuilder.HexColor");
        unbootEmDescription = config.getString("Unboot-EmbedBuilder.Description");
        
    }
    
}
