package Commands;

import Bot.SniperBot;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Objects;

public class BuyerRole {

    public static void applyBuyerRole(MessageReceivedEvent event) {
        Role buyerRole = event.getGuild().getRolesByName("Buyer", false).get(0);

        Member member = event.getMember();

        try {
            if (Objects.requireNonNull(member).getRoles().get(0).getName().equals("Restricted")) {
                event.getChannel().sendMessage("You do not have permission to add the Buyer role.").queue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[BuyerRole.applyBuyerRole] - Failed to get member's top role.");
        }

        try {
            for (Role role : Objects.requireNonNull(member).getRoles()) {
                if (role.getName().equals("Buyer")) {
                    event.getGuild().removeRoleFromMember(member, buyerRole).queue();
                    event.getChannel().sendTyping().complete();
                    event.getChannel().sendMessage(String.format("Removed Buyer role from %s.", member.getAsMention())).queue();
                    return;
                }
            }

            event.getGuild().addRoleToMember(member, buyerRole).queue();
            event.getChannel().sendTyping().complete();
            event.getChannel().sendMessage(String.format("Added Buyer role to %s.", member.getAsMention())).queue();
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[BuyerRole.applyBuyerRole] - Failed to apply buyer role.");
        }
    }
}
