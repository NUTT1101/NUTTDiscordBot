package com.github.nutt1101.discord_listener;

import java.awt.Color;

import com.github.nutt1101.Config;
import com.github.nutt1101.SSLHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.jsoup.nodes.Document;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BotCommand extends ListenerAdapter{
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.isFromGuild() || event.getAuthor().isBot()) return;

        if (event.getMessage().getContentRaw().equalsIgnoreCase("hi")) {
            event.getChannel().sendMessage("Hi! 你好! " + event.getMessage().getAuthor().getAsMention()).queue();;
        }
        
        if (event.getMessage().getContentRaw().equalsIgnoreCase("dayeh")) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setAuthor("點我進入大葉大學官網", Config.bootEmAuthorTextLink, Config.bootEmAuthorImageLink);
            embedBuilder.setDescription("[手機版請點我](http://fresh.dyu.edu.tw/)");
            embedBuilder.setColor(Color.decode(Config.bootEmHexColor));
            event.getChannel().sendMessage(embedBuilder.build()).queue();
        }

        if (event.getMessage().getContentRaw().equalsIgnoreCase("fresh")) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setAuthor("點我進入大葉大學新生專區", "http://fresh.dyu.edu.tw/", Config.bootEmAuthorImageLink);
            embedBuilder.setDescription("[手機版請點我](http://fresh.dyu.edu.tw/)");
            embedBuilder.setColor(Color.decode(Config.bootEmHexColor));
            event.getChannel().sendMessage(embedBuilder.build()).queue();;
        }

        if (event.getMessage().getContentRaw().equalsIgnoreCase("fresh_ann")) {

            try {
                Document document = SSLHelper.getConnection("https://bulletin.dyu.edu.tw/index.php?goBack=1&isHidden=1&pool_ID=37").get();
                String data = document.getElementsByTag("script").get(5).toString().split("\n")[5].split("'")[1];
                JsonParser parser = new JsonParser();
                JsonArray jsonArray = parser.parse(data).getAsJsonArray();

                EmbedBuilder embedBuilder = new EmbedBuilder();
                String description = "";

                for (int i=0; i< 15; i++) {
                    embedBuilder.setAuthor(document.title() + "公告", "http://fresh.dyu.edu.tw/", Config.bootEmAuthorImageLink);

                    description = description + String.valueOf(i + 1) +  ". [" + jsonArray.get(i).getAsJsonObject().get("title").getAsString() + "]" + "(" + 
                        "https://www.dyu.edu.tw/news.html?msg_ID=" + jsonArray.get(i).getAsJsonObject().get("ID").getAsString() +"&pool_ID=37&isHidden=1&goBack=1"
                        +")\n\n";
                    
                }
                embedBuilder.setDescription(description);
                event.getChannel().sendMessage(embedBuilder.build()).queue();;

            } catch (Exception e) {
                e.printStackTrace();
            }               
            
        }
    }
    
    
}
