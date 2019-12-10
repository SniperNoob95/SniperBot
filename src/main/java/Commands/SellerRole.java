package Commands;

import Bot.SniperBot;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Objects;

public class SellerRole {

    public static void applySellerRole(MessageReceivedEvent event) {
        Role sellerRole = event.getGuild().getRolesByName("Seller", false).get(0);

        Member member = event.getMember();

        try {
            if (Objects.requireNonNull(member).getRoles().get(0).getName().equals("Restricted")) {
                event.getChannel().sendMessage("You do not have permission to add the Seller role.").queue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[SellerRole.applySellerRole] - Failed to get member's top role.");
        }

        try {
            for (Role role : Objects.requireNonNull(member).getRoles()) {
                if (role.getName().equals("Seller")) {
                    event.getGuild().removeRoleFromMember(member, sellerRole).queue();
                    event.getChannel().sendTyping().complete();
                    event.getChannel().sendMessage(String.format("Removed Seller role from %s.", member.getAsMention())).queue();
                    return;
                } else {
                    event.getGuild().addRoleToMember(member, sellerRole).queue();
                    event.getChannel().sendTyping().complete();
                    event.getChannel().sendMessage(String.format("Added Seller role to %s.", member.getAsMention())).queue();
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            SniperBot.botLogger.logError("[SellerRole.applySellerRole] - Failed to apply seller role.");
        }
    }
}
