package gg.bayes.challenge.persistence.repository;

import gg.bayes.challenge.persistence.model.CombatLogEntryEntity;
import gg.bayes.challenge.rest.model.HeroKills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface CombatLogEntryRepository extends JpaRepository<CombatLogEntryEntity, Long> {
    @Query(value = "SELECT actor as hero, count(1) as kills from dota_combat_log where entry_type=:eventType and match_id=:matchId group by actor", nativeQuery = true)
    List<Tuple> findHeroKillsByMatchId(Long matchId, String eventType);

    @Query(value = "SELECT item, entry_timestamp as time from dota_combat_log where actor=:heroName and match_id=:matchId and entry_type=:eventType", nativeQuery = true)
    List<Tuple> findHeroItemsByMatchIdAndHeroName(Long matchId, String heroName, String eventType);

    @Query(value = "SELECT ability, count(1) as casts from dota_combat_log where actor=:heroName and match_id=:matchId and entry_type=:eventType group by ability", nativeQuery = true)
    List<Tuple> findHeroSpellsByMatchIdAndHeroName(Long matchId, String heroName, String eventType);

    @Query(value = "SELECT target, count(1) as damage_instances, sum(damage) as total_damage from dota_combat_log where actor=:heroName and match_id=:matchId and entry_type=:eventType group by target", nativeQuery = true)
    List<Tuple> findHeroDamageByMatchIdAndHeroName(Long matchId, String heroName, String eventType);
}
