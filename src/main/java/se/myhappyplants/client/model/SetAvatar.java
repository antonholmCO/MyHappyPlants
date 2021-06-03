package se.myhappyplants.client.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class that set the avatar picture.
 * Created by: Anton Holm
 * Updated by: Linn Borgström, 2021-05-13
 */
public class SetAvatar {

    /**
     * Static method that is called when a user is logged in
     * @param email to pair up the picture to
     * @return that path to the picture
     */
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
