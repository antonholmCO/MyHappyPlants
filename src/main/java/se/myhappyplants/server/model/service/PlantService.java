package se.myhappyplants.server.model.service;

import com.google.gson.Gson;
import se.myhappyplants.shared.APIPlant;
import se.myhappyplants.server.model.plant.PlantCollection;
import se.myhappyplants.server.model.plant.PlantDetail;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

/**
 * Class responsable for calling API and collect information about plants.
 * Created by: Frida Jacobsson
 * Updated by:
 */
public class PlantService {

  private String trefleURL = "https://trefle.io";
  private String searchURL = trefleURL + "/api/v1/plants/search?token=eI01vwK-LgBiMpuVI3tqDaT7xKSEyoEl2qf20rwxb9k&q=";

  /**
   * Method for getting common name, family name and scientific name based on a search word
   * @param userSearch search word from client and application
   * @return a list of plants from the API
   * @throws IOException
   * @throws InterruptedException,
   */
  public ArrayList<APIPlant> getResult(String userSearch) throws IOException, InterruptedException {

    String plantURL = searchURL + userSearch;

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(plantURL))
            .build();

    HttpResponse<String> response =
            client.send(request, HttpResponse.BodyHandlers.ofString());

    Gson gson = new Gson();
    PlantCollection plantCollection = gson.fromJson(response.body(), PlantCollection.class);
    ArrayList<APIPlant> plantResult = plantCollection.getData();
    return plantResult;
  }

  /**
   * Method for getting max and min precipitation and need of light based on plant-object
   * TODO should return something
   * @param plant An instance of a specific APIPlant
   * @throws IOException
   * @throws InterruptedException
   */
  public void getMoreInformation(APIPlant plant) throws IOException, InterruptedException {
    String plantURL = trefleURL + plant.links.plant + "?token=eI01vwK-LgBiMpuVI3tqDaT7xKSEyoEl2qf20rwxb9k";
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
