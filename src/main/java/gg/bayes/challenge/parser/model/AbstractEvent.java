package gg.bayes.challenge.parser.model;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;

public abstract class AbstractEvent implements LogEventAdapter {
    CommonInfo info;

    @Override
    public CombatLogEntryEntity convert() {
        var entity = info.convert();
        return customUpdate(entity);
    }

    abstract CombatLogEntryEntity customUpdate(CombatLogEntryEntity entity);
}
