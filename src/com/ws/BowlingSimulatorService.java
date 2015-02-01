package com.ws;

import com.ws.impl.BowlingSimulator;

import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Akronys on 01/02/2015.
 */

@Path("/")
public class BowlingSimulatorService extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(BowlingSimulator.class);
        return classes;

    }
}

