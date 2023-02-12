package gg.bayes.challenge.parser.model;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;

import java.util.regex.Pattern;

public class KillingHeroesEvent extends AbstractEvent {
    String diedHero;

    private final static Pattern eventPattern = Pattern.compile("\\[(\\d\\d:\\d\\d:\\d\\d\\.\\d\\d\\d)\\] npc_dota_hero_(.+) is killed by npc_dota_hero_(.+)");

    private KillingHeroesEvent(String time, String hero, String killedHero) {
        super();
        this.info = CommonInfo.of(time, hero);
        this.diedHero = killedHero;
    }

    public static KillingHeroesEvent of(String eventLog) {
        var matcher = eventPattern.matcher(eventLog);
        if (matcher.find()) {
            var time = matcher.group(1);
            var killed = matcher.group(2);
            var hero = matcher.group(3);

            return new KillingHeroesEvent(time, hero, killed);
        }
        return null;
    }

    @Override
    CombatLogEntryEntity customUpdate(CombatLogEntryEntity entity) {
        entity.setTarget(diedHero);
        entity.setType(CombatLogEntryEntity.Type.HERO_KILLED);
        return entity;
    }
}
