/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.gifmebackend.test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author johan
 */
@Path("/test")
public class RequestHandler {
    private static Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    
    @GET
    @Path("/error")
    public Response purposelyTestError() {
        logger.error("Test error from purposelyTestError()");
        return Response.ok("See error log").build();
    }
}
