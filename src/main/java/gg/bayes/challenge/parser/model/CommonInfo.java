package gg.bayes.challenge.parser.model;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class CommonInfo implements LogEventAdapter {
    Long timestamp;
    String hero;

    private CommonInfo() {

    }

    static CommonInfo of(String timestamp, String hero) {
        var time = TimeUnit.NANOSECONDS.toMillis(LocalTime.parse(timestamp).toNanoOfDay());
        var commonInfo = new CommonInfo();
        commonInfo.timestamp = time;
        commonInfo.hero = hero;
        return commonInfo;
    }

    @Override
    public CombatLogEntryEntity convert() {
        var entity = new CombatLogEntryEntity();
        entity.setTimestamp(timestamp);
        entity.setActor(hero);
        return entity;
    }
}
