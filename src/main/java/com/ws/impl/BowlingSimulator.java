package com.ws.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ws.Publisher;
import com.ws.itf.IBowlingSimulator;

import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by Akronys on 01/02/2015.
 */
public class BowlingSimulator implements IBowlingSimulator {

    private static final String BEGINNER = "beginner";
    private static final String CASUAL   = "casual";
    private static final String ADVANCED = "advanced";

    /**    TODO : OUT WAITING
    {
        "game_id": 1,
        "round_id": 4,
        "user_id": 1,
        "scores": [
            2,
            2
        ]
    }

     {
        users:[
            {"id":"1"},
            {"id":"2"},
            {"id":"3"}
        ]
    }
 **/

    // TODO : Appeler un/plusieurs services qui Renseigne la liste des joueurs, l'id de la game, l'id d'un round
    public Response postScore(String data) throws IOException {
        ObjectMapper mapper  = new ObjectMapper();
        int game_id  = mapper.readTree(data).get("game_id").asInt();
        String type  = mapper.readTree(data).get("type").asText();
        int user_id  = mapper.readTree(data).get("user_id").asInt();
        int round_id = mapper.readTree(data).get("round_id").asInt();

        ObjectNode dataTable = scores(user_id, round_id, game_id, type);

        return Response.status(200).entity(mapper.writeValueAsString(dataTable)).build();
    }

    public Response postScores(String data) throws IOException {
        ObjectMapper mapper       = new ObjectMapper();
        ObjectNode dataTable      = null;
        int game_id;
        String type;
        int user_id;

        game_id = mapper.readTree(data).get("game_id").asInt();
        type    = mapper.readTree(data).get("type").asText();

        Long sleep;

        for (int i=1; i<=10; i++){
            for (JsonNode user : mapper.readTree(data).get("users")) {
                sleep = (long) Math.random() * (50000 - 5000) + 5000;   // Generate Random sleep to show real throw by a user
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                user_id = user.get("id").asInt();
                dataTable = scores(user_id, i, game_id, type);
            }
        }

        return Response.status(200).entity(mapper.writeValueAsString(dataTable)).build();
    }

    public ObjectNode scores(int user_id, int round_id, int game_id, String type) throws JsonProcessingException {
        int[] scoring;

        if(round_id == 10) {
            scoring = new int[3];
            scoring = toThrow(type, scoring);
            HandleLastTurnException(type, scoring);
        } else {
            scoring = new int[2];
            scoring = toThrow(type, scoring);
        }

        ObjectMapper mapper  = new ObjectMapper();
        ObjectNode dataTable = mapper.createObjectNode();

        dataTable.put("game_id", game_id);
        dataTable.put("user_id", user_id);
        dataTable.put("round_id", round_id);
        dataTable.putPOJO("scores", scoring);

        try {
            new Publisher().publishScore(mapper.writeValueAsString(dataTable));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataTable;
    }

    /**
     * @param type String
     */
    public int[] toThrow(String type, int[] scoring) {

        // First Lancer
        scoring[0] = profileThrowing(type, 10);

        if (isStrike(scoring[0])) {
            scoring[1] = 0;
        } else {
            // Second Lancer
            int remainingKeels = 10 - scoring[0];
            scoring[1] = randomInRange(0, remainingKeels);
        }
        return scoring;

    }

    public int[] HandleLastTurnException(String type, int[] scoring) {
        if(scoring[0] + scoring[1] == 10){
            scoring[2] = profileThrowing(type, 10);
        }
        return scoring;
    }

    /**
     * @param fallenKeels int
     * @return isStrike Boolean
     */
    public boolean isStrike(int fallenKeels) {
        return fallenKeels == 10;
    }

    /**
     * @param keelsNumber int
     * @param type        String
     * @return isStrike Boolean
     */
    public int profileThrowing(String type, int keelsNumber) {
        double alea;
        int remainingKeels;

        switch (type) {
            case BEGINNER:
                alea = Math.random() * keelsNumber;
                if (alea <= 1) {
                    remainingKeels = randomInRange(9, 10);
                } else if (alea <= 3) {
                    remainingKeels = randomInRange(6, 8);

                } else {
                    remainingKeels = randomInRange(0, 5);
                }
                break;
            case CASUAL:
                alea = Math.random() * keelsNumber;
                if (alea <= 2) {
                    remainingKeels = randomInRange(9, 10);
                } else if (alea <= 3) {
                    remainingKeels = randomInRange(0, 3);

                } else {
                    remainingKeels = randomInRange(4, 8);
                }
                break;
            case ADVANCED:
                alea = Math.random() * keelsNumber;
                if (alea <= 2) {
                    remainingKeels = randomInRange(0, 6);
                } else {
                    remainingKeels = randomInRange(7, 10);
                }
                break;
            default:
                System.err.println("The profil given is unrecognized");
                return 0;
        }
        return remainingKeels;
    }

    /**
     * @param min int
     * @param max int
     * @return remainingKeels int
     */
    public int randomInRange(int min, int max) {
        return min + (int) (((max - min) + 1) * Math.random());
    }


}
