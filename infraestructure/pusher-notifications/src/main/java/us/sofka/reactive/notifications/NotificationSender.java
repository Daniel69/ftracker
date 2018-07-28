package us.sofka.reactive.notifications;

import com.pusher.rest.Pusher;

import java.util.Collections;

public class NotificationSender {

    public static void main(String[] args) {
        Pusher pusher = new Pusher("567868", "85b9372f508af897da66", "593da6693c7bfb31eaf7");
        pusher.setCluster("us2");
        pusher.setEncrypted(true);

        pusher.trigger("my-channel", "my-event", Collections.singletonMap("message", "Hola Mundo!"));
        System.out.println("Success!");

    }
}
