package gg.bayes.challenge.parser;

import gg.bayes.challenge.parser.model.AbstractEvent;

import java.util.List;

public interface ParserInterface {
    List<AbstractEvent> parseEvents(String source);
}
