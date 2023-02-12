package gg.bayes.challenge.service;

import gg.bayes.challenge.rest.model.HeroDamage;
import gg.bayes.challenge.rest.model.HeroItem;
import gg.bayes.challenge.rest.model.HeroKills;
import gg.bayes.challenge.rest.model.HeroSpells;

import java.util.List;

public interface ChallengeService {
    Long saveMatchLog(String source);
    List<HeroKills> getMatch(Long id);
    List<HeroItem> getHeroItems(Long matchId, String heroName);
    List<HeroSpells> getHeroSpells(Long matchId, String heroName);
    List<HeroDamage> getHeroDamages(Long matchId, String heroName);
}
