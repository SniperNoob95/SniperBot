package EventManager;

import Bot.SniperBot;
import Commands.Admit;
import Commands.BuyerRole;
import Commands.FFOTD;
import Commands.Help;
import Commands.Info;
import Commands.ItemSales;
import Commands.Join;
import Commands.LogSale;
import Commands.MemberCount;
import Commands.MemberMessages;
import Commands.Notify;
import Commands.Ping;
import Commands.Restricted;
import Commands.SellerRole;
import Commands.Staff;
import Commands.TotalMessages;
import Commands.TotalSales;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Objects;

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
            SniperBot.APIClient.insertMessage(event);
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert message into database.");
        }

        if (!message.getContentRaw().startsWith(SniperBot.prefix)) {
            return;
        }

        String[] args = message.getContentRaw().split("\\s+");

        if (args[0].toLowerCase().equals("!ping")) {
            try {
                SniperBot.APIClient.insertCommand(event);
            } catch (Exception e) {
                e.printStackTrace();
                SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert !ping command into database.");
            }
            Ping.sendPing(event);
        }

        if (args[0].toLowerCase().equals("!members")) {
            try {
                SniperBot.APIClient.insertCommand(event);
            } catch (Exception e) {
                e.printStackTrace();
                SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert !members command into database.");
            }
            MemberCount.sendMemberCount(event);
        }

        if (args[0].toLowerCase().equals("!buyer")) {
            try {
                SniperBot.APIClient.insertCommand(event);
            } catch (Exception e) {
                e.printStackTrace();
                SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert !buyer command into database.");
            }
            BuyerRole.applyBuyerRole(event);
        }

        if (args[0].toLowerCase().equals("!seller")) {
            try {
                SniperBot.APIClient.insertCommand(event);
            } catch (Exception e) {
                e.printStackTrace();
                SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert !seller command into database.");
            }
            SellerRole.applySellerRole(event);
        }

        if (args[0].toLowerCase().equals("!staff")) {
            try {
                SniperBot.APIClient.insertCommand(event);
            } catch (Exception e) {
                e.printStackTrace();
                SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert !staff command into database.");
            }
            Staff.sendStaff(event);
        }

        if (args[0].toLowerCase().equals("!admit")) {
            try {
                SniperBot.APIClient.insertCommand(event);
            } catch (Exception e) {
                e.printStackTrace();
                SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert !admit command into database.");
            }
            Admit.admitMember(event);
        }

        if (args[0].toLowerCase().equals("!join")) {
            try {
                SniperBot.APIClient.insertCommand(event);
            } catch (Exception e) {
                e.printStackTrace();
                SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert !join command into database.");
            }
            Join.requestJoin(event);
        }

        if (args[0].toLowerCase().equals("!info")) {
            try {
                SniperBot.APIClient.insertCommand(event);
            } catch (Exception e) {
                e.printStackTrace();
                SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert !info command into database.");
            }
            Info.sendInfo(event);
        }

        if (args[0].toLowerCase().equals("!messages")) {
            try {
                SniperBot.APIClient.insertCommand(event);
            } catch (Exception e) {
                e.printStackTrace();
                SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert !messages command into database.");
            }
            MemberMessages.sendMemberMessages(event);
        }

        if (args[0].toLowerCase().equals("!messagetotal")) {
            try {
                SniperBot.APIClient.insertCommand(event);
            } catch (Exception e) {
                e.printStackTrace();
                SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert !messageTotal command into database.");
            }
            TotalMessages.sendTotalMessages(event);
        }

        if (args[0].toLowerCase().equals("!help")) {
            try {
                SniperBot.APIClient.insertCommand(event);
            } catch (Exception e) {
                e.printStackTrace();
                SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert !help command into database.");
            }
            Help.sendHelp(event);
        }

        if (args[0].toLowerCase().equals("!ffotd")) {
            try {
                SniperBot.APIClient.insertCommand(event);
            } catch (Exception e) {
                e.printStackTrace();
                SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert !FFOTD command into database.");
            }
            FFOTD.changeFFOTD(event);
        }

        if (args[0].toLowerCase().equals("!notify")) {
            try {
                SniperBot.APIClient.insertCommand(event);
            } catch (Exception e) {
                e.printStackTrace();
                SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert !notify command into database.");
            }
            Notify.sendNotification(event);
        }

        if (args[0].toLowerCase().equals("!salestotal")) {
            try {
                SniperBot.APIClient.insertCommand(event);
            } catch (Exception e) {
                e.printStackTrace();
                SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert !salesTotal command into database.");
            }
            TotalSales.sendTotalSales(event);
        }

        if (args[0].toLowerCase().equals("!itemsales")) {
            try {
                SniperBot.APIClient.insertCommand(event);
            } catch (Exception e) {
                e.printStackTrace();
                SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert !itemSales command into database.");
            }
            ItemSales.sendItemSales(event);
        }

        if (args[0].toLowerCase().equals("!logsale")) {
            try {
                SniperBot.APIClient.insertCommand(event);
            } catch (Exception e) {
                e.printStackTrace();
                SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert !logSale command into database.");
            }
            LogSale.logSale(event);
        }

        if (args[0].toLowerCase().equals("!restricted")) {
            try {
                SniperBot.APIClient.insertCommand(event);
            } catch (Exception e) {
                e.printStackTrace();
                SniperBot.botLogger.logError("[EventManager.onMessageReceived] - Failed to insert !restricted command into database.");
            }
            Restricted.sendRestrictedInfo(event);
        }
    }

    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        Role restrictedRole = null;
        for (Role role : event.getGuild().getRoles()) {
            if (role.getName().equals("Restricted")) {
                restrictedRole = role;
            }
        }

        try {
            event.getGuild().addRoleToMember(event.getMember(), Objects.requireNonNull(restrictedRole)).complete();
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[EventManager.onGuildMemberJoin] - Failed to restricted role to new member.");
        }

        TextChannel restrictedChannel = null;
        TextChannel rulesChannel = null;
        for (TextChannel textChannel : event.getGuild().getTextChannels()) {
            if (textChannel.getName().equals("restricted")) {
                restrictedChannel = textChannel;
            }

            if (textChannel.getName().equals("rules")) {
                rulesChannel = textChannel;
            }
        }

        try {
            String joinMessage = String.format("Hello %s! Welcome to the Spell Collectors Discord. To get started, please link your Steam account to your Discord account. After that, please read the member requirements as well as the rules in %s." +
                    "\nOnce you have done so, type `!join` and one of our Member Approvers will assist you." +
                    "\nYou can also type `!restricted` to see the features available to you.", event.getMember().getAsMention(), Objects.requireNonNull(rulesChannel).getAsMention());
            Objects.requireNonNull(restrictedChannel).sendTyping().complete();
            restrictedChannel.sendMessage(joinMessage).queue();
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[EventManager.onGuildMemberJoin] - Failed to send welcome message.");
        }
    }

    @Override
    public void onGuildMemberLeave(@Nonnull GuildMemberLeaveEvent event) {
        SniperBot.botLogger.logMessage(String.format("Member %s left the server (%s).", event.getMember().getEffectiveName(), event.getMember().getRoles().get(0).getName()));
    }
}
