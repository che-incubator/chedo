package org.eclipse.che.chedo.commands;

import javax.inject.Inject;

import org.eclipse.che.chedo.service.CheWorkspaceService;

import picocli.CommandLine;
import picocli.CommandLine.Parameters;

@CommandLine.Command(name = "getRoute")
public class GetRoute implements Runnable {

    @Parameters(index = "0", description = "Current workspace route name.", defaultValue = "theia")
    String name;

    @Inject
    CheWorkspaceService service;

    @Override
    public void run() {
        System.out.println(service.getRoute(name));
    }
}
