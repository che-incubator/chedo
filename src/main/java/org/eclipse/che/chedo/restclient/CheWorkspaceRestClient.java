package org.eclipse.che.chedo.restclient;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.eclipse.che.chedo.model.CheWorkspace;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/workspace")
@RegisterRestClient
public interface CheWorkspaceRestClient {

    @GET
    @Path("/{workspaceId}")
    @Produces("application/json")
    CheWorkspace getWorkspace(@PathParam("workspaceId") String workspaceId, @HeaderParam("Authorization") String authHeaderValue);

}
