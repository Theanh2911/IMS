package project2.IMS.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project2.IMS.Entity.StocktakingSession;
import project2.IMS.Entity.SessionStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface StocktakingSessionRepository extends JpaRepository<StocktakingSession, Long> {
    List<StocktakingSession> findByCreatedByIdOrderBySessionDateDesc(Integer userId);

    @Modifying
    @Query("UPDATE StocktakingSession s SET s.status = 'COMPLETED' WHERE s.createdBy.id = :userId AND s.status = 'ACTIVE'")
    void updateActiveSessionsToCompleted(@Param("userId") Integer userId);

    Optional<StocktakingSession> findByCreatedByIdAndStatus(Integer id, SessionStatus sessionStatus);
}
