package project2.IMS.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project2.IMS.Entity.StocktakingProduct;

import java.util.List;
import java.util.Optional;

@Repository
public interface StocktakingProductRepository extends JpaRepository<StocktakingProduct, Long> {
    List<StocktakingProduct> findBySessionId(Long sessionId);

    Optional<StocktakingProduct> findBySessionIdAndProductId(Long sessionId, Long productId);
}