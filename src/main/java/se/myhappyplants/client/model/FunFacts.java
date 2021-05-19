package se.myhappyplants.client.model;

import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class FunFacts {

    private ArrayList<String> factsArray;

    public FunFacts() {
        factsArray = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/facts.txt"));) {
            String line;
            while ((line = br.readLine()) != null) {
                factsArray.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getRandomFact() {
        Random random = new Random();
        int randomIndex = random.nextInt(factsArray.size()) - 1;
        return factsArray.get(randomIndex);
    }

}
