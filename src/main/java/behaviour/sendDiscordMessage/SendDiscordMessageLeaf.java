package behaviour.sendDiscordMessage;

import config.ScriptConfiguration;
import framework.Leaf;
import main.Main;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.skills.SkillTracker;
import org.dreambot.api.utilities.AccountManager;
import org.dreambot.api.utilities.Logger;
import org.dreambot.api.utilities.Timer;
import utilities.DiscordWebHooks.DiscordEmbed;
import utilities.DiscordWebHooks.DiscordMessage;
import utilities.DiscordWebHooks.DiscordWebhook;
import utilities.DiscordWebHooks.embed.FieldEmbed;
import utilities.DiscordWebHooks.embed.FooterEmbed;
import utilities.DiscordWebHooks.embed.ThumbnailEmbed;
import utilities.Timing;
import utilities.WealthTracker;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SendDiscordMessageLeaf extends Leaf {
    private WealthTracker wealthTracker = new WealthTracker();
    DecimalFormat df = new DecimalFormat("#");
    DiscordEmbed embedWealth, embedExperience;
    List<Skill> skillsToTrack = new ArrayList<>(Arrays.asList(
            Skill.HITPOINTS,
            Skill.ATTACK,
            Skill.DEFENCE,
            Skill.STRENGTH,
            Skill.RANGED
    ));
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int onLoop() {
        Main.discordTimer = new Timer(ScriptConfiguration.getScriptConfiguration().getWebhookSettings().getMinutesBetweenMessages() * 60000);
        DiscordMessage message = new DiscordMessage.Builder()
                .withUsername("Hans Rune Dragon WebHook")
                .withAvatarURL("https://i.imgur.com/8HBktvH.png")
                .withContent("")
                .build();

        embedWealth = new DiscordEmbed.Builder()
                .withColor(Color.GREEN)
                .withTitle("Account "+AccountManager.getAccountNickname())
                .withThumbnail(new ThumbnailEmbed("https://i.imgur.com/oagJ2mM.png",30,30))
                .build();

        embedWealth.addFields(new FieldEmbed("Total wealth generated",
                df.format(wealthTracker.getWealthGenerated() / 1000) +
                "k (" + df.format(wealthTracker.getWealthGeneratedPerHour() / 1000) + "k per hour)",false));
        embedWealth.addFields(new FieldEmbed("Bank value",df.format(wealthTracker.getBankValue()/1000000)+"m",false));

        embedExperience = new DiscordEmbed.Builder()
                .withColor(Color.PINK)
                .withTitle("Account "+AccountManager.getAccountNickname())
                .withThumbnail(new ThumbnailEmbed("https://i.imgur.com/qfHMm7j.png",30,30))
                .build();



        long totalExp = skillsToTrack.stream()
                .mapToLong(SkillTracker::getGainedExperience)
                .sum();

        long totalExpPerHour = skillsToTrack.stream()
                .mapToLong(SkillTracker::getGainedExperiencePerHour)
                .sum();

        embedExperience.addFields(new FieldEmbed("Total experience gained", ""+df.format(totalExp/1000)+"k ("+
                df.format(totalExpPerHour/1000)+ "k per hour)", false));
        embedExperience.addFields(new FieldEmbed("Strength", ""+df.format(SkillTracker.getGainedExperience(Skill.STRENGTH)/1000)+"k",true));
        embedExperience.addFields(new FieldEmbed("Attack", ""+df.format(SkillTracker.getGainedExperience(Skill.ATTACK)/1000)+"k",true));
        embedExperience.addFields(new FieldEmbed("Defence", ""+df.format(SkillTracker.getGainedExperience(Skill.DEFENCE)/1000)+"k",true));
        embedExperience.addFields(new FieldEmbed("Hitpoints", ""+df.format(SkillTracker.getGainedExperience(Skill.HITPOINTS)/1000)+"k",true));

        message.addEmbeds(embedWealth);
        message.addEmbeds(embedExperience);

        DiscordWebhook discordWebhook = new DiscordWebhook(ScriptConfiguration.getScriptConfiguration().getWebhookSettings().getURL());
        discordWebhook.sendMessage(message);
        return Timing.loopReturn();
    }
}
