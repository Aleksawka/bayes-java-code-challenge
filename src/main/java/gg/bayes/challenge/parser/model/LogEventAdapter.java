package gg.bayes.challenge.parser.model;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;

public interface LogEventAdapter {
    CombatLogEntryEntity convert();
}