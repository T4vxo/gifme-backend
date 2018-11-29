/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.gifmebackend.randomgif;

import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author ElKebabHenry
 */
@Path("/radgif")
public class RequestHandler {
// id can be exchanged for any searchword

    /**
     *
     * @param query
     * @return
     * @throws IOException
     */
    @Path("/gif/{query}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response randomGif(@PathParam("query") String query) throws IOException {
        JSONObject obj = new JSONObject();
        System.out.println("allways");
        try {
            System.out.println("try");
            obj.put("result", "ok");
            obj.put("url", GiphyService.getInstance().getGifUrl(query));
            return Response.ok(obj.toJSONString()).build();
        } catch (Exception e) {
            System.out.println("catch");
            return Response.status(Response.Status.NO_CONTENT).build();
        }

    }
}
