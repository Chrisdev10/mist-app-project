package be.technifutur.repo;

import be.technifutur.model.entity.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
@Component
public interface MarketRepo extends JpaRepository<Market,Long> {
    Optional<Market> findByGameRef(UUID ref);
    @Transactional
    @Modifying
    @Query("delete from Market m where m.gameRef = ?1")
    void deleteByGameRef(UUID ref);
}
