package net.avaxplay.itemfinder.services;


import net.avaxplay.itemfinder.repositories.CombinedItemsRepository;
import net.avaxplay.itemfinder.schema.CombinedItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CombinedItemsService {
    private final CombinedItemsRepository combinedItemsRepository;

    public CombinedItemsService(CombinedItemsRepository combinedItemsRepository) {
        this.combinedItemsRepository = combinedItemsRepository;
    }

    public List<CombinedItem> findAll() {
        return combinedItemsRepository.findAll();
    }

    public List<CombinedItem> findAllFiltered() {
        return combinedItemsRepository.findAllFiltered();
    }

    public List<CombinedItem> findAllDateDesc() {
        return combinedItemsRepository.findAllDateDesc();
    }

    public List<CombinedItem> findAllFilteredDateDesc() {
        return combinedItemsRepository.findAllFilteredDateDesc();
    }

    public List<CombinedItem> searchAndSort(String searchPhrase, String orderBy, boolean descending, boolean filtered) {
        //System.out.printf("searchPhrase = [%s], sortBy = [%s], descending = [%b], filtered = [%b]%n", searchPhrase, orderBy, descending, filtered);
        String orderByColumn = switch (orderBy) {
            case "created" -> "CreationDate";
            case "name" -> "ItemName";
            default -> "EventDate";
        } ;
        return combinedItemsRepository.searchAndSort(searchPhrase, orderByColumn, descending, filtered);
    }
}
