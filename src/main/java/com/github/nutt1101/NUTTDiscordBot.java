package com.github.nutt1101;

import java.util.logging.Level;
import java.awt.Color;

import com.github.nutt1101.discord_listener.BotCommand;
import com.github.nutt1101.discord_listener.BotReady;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class NUTTDiscordBot extends JavaPlugin{
    public static JDA jda;

    @Override
    public void onEnable() {
        this.getLogger().log(Level.INFO, ChatColor.GREEN + "Power by NUTT1101");
        Config.pluginInit();
        try {
            jda = JDABuilder.createDefault(Config.botToken).build();
            jda.addEventListener(new BotReady());
            jda.addEventListener(new BotCommand());
            jda.awaitReady();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public void onDisable() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setAuthor(Config.unbootEmAuthor, Config.unbootEmAuthorTextLink, Config.unbootEmAuthorImageLink);
        embedBuilder.setColor(Color.decode(Config.unbootEmHexColor));
        embedBuilder.setDescription(Config.unbootEmDescription);
        jda.getTextChannelById(Config.channelID).sendMessage(embedBuilder.build()).queue();
        jda.shutdown();
    }

    
}
