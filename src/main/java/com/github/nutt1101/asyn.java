package com.github.nutt1101;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.nutt1101.discord_listener.BotCommand;
import com.google.gson.JsonArray;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jsoup.nodes.Document;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;

public class Asyn {
    private Plugin plugin = NUTTDiscordBot.getPlugin(NUTTDiscordBot.class);
    private final JDA jda = NUTTDiscordBot.jda;
    private List<String> idList = new ArrayList<>();
    private EmbedBuilder embedBuilder = new EmbedBuilder();

    public void run() {
        new BukkitRunnable(){
            
            @Override
            public void run() {            
                try {
                    Document document = SSLHelper.getConnection("https://bulletin.dyu.edu.tw/index.php?goBack=1&isHidden=1&pool_ID=37").get();
                    JsonArray jsonArray = BotCommand.getDataJsonaArray(document);

                    if (idList.isEmpty()) {
                        for (int i=0; i < 10; i++) {
                            if (jsonArray.get(i) != null) {
                                idList.add(jsonArray.get(i).getAsJsonObject().get("ID").getAsString());
                            }
                        }
                    } 
                    
                    for (int i=0; i < 10; i++) {
                        if (!idList.contains(jsonArray.get(i).getAsJsonObject().get("ID").getAsString())) {
                            String title = jsonArray.get(i).getAsJsonObject().get("title").getAsString();
                            
                            embedBuilder.setAuthor("新生專區有最新消息!", 
                                BotCommand.getLink(jsonArray.get(0).getAsJsonObject().get("ID").getAsString()) , 
                                Config.bootEmAuthorImageLink);
                            embedBuilder.setDescription("[" + title + "]" + "("+ BotCommand.getLink(jsonArray.get(i).getAsJsonObject().get("ID").getAsString()) +")");
                            embedBuilder.setColor(Color.decode(Config.bootEmHexColor));

                            jda.getTextChannelById("883674307086151763").sendMessage(jda.getRoleById("883571005073784873").getAsMention()).queue();
                            jda.getTextChannelById("883674307086151763").sendMessage(embedBuilder.build()).queue();
                            
                            idList.add(jsonArray.get(i).getAsJsonObject().get("ID").getAsString());
                        }
                    }

                    if (NUTTDiscordBot.hour_24Format.format(new Date()).equals("20:00")) {
                        EmbedBuilder embedBuilder = BotCommand.getTodayAnnounce(BotCommand.getDataJsonaArray(document));
                        embedBuilder.setAuthor("結至現在的新生專區今天的所有公告", Config.bootEmAuthorTextLink, Config.bootEmAuthorImageLink);
                        jda.getTextChannelById("885731450048098314").sendMessage(embedBuilder.build()).queue();

                        
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
        }.runTaskTimerAsynchronously(plugin, 0, 1200);
    }
}
