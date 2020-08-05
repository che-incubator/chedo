package org.eclipse.che.chedo.commands;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.eclipse.che.chedo.CheWorkspace;
import org.eclipse.che.chedo.CheWorkspaceService;
import org.eclipse.che.chedo.model.CheMachines;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import picocli.CommandLine;

@CommandLine.Command(name="getRoute")
public class GetRoute implements Runnable {

    @CommandLine.Option(names = {"-n", "--name"}, description = "Current workspace route name", defaultValue = "theia")
    String name;

    @Inject
    @RestClient
    CheWorkspaceService service;

    @ConfigProperty(name = "CHE_WORKSPACE_ID")
    String workspaceId;

    @ConfigProperty(name = "CHE_MACHINE_TOKEN")
    String machineToken;

    @Override
    public void run() {
        System.out.println(getRoute(name));
    }

   	public String getRoute(final String routeName) {
        CheWorkspace workspace = service.getWorkspace(workspaceId, "Bearer " + machineToken);

        Map<String, CheMachines> machines = workspace.runtime.machines;
        for( Entry<String, CheMachines> mEntry : machines.entrySet()) {
           if ( mEntry.getValue().servers != null && mEntry.getValue().servers.containsKey(routeName)){
               return mEntry.getValue().servers.get(routeName).url;
           }
        }
        throw new RuntimeException("Couldn't find any route in the workspace that is matching " + routeName);
	}

}