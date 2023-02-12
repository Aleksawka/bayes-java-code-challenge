package gg.bayes.challenge.parser.model;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;

import java.util.regex.Pattern;

public class CastingSpellsEvent extends AbstractEvent {
    String spellName;
    Integer level;

    private CastingSpellsEvent(String time, String heroName, String spellName, Integer level) {
        super();
        this.info = CommonInfo.of(time, heroName);
        this.spellName = spellName;
        this.level = level;
    }

    private final static Pattern eventPattern = Pattern.compile("\\[(\\d\\d:\\d\\d:\\d\\d\\.\\d\\d\\d)\\] npc_dota_hero_(.+) casts ability (.+) \\(lvl (\\d+)\\)");

    public static CastingSpellsEvent of(String eventLog) {
        var matcher = eventPattern.matcher(eventLog);
        if (matcher.find()) {
            var time = matcher.group(1);
            var heroName = matcher.group(2);
            var spellName = matcher.group(3);
            var level = Integer.valueOf(matcher.group(4));

            return new CastingSpellsEvent(time, heroName, spellName, level);
        }
        return null;
    }

    @Override
    CombatLogEntryEntity customUpdate(CombatLogEntryEntity entity) {
        entity.setType(CombatLogEntryEntity.Type.SPELL_CAST);
        entity.setAbility(spellName);
        entity.setAbilityLevel(level);
        return entity;
    }


}
