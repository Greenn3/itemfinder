package com.example.itemfinder;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LostItemRepository extends JpaRepository<LostItem, Integer> {
    // Custom query to find by item name
    List<LostItem> findByName(String name);

    List<LostItem> findByFinderId(Integer userId);

    List<LostItem> findByLoserId(Integer userId);
}
