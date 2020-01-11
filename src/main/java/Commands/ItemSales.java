package Commands;

import Bot.SniperBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Objects;

public class ItemSales {

    public static void sendItemSales(MessageReceivedEvent event) {
        try {
            if (!event.getChannel().getName().equals("price-checking")) {
                event.getChannel().sendTyping().complete();
                event.getChannel().sendMessage(String.format("Sales can only be queried in the %s channel",
                        event.getGuild().getTextChannelsByName("price-checking", false).get(0).getAsMention())).queue();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[ItemSales.sendItemSales] - Failed to check channel.");
        }

        String item;

        if (event.getMessage().getContentRaw().split("\\s+", 2).length == 2) {
            item = event.getMessage().getContentRaw().split("\\s+", 2)[1];
        } else {
            event.getChannel().sendTyping().complete();
            event.getChannel().sendMessage("Please enter one item name to search for.").queue();
            return;
        }

        try {
            String sales = SniperBot.databaseClient.getItemSales(item);
            event.getChannel().sendTyping().complete();
            event.getChannel().sendMessage(Objects.requireNonNullElse(sales, String.format("No sales found for `%s`.", item))).queue();
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[ItemSales.sendItemSales] - Unable to get item sales.");
            event.getChannel().sendTyping().complete();
            event.getChannel().sendMessage("Unable to get item sales.").queue();
        }
    }
}
