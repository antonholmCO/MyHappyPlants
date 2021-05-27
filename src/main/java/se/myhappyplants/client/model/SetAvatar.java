package se.myhappyplants.client.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by: Anton Holm
 * Updated by: Linn Borgström, 2021-05-13
 */
public class SetAvatar {

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
