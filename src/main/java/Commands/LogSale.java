package Commands;

import Bot.SniperBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.Color;
import java.util.Objects;

public class LogSale {

    public static void logSale(MessageReceivedEvent event) {
        String formatMessage = "Please adhere to the correct sales logging syntax:" +
                "\n```!logSale | Member(mention) | Item | Quality | Spell1 | Spell2/None | Price```" +
                "\nExample: ```!logSale | @Sniper Noob | Crone's Dome | Haunted | Spectral | TSFP | 10.5```";

        try {
            if (!event.getChannel().getName().equals("sales-logging")) {
                event.getChannel().sendTyping().complete();
                event.getChannel().sendMessage(String.format("Sales can only be logged in the %s channel",
                        event.getGuild().getTextChannelsByName("sales-logging", false).get(0).getAsMention())).queue();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[LogSale.logSale] - Failed to check channel.");
        }

        if (event.getMessage().getMentionedMembers().size() != 1) {
            event.getChannel().sendTyping().complete();
            event.getChannel().sendMessage(formatMessage).queue();
            return;
        }

        Role salesLogger = null;
        for (Role role : event.getGuild().getRoles()) {
            if (role.getName().equals("Sales Logger")) {
                salesLogger = role;
            }
        }

        try {
            if (!Objects.requireNonNull(event.getMember()).getRoles().contains(salesLogger)) {
                event.getChannel().sendTyping().complete();
                event.getChannel().sendMessage("You do not have permission to do this.").queue();
                return;
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            SniperBot.botLogger.logError("[LogSale.logSale] - Failed to check member's roles for the Sales Logger role.");
        }

        // enteredID, enteredName, sellerID, sellerName, item, itemQuality, spell1, spell2, price
        String[] saleAttributes = new String[9];

        String[] messageParts = event.getMessage().getContentRaw().split(" \\| ");

        if (messageParts.length != 7) {
            event.getChannel().sendTyping().complete();
            event.getChannel().sendMessage(formatMessage).queue();
            return;
        }

        try {
            saleAttributes[0] = Objects.requireNonNull(event.getMember()).getId();
            saleAttributes[1] = event.getMember().getEffectiveName();
            saleAttributes[2] = event.getMessage().getMentionedMembers().get(0).getId();
            saleAttributes[3] = event.getMessage().getMentionedMembers().get(0).getEffectiveName();
            saleAttributes[4] = messageParts[2];
            saleAttributes[5] = messageParts[3];
            saleAttributes[6] = messageParts[4];
            saleAttributes[7] = messageParts[5];
            saleAttributes[8] = messageParts[6];
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[LogSale.logSale] - Failed to get sale attributes.");
        }

        try {
            SniperBot.APIClient.insertSale(saleAttributes);
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[LogSale.logSale] - Failed to insert sale.");
        }

        EmbedBuilder sale = new EmbedBuilder();
        sale.setTitle("Sale Inserted");
        sale.setColor(Color.GREEN);

        sale.addField("Item", saleAttributes[4], false);
        sale.addField("Quality", saleAttributes[5], false);
        sale.addField("Spell1", saleAttributes[6], false);
        sale.addField("Spell2", saleAttributes[7], false);
        sale.addField("Price", saleAttributes[8], false);

        event.getChannel().sendTyping().complete();
        event.getChannel().sendMessage(sale.build()).queue();
    }
}
