package com.ws.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
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
 **/

    // TODO : Appeler un/plusieurs services qui Renseigne la liste des joueurs, l'id de la game, l'id d'un round
    public Response getScores(int user_id, int round_id, int game_id, String type) throws JsonProcessingException {
        ObjectMapper mapper  = new ObjectMapper();
        ObjectNode dataTable = Scores(user_id, round_id, game_id, type);

        return Response.status(200).entity(mapper.writeValueAsString(dataTable)).build();
    }


    public ObjectNode Scores(int user_id, int round_id, int game_id, String type) throws JsonProcessingException {
        int[] scoring ;

        // for (int i = 0; i < counter; i++) {
        scoring = toThrow(type);
           /*  System.out.println("Score tour 1 : " + scoring[i][0]
                    + "Score tour 2 : " + scoring[i][1]);
                    */
        // }

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
    public static int[] toThrow(String type) {
        int[] scoring = new int[2];

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

    /**
     * @param fallenKeels int
     * @return isStrike Boolean
     */
    public static boolean isStrike(int fallenKeels) {
        if (fallenKeels == 10) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param keelsNumber int
     * @param type        String
     * @return isStrike Boolean
     */
    public static int profileThrowing(String type, int keelsNumber) {
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
    public static int randomInRange(int min, int max) {
        int remainingKeels = min + (int) (Math.random() * ((max - min) + 1));
        return remainingKeels;
    }


}
