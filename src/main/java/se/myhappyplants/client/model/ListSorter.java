package se.myhappyplants.client.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import se.myhappyplants.shared.Plant;

import java.util.ArrayList;

/**
 * Class that sorts lists according to different options
 * Created by: Christopher O'Driscoll
 * Updated by:
 */
public class ListSorter {

    private static ArrayList<Plant> listToBeSorted;
    private static ArrayList<Plant> sortedList;

    /**
     * Creates a list of sorting options for search results
     * @return
     */
    public static ObservableList<SortingOption> sortOptionsSearch() {
        ObservableList<SortingOption> sortOptions = FXCollections.observableArrayList();
        for (SortingOption option: SortingOption.values()) {
            if(option!=SortingOption.nickname && option!=SortingOption.waterNeed) //always null on results
                sortOptions.add(option);
        }
        return sortOptions;
    }

    /**
     * Creates a list of all sorting options for a user's library
     * @return
     */
    public static ObservableList<SortingOption> sortOptionsLibrary() {
        ObservableList<SortingOption> sortOptions = FXCollections.observableArrayList();
        for (SortingOption option: SortingOption.values()) {
            if(option!=SortingOption.commonName && option!=SortingOption.scientificName) //always null on results
            sortOptions.add(option);
        }
        return sortOptions;
    }
    /**
     * calls a different sorting technique based on sorting option selected
     * @param sortOption
     * @param plantList
     * @return
     */
    public static ArrayList<Plant> sort(SortingOption sortOption, ArrayList<Plant> plantList) {
        listToBeSorted = plantList;
        switch (sortOption) {
            case nickname:
                sortedList = sortByNickname();
                break;
            case commonName:
                sortedList = sortByCommonName();
                break;
            case scientificName:
                sortedList = sortByScientificName();
                break;
            case waterNeed:
                sortedList = sortByWaterNeed();
                break;
            default:
                sortedList = listToBeSorted;
        }
        return sortedList;
    }

    private static ArrayList<Plant> sortByNickname() {

        listToBeSorted.sort((plant1, plant2) -> {
            String s1 = plant1.getNickname();
            String s2 = plant2.getNickname();
            return s1.compareToIgnoreCase(s2);
        });
        return listToBeSorted;
    }

    private static ArrayList<Plant> sortByCommonName() {

        listToBeSorted.sort((plant1, plant2) -> {
            String s1 = plant1.getCommonName();
            String s2 = plant2.getCommonName();
            return s1.compareToIgnoreCase(s2);
        });
        return listToBeSorted;
    }

    private static ArrayList<Plant> sortByScientificName() {

        listToBeSorted.sort((plant1, plant2) -> {
            String s1 = plant1.getScientificName();
            String s2 = plant2.getScientificName();
            return s1.compareToIgnoreCase(s2);
        });
        return listToBeSorted;
    }

    private static ArrayList<Plant> sortByWaterNeed() {

        listToBeSorted.sort((plant1, plant2) -> {
            double d1 = plant1.getProgress();
            double d2 = plant2.getProgress();
            return Double.compare(d1, d2);
        });
        return listToBeSorted;
    }
}
