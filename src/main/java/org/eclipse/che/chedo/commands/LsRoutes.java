package org.eclipse.che.chedo.commands;

import javax.inject.Inject;

import org.eclipse.che.chedo.service.CheWorkspaceService;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name="lsRoutes")
public class LsRoutes implements Runnable {

    @Option(names = { "-u", "--url"}, description = "Display url")
    private boolean showUrl;

    @Inject
    CheWorkspaceService service;

    @Override
    public void run() {
        service.displayRoutes(showUrl);
	}

}
