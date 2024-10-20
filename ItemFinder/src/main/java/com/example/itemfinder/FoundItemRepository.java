package com.example.itemfinder;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoundItemRepository extends JpaRepository<FoundItem, Integer> {
    // Custom query to find by item name
    List<FoundItem> findByName(String name);
}
