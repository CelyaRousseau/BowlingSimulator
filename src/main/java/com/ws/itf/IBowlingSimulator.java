package com.ws.itf;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by Akronys on 01/02/2015.
 */

@Path("/")
public interface IBowlingSimulator {

    // TODO: Modifier la route pour qu'elle prenne en compte une "piste".

    @POST
    @Path("/Scores")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postScores(String users) throws IOException;

    @POST
    @Path("/Score")
    @Produces(MediaType.APPLICATION_JSON)
    public Response postScore(String user) throws IOException;

    // TODO: Ajouter RabbitMQ et actionner ce service en fonction d'un lancement de partie -> msag reçu.

}