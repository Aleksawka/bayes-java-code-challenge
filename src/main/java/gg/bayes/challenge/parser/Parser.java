package gg.bayes.challenge.parser;

import gg.bayes.challenge.parser.factory.EventsFactory;
import gg.bayes.challenge.parser.model.AbstractEvent;
import gg.bayes.challenge.parser.model.LogEventAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class Parser implements ParserInterface {

    private final EventsFactory factory;

    @Autowired
    public Parser(EventsFactory factory) {
        this.factory = factory;
    }

    public List<AbstractEvent> parseEvents(String source) {
        return source.lines().map(factory::getEvent).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
