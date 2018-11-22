/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.gifmebackend.memebuilder;

import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author Administratör
 */
@Path("/memebuilder")
public class RequestHandler {
    @GET
    @Path("/text/random")
    public Response randomText(@QueryParam("sek") String sek) throws IOException {
        List<String> res = GetWikiApi.GetWiki(sek);
        return Response.ok(res).build();
    }
}
