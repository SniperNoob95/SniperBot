package EventManager;

import Bot.SniperBot;
import Commands.Admit;
import Commands.BuyerRole;
import Commands.MemberCount;
import Commands.Ping;
import Commands.SellerRole;
import Commands.Staff;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class EventManager extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Message message = event.getMessage();

        if (message.isFromType(ChannelType.PRIVATE)) {
            return;
        }

        if (message.getAuthor().isBot()) {
            return;
        }

        try {
            System.out.println(String.format("Storing message: %s", event.getMessage().getContentRaw()));
            SniperBot.databaseWriter.insertMessage(event);
        } catch (SQLException e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert message into database.");
        }

        if (!message.getContentRaw().startsWith(SniperBot.prefix)) {
            return;
        }

        String[] args = message.getContentRaw().split("\\s+");

        if (args[0].equals("!ping")) {
            event.getChannel().sendTyping().complete();
            Ping.sendPing(event);
        }

        if (args[0].equals("!members")) {
            event.getChannel().sendTyping().complete();
            MemberCount.sendMemberCount(event);
        }

        if (args[0].equals("!buyer")) {
            event.getChannel().sendTyping().complete();
            BuyerRole.applyBuyerRole(event);
        }

        if (args[0].equals("!seller")) {
            event.getChannel().sendTyping().complete();
            SellerRole.applySellerRole(event);
        }

        if (args[0].equals("!staff")) {
            event.getChannel().sendTyping().complete();
            Staff.sendStaff(event);
        }

        if (args[0].equals("!admit")) {
            event.getChannel().sendTyping().complete();
            Admit.admitMember(event);
        }
    }
}
