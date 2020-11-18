package org.eclipse.che.chedo;

import org.eclipse.che.chedo.commands.GetRoute;
import org.eclipse.che.chedo.commands.LsRoutes;
import org.eclipse.che.chedo.commands.Open;

import picocli.CommandLine;

import io.quarkus.picocli.runtime.annotations.TopCommand;

@TopCommand
@CommandLine.Command(subcommands = { GetRoute.class, LsRoutes.class, Open.class })
public class Chedo implements Runnable {
    @Override
    public void run() {
        System.out.println("Please run one of these sub commands: chedo getRoute -name=\"theia\"");
    }
}
