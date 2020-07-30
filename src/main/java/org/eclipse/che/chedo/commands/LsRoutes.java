package org.eclipse.che.chedo.commands;

import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.eclipse.che.chedo.CheWorkspace;
import org.eclipse.che.chedo.CheWorkspaceService;
import org.eclipse.che.chedo.model.CheMachines;
import org.eclipse.che.chedo.model.CheServer;
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

    @Override
    public void run() {
        final String workspaceId = getCurrentWorkspaceId();
        final CheWorkspace workspace = service.getWorkspace(workspaceId, "Bearer " + System.getenv("CHE_MACHINE_TOKEN"));
        Map<String, CheMachines> machines = workspace.runtime.machines;
        for( Entry<String, CheMachines> mEntry : machines.entrySet()) {
           if ( mEntry.getValue().servers != null ){
                for (Entry<String, CheServer> server: mEntry.getValue().servers.entrySet()) {
                    String display = server.getKey();
                    if(showUrl){
                        display += "\t"+server.getValue().url;
                    }
                    System.out.println(display);
                }
           }
        }                                          
	}
    
    private String getCurrentWorkspaceId() {
        return System.getenv("CHE_WORKSPACE_ID");
    }

}