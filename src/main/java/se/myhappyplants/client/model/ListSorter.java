package se.myhappyplants.client.model;

import se.myhappyplants.shared.Plant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListSorter {

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

    public static ArrayList<Plant> sort(String sortOption, ArrayList<Plant> currentUserLibrary) {
        ArrayList<Plant> sortedList = null;
        switch (sortOption) {
            case "  Common name":
                sortedList = sortByCommonName(currentUserLibrary);
                break;
            case "  Water need":
                sortedList = sortByWaterNeed(currentUserLibrary);
                break;
            default:
                sortedList = sortByNickname(currentUserLibrary);
        }
        return sortedList;
    }
}
