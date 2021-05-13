package se.myhappyplants.shared;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SetAvatar {

    //TODO: Fråga gruppen om detta skavara här.
    // Om ja: ta bort kommentarer i SearchTabConroller rad 51, MyPlantsTabController rad 50,
    //TODO: samt settingsTebController rad 40
    public static String setAvatarOnLogin(String email) {
        String avatarURL;
        try (BufferedReader br = new BufferedReader(new FileReader("resources/images/user_avatars/" + email + "_avatar.txt"))) {
            String readtxt = br.readLine();
            avatarURL = new File(readtxt).toURI().toString();
        }
        catch (IOException e) {
            avatarURL = new File("resources/images/user_default_img.png").toURI().toString();
        }
        return avatarURL;
    }
}
