package se.myhappyplants.client.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import se.myhappyplants.client.view.PlantPane;

/**
 * Class that sorts lists of plant panes according to different options
 * Created by: Christopher O'Driscoll
 * Updated by: Christopher O'Driscoll
 */
public class ListSorter {

    private static ObservableList<PlantPane> listToBeSorted;

    /**
     * Creates a list of sorting options for search results
     * @return ObservableList<SortingOption>
     */
    public static ObservableList<SortingOption> sortOptionsSearch() {
        ObservableList<SortingOption> sortOptions = FXCollections.observableArrayList();
        for (SortingOption option : SortingOption.values()) {
            if (option != SortingOption.nickname && option != SortingOption.waterNeed) //null on search results
                sortOptions.add(option);
        }
        return sortOptions;
    }

    /**
     * Creates a list of sorting options for a user's library
     * @return ObservableList<SortingOption>
     */
    public static ObservableList<SortingOption> sortOptionsLibrary() {
        ObservableList<SortingOption> sortOptions = FXCollections.observableArrayList();
        for (SortingOption option : SortingOption.values()) {
            if (option != SortingOption.commonName && option != SortingOption.scientificName) //null on library plants
                sortOptions.add(option);
        }
        return sortOptions;
    }

    /**
     * calls a different sorting technique based on sorting option selected
     *
     * @param sortOption
     * @param plantList
     * @return ObservableList<PlantPane>
     */
    public static ObservableList<PlantPane> sort(SortingOption sortOption, ObservableList<PlantPane> plantList) {
        listToBeSorted = plantList;
        ObservableList<PlantPane> sortedList;
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

    /**
     * Method to sort the list of plants by nickname
     * @return ObservableList<PlantPane> to put on the ListView in the GUI
     */
    private static ObservableList<PlantPane> sortByNickname() {

        listToBeSorted.sort((pane1, pane2) -> {
            String s1 = pane1.getPlant().getNickname();
            String s2 = pane2.getPlant().getNickname();
            return s1.compareToIgnoreCase(s2);
        });
        return listToBeSorted;
    }

    /**
     * Method to sort the list of plants by common name
     * @return ObservableList<PlantPane> to put on the ListView in the GUI
     */
    private static ObservableList<PlantPane> sortByCommonName() {

        listToBeSorted.sort((pane1, pane2) -> {
            String s1 = pane1.getPlant().getCommonName();
            String s2 = pane2.getPlant().getCommonName();
            return s1.compareToIgnoreCase(s2);
        });
        return listToBeSorted;
    }
    /**
     * Method to sort the list of plants by scientific name
     * @return ObservableList<PlantPane> to put on the ListView in the GUI
     */
    private static ObservableList<PlantPane> sortByScientificName() {

        listToBeSorted.sort((pane1, pane2) -> {
            String s1 = pane1.getPlant().getScientificName();
            String s2 = pane2.getPlant().getScientificName();
            return s1.compareToIgnoreCase(s2);
        });
        return listToBeSorted;
    }
    /**
     * Method to sort the list of plants by the need of water
     * @return ObservableList<PlantPane> to put on the ListView in the GUI
     */
    private static ObservableList<PlantPane> sortByWaterNeed() {

        listToBeSorted.sort((pane1, pane2) -> {
            double d1 = pane1.getPlant().getProgress();
            double d2 = pane2.getPlant().getProgress();
            return Double.compare(d1, d2);
        });
        return listToBeSorted;
    }
}
