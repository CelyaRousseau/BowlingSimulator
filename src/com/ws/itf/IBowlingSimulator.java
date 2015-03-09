package com.ws.itf;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Akronys on 01/02/2015.
 */

@Path("/")
public interface IBowlingSimulator {

    // TODO: Modifier la route pour qu'elle prenne en compte une "piste".

    @POST
    @Path("/Scores")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getScores(@QueryParam("user_id") int user_id, @QueryParam("round_id") int counter, @QueryParam("game_id") int game_id, @QueryParam("type") String type) throws JsonProcessingException;
    // TODO: Ajouter RabbitMQ et actionner ce service en fonction d'un lancement de partie -> msag reçu.

}