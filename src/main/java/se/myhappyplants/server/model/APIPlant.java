package se.myhappyplants.server.model;


import se.myhappyplants.server.model.plant.Links;

import java.net.URL;

public class APIPlant {

  public String common_name;
  public String scientific_name;
  public String family_common_name;
  public URL image_url;
  public Links links;

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
}
