package Commands;

import Bot.SniperBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.Color;
import java.util.Objects;

public class Info {

    public static void sendInfo(MessageReceivedEvent event) {
        EmbedBuilder info = new EmbedBuilder();
        info.setTitle("Information");
        info.setDescription("Information about the bot and server.");
        info.setColor(Color.GREEN);

        info.addField("Server", String.format("Members: %d", event.getGuild().getMembers().size()), false);

        info.addField("Bot", "Created with JDA: https://github.com/DV8FromTheWorld/JDA", false);

        try {
            String ownerUrl = Objects.requireNonNull(event.getGuild().getMemberById(181588597558738954L)).getUser().getAvatarUrl();
            info.setFooter("Created by Sniper Noob", ownerUrl);
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            SniperBot.botLogger.logError("[Info.sendInfo] - Unable to retrieve logo for footer.");
        }

        event.getChannel().sendMessage(info.build()).queue();
        info.clear();
    }
}
