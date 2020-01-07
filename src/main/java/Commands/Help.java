package Commands;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class Help {

    public static void sendHelp(MessageReceivedEvent event) {
        List<TextChannel> channelList = event.getGuild().getTextChannelsByName("bot-commands", false);

        if (channelList.size() != 1) {
            event.getChannel().sendTyping().complete();
            event.getChannel().sendMessage("Zero or more than one bot-commands channel exists, please ping Sniper Noob to add one or remove duplicates.").queue();
        } else {
            event.getChannel().sendTyping().complete();
            event.getChannel().sendMessage(String.format("Please see %s for information on bot commands.", channelList.get(0).getAsMention())).queue();
        }
    }
}
