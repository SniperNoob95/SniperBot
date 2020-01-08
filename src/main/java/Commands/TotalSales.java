package Commands;

import Bot.SniperBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class TotalSales {

    public static void sendTotalSales(MessageReceivedEvent event) {
        try {
            if (!Objects.requireNonNull(event.getMember()).getRoles().get(0).getName().equals("Owner")) {
                event.getChannel().sendTyping().complete();
                event.getChannel().sendMessage("You do not have permission to do this.").queue();
                return;
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            SniperBot.botLogger.logError("[TotalSales.sendTotalSales] - Failed to check member's top role.");
        }

        try {
            int count = SniperBot.databaseClient.totalSales();
            event.getChannel().sendTyping().complete();
            if (count != -1) {
                event.getChannel().sendMessage(String.format("There have been %s total sales logged in this server.", NumberFormat.getNumberInstance(Locale.US).format(count))).queue();
            } else {
                event.getChannel().sendMessage("Unable to retrieve sales count.").queue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[TotalSales.sendTotalSales] - Unable to get total sales count.");
            event.getChannel().sendTyping().complete();
            event.getChannel().sendMessage("Unable to retrieve sales count.").queue();
        }
    }
}
