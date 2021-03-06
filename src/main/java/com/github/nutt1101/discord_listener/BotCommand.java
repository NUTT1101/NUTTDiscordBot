package com.github.nutt1101.discord_listener;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.github.nutt1101.Config;
import com.github.nutt1101.NUTTDiscordBot;
import com.github.nutt1101.SSLHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.jsoup.nodes.Document;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BotCommand extends ListenerAdapter{

    /**
     * message event
     */
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] message = event.getMessage().getContentRaw().split("");
        if (!message[0].equals("$")) return;

        if (!event.isFromGuild() || event.getAuthor().isBot()) return;
        
        if (event.getMessage().getContentRaw().equalsIgnoreCase("$dayeh")) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setAuthor("點我進入大葉大學官網", Config.bootEmAuthorTextLink, Config.bootEmAuthorImageLink);
            embedBuilder.setDescription("[手機版請點我](http://www.dyu.edu.tw/)");
            embedBuilder.setColor(Color.decode(Config.bootEmHexColor));
            event.getChannel().sendMessage(embedBuilder.build()).queue();
            return;
        }

        if (event.getMessage().getContentRaw().equalsIgnoreCase("$fresh")) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setAuthor("點我進入大葉大學新生專區", "http://fresh.dyu.edu.tw/", Config.bootEmAuthorImageLink);
            embedBuilder.setDescription("[手機版請點我](http://fresh.dyu.edu.tw/)");
            embedBuilder.setColor(Color.decode(Config.bootEmHexColor));
            event.getChannel().sendMessage(embedBuilder.build()).queue();
            return;
        }

        if (event.getMessage().getContentRaw().equalsIgnoreCase("$ann")) {
            try {
                Document document = SSLHelper.getConnection(Config.crawlingWeb).get();
                JsonArray jsonArray = getDataJsonaArray(document);    
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setColor(Color.decode(Config.bootEmHexColor));
                embedBuilder.setFooter("還有" + String.valueOf(jsonArray.size() - 15) + "則消息");
                String description = "";

                for (int i=0; i < 15; i++) {
                    embedBuilder.setAuthor(document.title() + "公告", "http://fresh.dyu.edu.tw/", Config.bootEmAuthorImageLink);
                    if (jsonArray.get(i) != null) {
                        description = description + String.valueOf(i + 1) +  ". [" + jsonArray.get(i).getAsJsonObject().get("title").getAsString() + "]" + "(" + 
                        getLink(jsonArray.get(i).getAsJsonObject().get("ID").getAsString()) + ")\n\n";
                    }
                }
                embedBuilder.setDescription(description);
                event.getChannel().sendMessage(embedBuilder.build()).queue();;
                return;

            } catch (Exception e) {
                e.printStackTrace();
            }      

            return;
        }

        if (event.getMessage().getContentRaw().equalsIgnoreCase("$ann today")) {
            try {
                Document document = SSLHelper.getConnection(Config.crawlingWeb).get();
                JsonArray jsonArray = getDataJsonaArray(document);
                EmbedBuilder embedBuilder = getTodayAnnouncement(jsonArray);
                event.getChannel().sendMessage(embedBuilder.build()).queue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (event.getMessage().getContentRaw().contains("$search")) {
            if (event.getMessage().getContentRaw().contains(" ")) {
                String search = event.getMessage().getContentRaw().split(" ")[1];
                try {
                    Document document = SSLHelper.getConnection(Config.crawlingWeb).get();
                    JsonArray jsonArray = getDataJsonaArray(document);    
                    EmbedBuilder embedBuilder  = new EmbedBuilder();
                    embedBuilder.setAuthor("包含 " + search + " 的所有內容如下", Config.bootEmAuthorTextLink, Config.bootEmAuthorImageLink);
                    String description = "";

                    for (int i=0; i< jsonArray.size(); i++) {
                        if (jsonArray.get(i).getAsJsonObject().get("title").getAsString().contains(search)) {
                            description = description + "[" + jsonArray.get(i).getAsJsonObject().get("title").getAsString() + "]" + "(" + 
                                getLink(jsonArray.get(i).getAsJsonObject().get("ID").getAsString()) + ")\n\n";
                        }
                    }

                    int count = 0;
                    while (description.length() >= 1500) {
                        List<String> allSearchAnnounce = new ArrayList<>(Arrays.asList(description.split("\n\n")));
                        allSearchAnnounce.remove(allSearchAnnounce.size() - 1);
                        description = String.join("\n\n", allSearchAnnounce);
                        count++;
                    }
                    
                    if (count != 0) {
                        embedBuilder.setFooter("查詢到的內容還有 " + count + " 則");
                    }

                    description = description.equals("") ? "沒有查詢到包含 " + search + " 的內容" : description;
                    description = reorganizeDescription(description);
                    embedBuilder.setDescription(description);
                    event.getMessage().getChannel().sendMessage(embedBuilder.build()).queue();
                } catch (Exception e) {   
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * Cleansing data from announcement convert to jsonArray.
     * 
     * @param document web document
     * @return all announcement -> jsonArray
     */
    public static JsonArray getDataJsonaArray(Document document) {
        try {
            String data = document.getElementsByTag("script").get(5).toString().split("\n")[5].split("'")[1];
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(data).getAsJsonArray();
            return jsonArray;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    /**
     * Input a annoucement id and convert to url.
     * 
     * @param id announcement id.
     * @return announcement url.
     */
    public static String getLink(String id) {
        return "https://www.dyu.edu.tw/news.html?msg_ID=" + id +"&pool_ID=37&isHidden=1&goBack=1";
    }


    /**
     * Get date conform to today announcement.
     * 
     * @param jsonArray the web document data array.
     * @return EmbedBuilder
     */
    public static EmbedBuilder getTodayAnnouncement(JsonArray jsonArray) {
        Date date = new Date();
        EmbedBuilder embedBuilder = new EmbedBuilder();
        String description = "";
        embedBuilder.setAuthor("今天的公告", Config.bootEmAuthorTextLink, Config.bootEmAuthorImageLink);
        embedBuilder.setColor(Color.decode(Config.bootEmHexColor));

        for (int i=0 ; i < jsonArray.size(); i++) {
            if (jsonArray.get(i) != null) {
                String docDate = jsonArray.get(i).getAsJsonObject().get("update").getAsString().replace(" ", "");

                if (NUTTDiscordBot.yearMonthDayFormat.format(date).equals(docDate)) {
                    description = description + "[" + jsonArray.get(i).getAsJsonObject().get("title").getAsString() + "]" + "(" + 
                    getLink(jsonArray.get(i).getAsJsonObject().get("ID").getAsString()) + ")\n\n";
                }
            }
        }
        description = description.equals("") ? "今天 `" + NUTTDiscordBot.yearMonthDayFormat.format(date) + "` 沒有公告" : description;
        description =  reorganizeDescription(description);
        embedBuilder.setDescription(description);
        return embedBuilder;
    }

    /**
     * Add a number to each announcement.
     * 
     * <p>input => a...\n\nb...\n\n</p>
     * reorganize => 
     * <p>1. a...\n\n</p>
     * <p>2. b...</p>
     * 
     * @param description embedBuilder description 
     * @return sorted descrition
     */
    public static String reorganizeDescription(String description) {
        String sort[] = description.split("\n\n");
        for (int i=0; i < sort.length ; i++) {
            sort[i] = String.valueOf(i+1) + ". " + sort[i]; 
        }
        return String.join("\n\n", sort);
    }
}
