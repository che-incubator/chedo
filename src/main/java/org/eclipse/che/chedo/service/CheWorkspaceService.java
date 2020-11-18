package org.eclipse.che.chedo.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.che.chedo.model.CheWorkspace;
import org.eclipse.che.chedo.restclient.CheWorkspaceRestClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Singleton
public class CheWorkspaceService {

    @Inject
    @RestClient
    CheWorkspaceRestClient service;

    @ConfigProperty(name = "CHE_WORKSPACE_ID")
    String workspaceId;

    @ConfigProperty(name = "CHE_MACHINE_TOKEN")
    String machineToken;

    public String getRoute(final String routeName) {

        List<String> routes = getWorkspace().runtime.machines //
                .entrySet() //
                .stream() //
                .map((machinesEntry) -> machinesEntry.getValue().servers) //
                .filter((servers) -> servers != null && servers.containsKey(routeName)) //
                .map((servers) -> servers.get(routeName).url) //
                .collect(Collectors.toList());

        if (routes.isEmpty()) {
            throw new RuntimeException("Couldn't find any route in the workspace that is matching " + routeName);
        }
        return routes.get(0);
    }

    public void displayRoutes(boolean showUrl) {

        getWorkspace().runtime.machines //
                .entrySet() //
                .stream() //
                .filter((machinesEntry) -> machinesEntry != null &&  machinesEntry.getValue().servers !=null) //
                .flatMap((machinesEntry) -> machinesEntry.getValue().servers.entrySet().stream()) //
                .forEach((server) -> {
                    String display = server.getKey();
                    if (showUrl) {
                        display += "\t" + server.getValue().url;
                    }
                    System.out.println(display);
                });
    }

    private CheWorkspace getWorkspace() {
        return service.getWorkspace(workspaceId, "Bearer " + machineToken);
    }
}
