/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.rest.application.config;

import java.util.Set;
import javax.ws.rs.core.Application;
import org.jsoup.Jsoup;

/**
 *
 * @author Administratör
 */
@javax.ws.rs.ApplicationPath("/api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(te.te4.gifmebackend.auth.RequestHandler.class);
        resources.add(te.te4.gifmebackend.auth.filter.AuthFilter.class);
        resources.add(te.te4.gifmebackend.greetings9gag.RequestHandler.class);
        resources.add(te.te4.gifmebackend.memebuilder.RequestHandler.class);
        resources.add(te.te4.gifmebackend.randomgif.RequestHandler.class);
        resources.add(te.te4.gifmebackend.test.RequestHandler.class);
        resources.add(te.te4.gifmebackend.utils.CORSFilter.class);
    }
    
}
