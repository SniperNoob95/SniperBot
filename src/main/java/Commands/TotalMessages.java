package Commands;

import Bot.SniperBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class TotalMessages {

    public static void sendTotalMessages(MessageReceivedEvent event) {
        try {
            if (!Objects.requireNonNull(event.getMember()).getRoles().get(0).getName().equals("Owner")) {
                event.getChannel().sendTyping().complete();
                event.getChannel().sendMessage("You do not have permission to do this.").queue();
                return;
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            SniperBot.botLogger.logError("[TotalMessages.sendTotalMessages] - Failed to check member's top role.");
        }

        try {
            int count = SniperBot.databaseClient.getTotalMessages();
            event.getChannel().sendTyping().complete();
            if (count != -1) {
                event.getChannel().sendMessage(String.format("There have been %s total messages sent in this server.", NumberFormat.getNumberInstance(Locale.US).format(count))).queue();
            } else {
                event.getChannel().sendMessage("Unable to retrieve message count.").queue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[TotalMessages.sendTotalMessages] - Unable to get total message count.");
            event.getChannel().sendTyping().complete();
            event.getChannel().sendMessage("Unable to retrieve message count.").queue();
        }
    }
}
