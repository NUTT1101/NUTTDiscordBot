package com.github.nutt1101;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Config {
    private final static Plugin plugin = NUTTDiscordBot.getPlugin(NUTTDiscordBot.class);
    public static String botToken;
    public static String channelID;
    public static String crawlingWeb;

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
        
        botToken = config.isSet("Bot-Token") ? config.getString("Bot-Token") : null;
        channelID = config.isSet("Discord-Channel") ? config.getString("Discord-Channel") : null;
        crawlingWeb = config.isSet("Crawling-Web") ? config.getString("Crawling-Web") : null;
        
        bootEmAuthor = config.isSet("Boot-EmbedBuilder.Author.Text") ? config.getString("Boot-EmbedBuilder.Author.Text") : "機器人已開機";
        bootEmAuthorTextLink = config.isSet("Boot-EmbedBuilder.Author.Link") ? config.getString("Boot-EmbedBuilder.Author.Link") : "https://www.dyu.edu.tw";
        bootEmAuthorImageLink = config.isSet("Boot-EmbedBuilder.Author.Image") ? config.getString("Boot-EmbedBuilder.Author.Image") : "https://i.imgur.com/6zaOG3Q.png";
        bootEmHexColor = config.isSet("Boot-EmbedBuilder.HexColor") ? config.getString("Boot-EmbedBuilder.HexColor") : "#007654";
        bootEmDescription = config.isSet("Boot-EmbedBuilder.Description") ? config.getString("Boot-EmbedBuilder.Description") : "**這是一個可以爬取學校最新公告的爬蟲機器人**\n**開發: <@473018985299050497>**";
        
        unbootEmAuthor = config.isSet("Unboot-EmbedBuilder.Author.Text") ? config.getString("Unboot-EmbedBuilder.Author.Text") : "機器人已關機";
        unbootEmAuthorTextLink = config.isSet("Unboot-EmbedBuilder.Author.Link") ? config.getString("Unboot-EmbedBuilder.Author.Link") : "https://www.dyu.edu.tw";
        unbootEmAuthorImageLink = config.isSet("Unboot-EmbedBuilder.Author.Image") ? config.getString("Unboot-EmbedBuilder.Author.Image") : "https://i.imgur.com/6zaOG3Q.png";
        unbootEmHexColor = config.isSet("Unboot-EmbedBuilder.HexColor") ? config.getString("Unboot-EmbedBuilder.HexColor") : "#e51b23";
        unbootEmDescription = config.isSet("Unboot-EmbedBuilder.Description") ? config.getString("Unboot-EmbedBuilder.Description") : "***__已從伺服端離線__***";
        
    }
    
}
