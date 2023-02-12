package gg.bayes.challenge.parser.factory;

import gg.bayes.challenge.parser.model.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class EventsFactory {
    final static Map<String, Function<String, AbstractEvent>> map = new HashMap<>();
    static {
        map.put("hits", DamageEvent::of);
        map.put("is killed by", KillingHeroesEvent::of);
        map.put("casts", CastingSpellsEvent::of);
        map.put("buys", BuyingItemsEvent::of);
    }

    public AbstractEvent getEvent(String log) {
        var event = map.entrySet().stream()
                .filter( entry -> log.contains(entry.getKey())).findFirst();
        return event.map(entry -> entry.getValue().apply(log)).orElse(null);
    }
}
