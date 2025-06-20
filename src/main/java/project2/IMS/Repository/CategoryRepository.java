package project2.IMS.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project2.IMS.Entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
} 