/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package te.te4.gifmebackend.auth.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import te.te4.gifmebackend.auth.User;
import te.te4.gifmebackend.db.ConnectionFactory;

/**
 *
 * @author johan
 */
@Provider
public class AuthFilter implements ContainerRequestFilter {

    /**
     * URIs which do not require authorization.
     */
    private static String[] WHITELISTED_URIS = new String[]{
        //  KEEP ALL THESE LOWERCASE
        "/auth",
        "/randgif/gif"
    };

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (!requiresAuth(requestContext.getUriInfo().getPath())) {
            return;
        }

        try {
            String authHeader = requestContext.getHeaderString("Authorization");
            
            if (authHeader == null || !authenticateAuthHeader(authHeader)) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            requestContext.abortWith(Response.serverError().build());
        }
    }

    /**
     * Checks with a header for authentication.
     *
     * @param header
     * @return Whether the header has valid authentication credentials.
     */
    private boolean authenticateAuthHeader(String header) throws SQLException, IllegalStateException {
        try {
            //  We don't care about the user ID
            User.getUserIdFromAuth(header);
        } catch (SecurityException secEx) {
            return false;
        } catch (SQLException sqlEx) {
            throw sqlEx;
        } catch (IllegalStateException illegalStateEx) {
            throw illegalStateEx;
        }

        return false;
    }

    private boolean requiresAuth(String uri) {
        String basePath = "/api";
        String uriWithoutBasePath;
        
        if (uri.toLowerCase().startsWith(basePath)) {
            uriWithoutBasePath = uri.substring(basePath.length());
        } else {
            uriWithoutBasePath = uri;
        }
        
        if (!uriWithoutBasePath.startsWith("/")) {
            uriWithoutBasePath = "/" + uriWithoutBasePath;
        }
        
        for (String whitelistedUri : WHITELISTED_URIS) {
            if (uriWithoutBasePath.toLowerCase().startsWith(whitelistedUri)) {
                return false;
            }
        }
        
        return true;
    }
}
