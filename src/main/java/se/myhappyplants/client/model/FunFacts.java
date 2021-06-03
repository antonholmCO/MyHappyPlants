package se.myhappyplants.client.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class to get read the fun fact from a file and show it to the user
 * Created by: Christopher O'Driscoll
 * Updated by: Christopher O'Driscoll
 */
public class FunFacts {

    private ArrayList<String> factsArray;

    /**
     * Constructor that reads from a text file.
     */
    public FunFacts() {
        factsArray = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/facts.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                factsArray.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method to randomize the facts
     * @return ObservableList<Labels> to put on the ListView
     */
    public ObservableList<Label> getRandomFact() {
        Random random = new Random();
        int randomIndex = random.nextInt(factsArray.size());
        ObservableList<Label> funFactsList = FXCollections.observableArrayList();
        Label lblFunFactText = new Label();
        lblFunFactText.setMaxWidth(195);
        lblFunFactText.setWrapText(true);
        lblFunFactText.setText(factsArray.get(randomIndex));
        funFactsList.add(lblFunFactText);
        return funFactsList;
    }
}
