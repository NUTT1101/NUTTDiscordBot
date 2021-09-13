package com.github.nutt1101;

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
    private String id = "";
    private EmbedBuilder embedBuilder = new EmbedBuilder();

    public void run() {
        new BukkitRunnable(){
            
            @Override
            public void run() {
            
                try {
                    Document document = SSLHelper.getConnection("https://bulletin.dyu.edu.tw/index.php?goBack=1&isHidden=1&pool_ID=37").get();
                    JsonArray jsonArray = BotCommand.getDataJsonaArray(document);

                    if (id.equals("")) {
                        id = jsonArray.get(0).getAsJsonObject().get("ID").getAsString();
                    } 
                    
                    if (!jsonArray.get(0).getAsJsonObject().get("ID").getAsString().equals(id)) {
                        String title = jsonArray.get(0).getAsJsonObject().get("title").getAsString();
                        
                        embedBuilder.setAuthor("新生專區有最新消息!", 
                            BotCommand.getLink(jsonArray.get(0).getAsJsonObject().get("ID").getAsString()) , 
                            Config.bootEmAuthorImageLink);
                        
                        embedBuilder.setDescription("[" + title + "]" + "("+ BotCommand.getLink(jsonArray.get(0).getAsJsonObject().get("ID").getAsString()) +")");
                        jda.getTextChannelById("883674307086151763").sendMessage(jda.getRoleById("883571005073784873").getAsMention()).queue();
                        jda.getTextChannelById("883674307086151763").sendMessage(embedBuilder.build()).queue();
                        id = jsonArray.get(0).getAsJsonObject().get("ID").getAsString();
                    }
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
        }.runTaskTimerAsynchronously(plugin, 0, 1200);
    }
}
