package se.myhappyplants.shared;

import se.myhappyplants.server.model.plant.Links;

import java.io.Serializable;
import java.net.URL;

/**
 * Class defining a plant from the API
 * Created by: Frida Jacobsson
 * Updated by: Linn Borgström, Eric Simonson, Susanne Vikström
 */
public class APIPlant implements Serializable {

  public String common_name;
  public String scientific_name;
  public String family_common_name;
  private String lightLevel;
  private String minWaterLevel;
  private String maxWaterLevel;
  public URL image_url;
  public Links links;

  /**
   * Constructor used to create a plant based on information from API
   *
   * @param common_name
   * @param scientific_name
   * @param family_common_name
   * @param image_url
   * @param links
   */

  public APIPlant(String common_name, String scientific_name, String family_common_name, URL image_url, Links links) {
    this.common_name = common_name;
    this.scientific_name = scientific_name;
    this.family_common_name = family_common_name;
    this.image_url = image_url;
    this.links = links;
  }


  public String getCommon_name() {
    return common_name;
  }

  public void setCommon_name(String common_name) {
    this.common_name = common_name;
  }

  public String getScientific_name() {
    return scientific_name;
  }

  public void setScientific_name(String scientific_name) {
    this.scientific_name = scientific_name;
  }

  public String getFamily_common_name() {
    return family_common_name;
  }

  public void setFamily_common_name(String family_common_name) {
    this.family_common_name = family_common_name;
  }

  public URL getImage_url() {
    return image_url;
  }

  public void setImage_url(URL image_url) {
    this.image_url = image_url;
  }

  public Links getLinks() {
    return links;
  }

  public void setLinks(Links links) {
    this.links = links;
  }

  public String getLightLevel() {
    return lightLevel;
  }

  public String getMinWaterLevel() {
    return minWaterLevel;
  }

  public String getMaxWaterLevel() {
    return maxWaterLevel;
  }

  public String toString() {
    String toString = String.format("Common name: %s \tFamily name: %s \tScientific name: %s ", common_name, family_common_name, scientific_name);
    return toString;
  }

  public String toString2(){
    String toString = String.format("Light level: %s \tMinimum water: %s \tMaximum water: %s ", lightLevel, minWaterLevel, maxWaterLevel);
    return toString;
  }


}
