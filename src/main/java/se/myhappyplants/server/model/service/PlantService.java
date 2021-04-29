package se.myhappyplants.server.model.service;

import com.google.gson.Gson;
import se.myhappyplants.server.PasswordsAndKeys;
import se.myhappyplants.server.controller.Controller;
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
 * Updated by: Linn Borgström, Eric Simonson, Susanne Vikström
 */
public class PlantService {

  private String trefleURL = "https://trefle.io";
  private String searchURL = trefleURL + "/api/v1/plants/search?token=eI01vwK-LgBiMpuVI3tqDaT7xKSEyoEl2qf20rwxb9k&filter_not[maximum_precipitation_mm]=null&q=";
  private Controller controller;

  public PlantService(Controller controller){
    this.controller=controller;
  }

  public PlantService() {

  }

  /**
   * Method for getting common name, family name and scientific name based on a search word
   * @param userSearch search word from client and application
   * @return a list of plants from the API
   * @throws IOException
   * @throws InterruptedException
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
  public String getMoreInformation(APIPlant plant) throws IOException, InterruptedException {
    String token = PasswordsAndKeys.APIToken;
    String plantURL = trefleURL + plant.links.plant + "?token=" + token;

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(plantURL))
            .build();

    HttpResponse<String> response =
            client.send(request, HttpResponse.BodyHandlers.ofString());

    Gson gson = new Gson();
    PlantDetail plantDetail = gson.fromJson(response.body(), PlantDetail.class);

    String minWater = plantDetail.data.main_species.growth.minimum_precipitation.toString();
    int light = plantDetail.data.main_species.growth.light;
    String lightText = controller.calculateLightLevel(light);
    String waterText =  controller.calculateWater(minWater);
    String lightWaterInfo = lightText + "\n" + waterText;
    System.out.println(lightWaterInfo);
    return lightWaterInfo;
  }

  public long getWaterFrequency (String apiURL) throws IOException, InterruptedException {
    String token = PasswordsAndKeys.APIToken;
    String plantURL = trefleURL + apiURL + "?token=" + token;
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(plantURL))
            .build();

    HttpResponse<String> response =
            client.send(request, HttpResponse.BodyHandlers.ofString());

    Gson gson = new Gson();
    PlantDetail plantDetail = gson.fromJson(response.body(), PlantDetail.class);
    String waterFreqMin = plantDetail.data.main_species.growth.minimum_precipitation.toString();
    String parsedWaterFreq = waterFreqMin.substring(4, waterFreqMin.length()-3);
    long waterFrequencyMilli = 0;
    try {
      long week = 604000000l;
      //todo find better calculation for how often each plant needs watering
      //1 day = 86 000 000
      //min water = 200mm/year -> 4 weeks
      //min water = 1000mm/year -> 1 week
      int waterFrequencyInt = Integer.parseInt(parsedWaterFreq);
      if(waterFrequencyInt<=200) {
        waterFrequencyMilli = week * 4;
      }
      else if(waterFrequencyInt>200 && waterFrequencyInt<=400) {
        waterFrequencyMilli = week * 3;
      }
      else if(waterFrequencyInt>400 && waterFrequencyInt<=600) {
        waterFrequencyMilli = week * 2;
      }
      else if(waterFrequencyInt>600 && waterFrequencyInt<=800) {
        waterFrequencyMilli = week * 1;
      }
      else if(waterFrequencyInt>800) {
        waterFrequencyMilli = week / 2;
      }
    }
    catch (NumberFormatException e) {

    }
    return waterFrequencyMilli;
  }
}
