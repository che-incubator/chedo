package org.eclipse.che.chedo.commands;

import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.eclipse.che.chedo.CheWorkspace;
import org.eclipse.che.chedo.CheWorkspaceService;
import org.eclipse.che.chedo.model.CheMachines;
import org.eclipse.che.chedo.model.CheServer;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name="lsRoutes")
public class LsRoutes implements Runnable {

    @Option(names = { "-u", "--url"}, description = "Display url")
    private boolean showUrl;

    @Inject
    @RestClient
    CheWorkspaceService service;

    @ConfigProperty(name = "CHE_WORKSPACE_ID")
    String workspaceId;

    @ConfigProperty(name = "CHE_MACHINE_TOKEN")
    String machineToken;

    @Override
    public void run() {
        final CheWorkspace workspace = service.getWorkspace(workspaceId, "Bearer " + machineToken);
        final Map<String, CheMachines> machines = workspace.runtime.machines;
        for( final Entry<String, CheMachines> mEntry : machines.entrySet()) {
           if ( mEntry.getValue().servers != null ){
                for (final Entry<String, CheServer> server: mEntry.getValue().servers.entrySet()) {
                    String display = server.getKey();
                    if(showUrl){
                        display += "\t"+server.getValue().url;
                    }
                    System.out.println(display);
                }
           }
        }                                          
	}

}