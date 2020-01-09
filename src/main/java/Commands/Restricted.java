package Commands;

import Bot.SniperBot;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Objects;

public class Restricted {

    public static void sendRestrictedInfo(MessageReceivedEvent event) {
        TextChannel helpChannel = null;
        TextChannel salesLoggingChannel = null;
        TextChannel priceCheckingChannel = null;
        TextChannel botCommandsChannel = null;
        TextChannel rulesChannel = null;

        for (TextChannel textChannel : event.getGuild().getTextChannels()) {
            if (textChannel.getName().equals("help")) {
                helpChannel = textChannel;
            }

            if (textChannel.getName().equals("sales-logging")) {
                salesLoggingChannel = textChannel;
            }

            if (textChannel.getName().equals("price-checking")) {
                priceCheckingChannel = textChannel;
            }

            if (textChannel.getName().equals("bot-commands")) {
                botCommandsChannel = textChannel;
            }

            if (textChannel.getName().equals("rules")) {
                rulesChannel = textChannel;
            }
        }

        try {
            String restrictedInfo = String.format("Restricted users have access to the following areas:" +
                            "\n%s can be used to get help for new collectors. Please do NOT randomly ping people, be patient." +
                            "\n%s can be used to log sales with the form pinned there." +
                            "\n%s can be used to query logged sales." +
                            "\nPlease see %s for all info on bot commands." +
                            "\nPlease see %s for the rules.",
                    Objects.requireNonNull(helpChannel).getAsMention(), Objects.requireNonNull(salesLoggingChannel).getAsMention(), Objects.requireNonNull(priceCheckingChannel).getAsMention(), Objects.requireNonNull(botCommandsChannel).getAsMention(), Objects.requireNonNull(rulesChannel).getAsMention());

            event.getChannel().sendTyping().complete();
            event.getChannel().sendMessage(restrictedInfo).queue();
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[Restricted.sendRestrictedInfo] - Failed to send restricted info.");
        }
    }
}
