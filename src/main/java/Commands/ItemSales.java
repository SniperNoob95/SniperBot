package Commands;

import Bot.SniperBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Objects;

public class ItemSales {

    public static void sendItemSales(MessageReceivedEvent event) {
        try {
            if (Objects.requireNonNull(event.getMember()).getRoles().get(0).getName().equals("Restricted")) {
                event.getChannel().sendTyping().complete();
                event.getChannel().sendMessage("You do not have permission to do this.").queue();
                return;
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            SniperBot.botLogger.logError("[MemberMessages.sendMemberMessages] - Failed to check member's top role.");
        }

        String item;

        if (event.getMessage().getContentRaw().split("\\s+").length == 2) {
            item = event.getMessage().getContentRaw().split("\\s+")[1];
        } else {
            event.getChannel().sendTyping().complete();
            event.getChannel().sendMessage("Please enter one item name to search for.").queue();
            return;
        }

        try {
            String sales = SniperBot.databaseClient.itemSales(item);
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
