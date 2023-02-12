package gg.bayes.challenge.parser.model;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;

import java.util.regex.Pattern;

public class BuyingItemsEvent extends AbstractEvent {
    String item;

    private final static Pattern eventPattern = Pattern.compile("\\[(\\d\\d:\\d\\d:\\d\\d\\.\\d\\d\\d)\\] npc_dota_hero_(.+) buys item item_(.+)");

    private BuyingItemsEvent(String time, String heroName, String item) {
        super();
        this.info = CommonInfo.of(time, heroName);
        this.item = item;
    }

    public static BuyingItemsEvent of(String eventLog) {
        var matcher = eventPattern.matcher(eventLog);
        if (matcher.find()) {
            var time =matcher.group(1);
            var heroName = matcher.group(2);
            var item = matcher.group(3);

            return new BuyingItemsEvent(time, heroName, item);
        }
        return null;
    }

    @Override
    CombatLogEntryEntity customUpdate(CombatLogEntryEntity entity) {
        entity.setItem(item);
        entity.setType(CombatLogEntryEntity.Type.ITEM_PURCHASED);
        return entity;
    }
}
