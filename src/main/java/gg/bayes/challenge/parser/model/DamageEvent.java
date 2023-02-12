package gg.bayes.challenge.parser.model;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;

import java.util.regex.Pattern;

public class DamageEvent extends AbstractEvent {
    String targetHero;
    Integer damage;

    private final static Pattern eventPattern = Pattern.compile("\\[(\\d\\d:\\d\\d:\\d\\d\\.\\d\\d\\d)\\] npc_dota_hero_(.+) hits npc_dota_hero_(.+) with (.+) for (\\d+) damage");

    private DamageEvent(String time, String hero, String target, Integer damage) {
        super();
        this.info = CommonInfo.of(time, hero);
        this.targetHero = target;
        this.damage = damage;
    }

    public static DamageEvent of(String eventLog) {
        var matcher = eventPattern.matcher(eventLog);
        if (matcher.find()) {
            var time = matcher.group(1);
            var hero = matcher.group(2);
            var target = matcher.group(3);
            var damage = Integer.valueOf(matcher.group(5));

            return new DamageEvent(time, hero, target, damage);
        }

        return null;
    }

    @Override
    CombatLogEntryEntity customUpdate(CombatLogEntryEntity entity) {
        entity.setDamage(damage);
        entity.setTarget(targetHero);
        entity.setType(CombatLogEntryEntity.Type.DAMAGE_DONE);
        return entity;
    }
}
