package EventManager;

import Bot.SniperBot;
import Commands.Admit;
import Commands.BuyerRole;
import Commands.FFOTD;
import Commands.Help;
import Commands.Info;
import Commands.ItemSales;
import Commands.Join;
import Commands.MemberCount;
import Commands.MemberMessages;
import Commands.Notify;
import Commands.Ping;
import Commands.SellerRole;
import Commands.Staff;
import Commands.TotalMessages;
import Commands.TotalSales;
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
            SniperBot.databaseClient.insertMessage(event);
        } catch (SQLException e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert message into database.");
        }

        if (!message.getContentRaw().startsWith(SniperBot.prefix)) {
            return;
        }

        String[] args = message.getContentRaw().split("\\s+");

        if (args[0].equals("!ping")) {
            try {
                SniperBot.databaseClient.insertCommand(event);
            } catch (SQLException e) {
                e.printStackTrace();
                SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert !ping command into database.");
            }
            Ping.sendPing(event);
        }

        if (args[0].equals("!members")) {
            try {
                SniperBot.databaseClient.insertCommand(event);
            } catch (SQLException e) {
                e.printStackTrace();
                SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert !members command into database.");
            }
            MemberCount.sendMemberCount(event);
        }

        if (args[0].equals("!buyer")) {
            try {
                SniperBot.databaseClient.insertCommand(event);
            } catch (SQLException e) {
                e.printStackTrace();
                SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert !buyer command into database.");
            }
            BuyerRole.applyBuyerRole(event);
        }

        if (args[0].equals("!seller")) {
            try {
                SniperBot.databaseClient.insertCommand(event);
            } catch (SQLException e) {
                e.printStackTrace();
                SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert !seller command into database.");
            }
            SellerRole.applySellerRole(event);
        }

        if (args[0].equals("!staff")) {
            try {
                SniperBot.databaseClient.insertCommand(event);
            } catch (SQLException e) {
                e.printStackTrace();
                SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert !staff command into database.");
            }
            Staff.sendStaff(event);
        }

        if (args[0].equals("!admit")) {
            try {
                SniperBot.databaseClient.insertCommand(event);
            } catch (SQLException e) {
                e.printStackTrace();
                SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert !admit command into database.");
            }
            Admit.admitMember(event);
        }

        if (args[0].equals("!join")) {
            try {
                SniperBot.databaseClient.insertCommand(event);
            } catch (SQLException e) {
                e.printStackTrace();
                SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert !join command into database.");
            }
            Join.requestJoin(event);
        }

        if (args[0].equals("!info")) {
            try {
                SniperBot.databaseClient.insertCommand(event);
            } catch (SQLException e) {
                e.printStackTrace();
                SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert !info command into database.");
            }
            Info.sendInfo(event);
        }

        if (args[0].equals("!messages")) {
            try {
                SniperBot.databaseClient.insertCommand(event);
            } catch (SQLException e) {
                e.printStackTrace();
                SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert !messages command into database.");
            }
            MemberMessages.sendMemberMessages(event);
        }

        if (args[0].equals("!messageTotal")) {
            try {
                SniperBot.databaseClient.insertCommand(event);
            } catch (SQLException e) {
                e.printStackTrace();
                SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert !messageTotal command into database.");
            }
            TotalMessages.sendTotalMessages(event);
        }

        if (args[0].equals("!help")) {
            try {
                SniperBot.databaseClient.insertCommand(event);
            } catch (SQLException e) {
                e.printStackTrace();
                SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert !help command into database.");
            }
            Help.sendHelp(event);
        }

        if (args[0].equals("!FFOTD")) {
            try {
                SniperBot.databaseClient.insertCommand(event);
            } catch (SQLException e) {
                e.printStackTrace();
                SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert !FFOTD command into database.");
            }
            FFOTD.changeFFOTD(event);
        }

        if (args[0].equals("!notify")) {
            try {
                SniperBot.databaseClient.insertCommand(event);
            } catch (SQLException e) {
                e.printStackTrace();
                SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert !notify command into database.");
            }
            Notify.sendNotification(event);
        }

        if (args[0].equals("!salesTotal")) {
            try {
                SniperBot.databaseClient.insertCommand(event);
            } catch (SQLException e) {
                e.printStackTrace();
                SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert !salesTotal command into database.");
            }
            TotalSales.sendTotalSales(event);
        }

        if (args[0].equals("!itemSales")) {
            try {
                SniperBot.databaseClient.insertCommand(event);
            } catch (SQLException e) {
                e.printStackTrace();
                SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert !itemSales command into database.");
            }
            ItemSales.sendItemSales(event);
        }
    }
}
