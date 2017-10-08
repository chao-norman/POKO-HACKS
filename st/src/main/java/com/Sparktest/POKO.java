package com.Sparktest;
import com.ciscospark.*;
import java.net.URI;

public class POKO {

    public static void main( String[] args ) {

        System.out.println("DON'T BE A POKO");

        // To obtain a developer access token, visit https://developer.ciscospark.com
        String accessToken = "NTFmODVlYjUtYjUyZi00NmNkLTg2YTAtYjFjNDkxZGZhZDhjNzJkYzRmYzItYWFi";

        // Initialize the client
        Spark spark = Spark.builder()
                .baseUrl(URI.create("https://api.ciscospark.com/v1"))
                .accessToken(accessToken)
                .build();


        // List the rooms that I'm in
        spark.rooms()
                .iterate()
                .forEachRemaining(room -> {
                    System.out.println(room.getTitle() + ", created " + room.getCreated() + ": " + room.getId());
                });

        // Create a new room
        Room room = new Room();
        room.setTitle("Hello World");
        room = spark.rooms().post(room);


        // Add a coworker to the room
        Membership membership = new Membership();
        membership.setRoomId(room.getId());
        membership.setPersonEmail("huang.alpal@berkeley.edu");
        spark.memberships().post(membership);

        // List the members of the room
        spark.memberships().queryParam("roomId", room.getId())
                .iterate()
                .forEachRemaining(member -> {
                    System.out.println(member.getPersonEmail());
                });

        // Post a text message to the room
        Message message = new Message();
        message.setRoomId(room.getId());
        message.setText("Hello World!");
        spark.messages().post(message);

        // Share a file with the room
        message = new Message();
        message.setRoomId(room.getId());
        message.setFiles(URI.create("https://i.imgur.com/qLO4grP.gif?noredirect"));
        spark.messages().post(message);

    }
}
