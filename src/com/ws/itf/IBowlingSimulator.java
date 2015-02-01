package com.ws.itf;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by Akronys on 01/02/2015.
 */

@Path("")
public interface IBowlingSimulator {

    @GET
    @Path("/Scores")
    @Produces(MediaType.APPLICATION_JSON)
    public int[] getScores();

}