package de.medieninformatik.server;

import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Jeremy Grunert m29265
 * date 2022-02-10
 * Programmierung 3 - Hausarbeit
 * <p>
 * The CineLabApplication class provides all necessary REST classes for the grizzly server.
 */
public class CineLabApplication extends Application {
    private final Set<Class<?>> classes = new HashSet<>(); // Set to save all classes.

    /**
     * The constructor adds all REST classes to the "classes" set.
     */
    public CineLabApplication() {
        classes.add(CineLabRest.class);
    }

    /**
     * Getter for set "classes".
     *
     * @return set of all added classes
     */
    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }
}
