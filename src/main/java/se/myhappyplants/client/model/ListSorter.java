package se.myhappyplants.client.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import se.myhappyplants.shared.Plant;

import java.util.ArrayList;

/**
 * Class that sorts lists according to different options
 */
public class ListSorter {

    public static ObservableList<SortingOption> sortOptions() {
        ObservableList<SortingOption> sortOptions = FXCollections.observableArrayList();
        for (SortingOption option: SortingOption.values()) {
            sortOptions.add(option);
        }
        return sortOptions;
    }

    /**
     * calls a different sorting technique based on sorting option selected
     * @param sortOption
     * @param currentUserLibrary
     * @return
     */
    public static ArrayList<Plant> sort(SortingOption sortOption, ArrayList<Plant> currentUserLibrary) {
        ArrayList<Plant> sortedList = null;
        switch (sortOption) {
            case commonName:
                sortedList = sortByCommonName(currentUserLibrary);
                break;
            case waterNeed:
                sortedList = sortByWaterNeed(currentUserLibrary);
                break;
            default:
                sortedList = sortByNickname(currentUserLibrary);
        }
        return sortedList;
    }

    public static ArrayList<Plant> sortByNickname(ArrayList<Plant> currentUserLibrary) {

        currentUserLibrary.sort((plant1, plant2) -> {
            String s1 = plant1.getNickname();
            String s2 = plant2.getNickname();
            return s1.compareToIgnoreCase(s2);
        });
        return currentUserLibrary;
    }

    public static ArrayList<Plant> sortByCommonName(ArrayList<Plant> currentUserLibrary) {

        currentUserLibrary.sort((plant1, plant2) -> {
            String s1 = plant1.getCommonName();
            String s2 = plant2.getCommonName();
            return s1.compareToIgnoreCase(s2);
        });
        return currentUserLibrary;
    }

    public static ArrayList<Plant> sortByWaterNeed(ArrayList<Plant> currentUserLibrary) {

        currentUserLibrary.sort((plant1, plant2) -> {
            double d1 = plant1.getProgress();
            double d2 = plant2.getProgress();
            return Double.compare(d1, d2);
        });
        return currentUserLibrary;
    }
}
