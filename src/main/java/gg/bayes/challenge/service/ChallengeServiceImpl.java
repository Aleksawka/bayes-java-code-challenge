package gg.bayes.challenge.service;

import gg.bayes.challenge.parser.ParserInterface;
import gg.bayes.challenge.parser.model.LogEventAdapter;
import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import gg.bayes.challenge.persistence.model.MatchEntity;
import gg.bayes.challenge.persistence.repository.CombatLogEntryRepository;
import gg.bayes.challenge.persistence.repository.MatchRepository;
import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItem;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log
public class ChallengeServiceImpl implements ChallengeService{

    private final ParserInterface parser;

    private final MatchRepository matchRepository;

    private final CombatLogEntryRepository logEntryRepository;

    @Autowired
    public ChallengeServiceImpl(ParserInterface parser, MatchRepository matchRepository,
                                CombatLogEntryRepository logEntryRepository) {
        this.parser = parser;
        this.matchRepository = matchRepository;
        this.logEntryRepository = logEntryRepository;
    }


    @Override
    @Transactional
    public Long saveMatchLog(String source) {
        var logEvents = parser.parseEvents(source);
        var match = new MatchEntity();
        logEvents.stream().map(LogEventAdapter::convert).forEach(match::addLog);
        matchRepository.save(match);
        return match.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<HeroKills> getMatch(Long id) {
        var queryResult = logEntryRepository.findHeroKillsByMatchId(id,
                CombatLogEntryEntity.Type.HERO_KILLED.toString());

        return queryResult.stream()
                .map(tuple ->
                        new HeroKills(tuple.get("hero",  String.class),
                        tuple.get("kills", BigInteger.class).intValue()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<HeroItem> getHeroItems(Long matchId, String heroName) {
        var queryResult = logEntryRepository.findHeroItemsByMatchIdAndHeroName(matchId,
                heroName, CombatLogEntryEntity.Type.ITEM_PURCHASED.toString());

        return queryResult.stream()
                .map(tuple ->
                        new HeroItem(tuple.get("item",  String.class),
                                tuple.get("time", BigInteger.class).longValue()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<HeroSpells> getHeroSpells(Long matchId, String heroName) {
        var queryResult = logEntryRepository.findHeroSpellsByMatchIdAndHeroName(matchId,
                heroName, CombatLogEntryEntity.Type.SPELL_CAST.toString());

        return queryResult.stream()
                .map(tuple ->
                        new HeroSpells(tuple.get("ability",  String.class),
                                tuple.get("casts", BigInteger.class).intValue()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<HeroDamage> getHeroDamages(Long matchId, String heroName) {
        var queryResult = logEntryRepository.findHeroDamageByMatchIdAndHeroName(matchId,
                heroName, CombatLogEntryEntity.Type.DAMAGE_DONE.toString());

        return queryResult.stream()
                .map(tuple ->
                        new HeroDamage(tuple.get("target",  String.class),
                                tuple.get("damage_instances", BigInteger.class).intValue(),
                        tuple.get("total_damage", BigInteger.class).intValue())
                )
                .collect(Collectors.toList());
    }
}
