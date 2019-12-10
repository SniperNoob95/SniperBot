package Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.Color;
import java.util.List;

public class Staff {

    public static void sendStaff(MessageReceivedEvent event) {
        List<Member> owners = event.getGuild().getMembersWithRoles(event.getGuild().getRolesByName("Owner", false));
        List<Member> moderators = event.getGuild().getMembersWithRoles(event.getGuild().getRolesByName("Moderator", false));
        List<Member> memberApprovers = event.getGuild().getMembersWithRoles(event.getGuild().getRolesByName("Member Approver", false));
        List<Member> salesLoggers = event.getGuild().getMembersWithRoles(event.getGuild().getRolesByName("Sales Logger", false));

        EmbedBuilder staff = new EmbedBuilder();
        staff.setTitle("Spell Collectors Staff");
        staff.setDescription("Current Staff Roster");
        staff.setColor(Color.GREEN);

        StringBuilder stringBuilder = new StringBuilder();
        for (Member member : owners) {
            stringBuilder.append(String.format("%s\n", member.getEffectiveName()));
        }
        staff.addField("Owner", stringBuilder.toString(), false);

        stringBuilder.setLength(0);
        for (Member member : moderators) {
            stringBuilder.append(String.format("%s\n", member.getEffectiveName()));
        }
        staff.addField("Moderators", stringBuilder.toString(), false);

        stringBuilder.setLength(0);
        for (Member member : memberApprovers) {
            stringBuilder.append(String.format("%s\n", member.getEffectiveName()));
        }
        staff.addField("Member Approvers", stringBuilder.toString(), false);

        stringBuilder.setLength(0);
        for (Member member : salesLoggers) {
            stringBuilder.append(String.format("%s\n", member.getEffectiveName()));
        }
        staff.addField("Sales Loggers", stringBuilder.toString(), false);

        event.getChannel().sendMessage(staff.build()).queue();
        staff.clear();
    }
}
