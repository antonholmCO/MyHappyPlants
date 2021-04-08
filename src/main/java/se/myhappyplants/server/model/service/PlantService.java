package se.myhappyplants.server.model.service;

import com.google.gson.Gson;
import se.myhappyplants.server.model.APIPlant;
import se.myhappyplants.server.model.Plant.PlantCollection;
import se.myhappyplants.server.model.Plant.PlantDetail;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class PlantService {

  /**
   * Class responsable for calling API and collect information about plants.
   * Version 1. Author: Frida Jacobsson.
   */

  //new PlantService().getResult("https://trefle.io/api/v1/plants/search?token=eI01vwK-LgBiMpuVI3tqDaT7xKSEyoEl2qf20rwxb9k&q="+search);

  private String trefleURL = "https://trefle.io";
  private String searchURL = trefleURL + "/api/v1/plants/search?token=eI01vwK-LgBiMpuVI3tqDaT7xKSEyoEl2qf20rwxb9k&q=";

  public void getResult() throws Exception {

    Scanner scanner = new Scanner(System.in);
    System.out.println("What would you like to search for?");
    String userSearch = scanner.nextLine();
    String plantURL = searchURL + userSearch;

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(plantURL))
            .build();

    HttpResponse<String> response =
            client.send(request, HttpResponse.BodyHandlers.ofString());

    Gson gson = new Gson();
    PlantCollection plantCollection = gson.fromJson(response.body(), PlantCollection.class);

    for (APIPlant plant : plantCollection.data) {
      System.out.println("Common name: " + plant.common_name + ", Scientific name: " + plant.scientific_name + ", Family name: " + plant.family_common_name);
      getMoreInformation(plant);
    }
  }

  public void getMoreInformation(APIPlant plant) throws IOException, InterruptedException {
    String plantURL = trefleURL + plant.links.plant+"?token=eI01vwK-LgBiMpuVI3tqDaT7xKSEyoEl2qf20rwxb9k";
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(plantURL))
            .build();

    HttpResponse<String> response =
            client.send(request, HttpResponse.BodyHandlers.ofString());

    Gson gson = new Gson();
    PlantDetail plantDetail = gson.fromJson(response.body(), PlantDetail.class);
    System.out.println(plantDetail.data.main_species.growth.light); //hur mycket ljus växten behöver.
    System.out.println(plantDetail.data.main_species.growth.maximum_precipitation); //mm per år
    System.out.println(plantDetail.data.main_species.growth.minimum_precipitation); //mm per år
  }
}
