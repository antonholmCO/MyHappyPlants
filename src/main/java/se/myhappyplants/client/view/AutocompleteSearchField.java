package se.myhappyplants.client.view;

import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class AutocompleteSearchField extends TextField {

    private ArrayList<String> searchHistory;
    private ContextMenu historyPopup;

    public AutocompleteSearchField () throws IOException {
        super();
        historyPopup = new ContextMenu();
        populateSearchHistory();
        setListener();
    }

    public void populateSearchHistory () throws IOException {
        searchHistory = new ArrayList<>();

        File file = new File("resources/searchHistory.txt");
        if(!file.exists()) {
            file.createNewFile();
        }
        else if(file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader("resources/searchHistory.txt"));) {
                String line;
                while ((line = br.readLine()) != null) {
                    searchHistory.add(line);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setListener() {
        textProperty().addListener((observable, oldValue, newValue) -> {
            String enteredText = getText();
            if (enteredText == null || enteredText.isEmpty()) {
                historyPopup.hide();
            } else {
                List<String> filteredEntries = searchHistory.stream()
                        .filter(e -> e.toLowerCase().contains(enteredText.toLowerCase()))
                        .collect(Collectors.toList());
                if (!filteredEntries.isEmpty()) {
                    populatePopup(filteredEntries, enteredText);
                    if (!historyPopup.isShowing()) {
                        historyPopup.show(AutocompleteSearchField.this, Side.BOTTOM, 0, 0); //position of popup
                    }
                } else {
                    historyPopup.hide();
                }
            }
        });

        focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            historyPopup.hide();
        });
    }

    private void populatePopup(List<String> searchResult, String enteredText) {

        List<CustomMenuItem> menuItems = new LinkedList<>();
        int maxEntries = 10;
        int count = Math.min(searchResult.size(), maxEntries);
        for (int i = 0; i < count; i++) {
            final String result = searchResult.get(i);
            Label entryLabel = new Label();
            entryLabel.setGraphic(buildTextFlow(result, enteredText));
            entryLabel.setPrefHeight(10);
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            menuItems.add(item);
            item.setOnAction(actionEvent -> {
                setText(result);
                positionCaret(result.length());
                historyPopup.hide();
            });
        }
        historyPopup.getItems().clear();
        historyPopup.getItems().addAll(menuItems);
    }

    private Node buildTextFlow(String text, String filter) {
        int filterIndex = text.toLowerCase().indexOf(filter.toLowerCase());
        Text textBefore = new Text(text.substring(0, filterIndex));
        Text textAfter = new Text(text.substring(filterIndex + filter.length()));
        Text textFilter = new Text(text.substring(filterIndex,  filterIndex + filter.length())); //instead of "filter" to keep all "case sensitive"
        textFilter.setFill(Color.GREEN);
        return new TextFlow(textBefore, textFilter, textAfter);
    }

    public void addToHistory() {
        String searchText = getText()+"\n";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("resources/searchHistory.txt", true))) {
            bw.write(searchText);
            bw.flush();
            populateSearchHistory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
