package org.eclipse.che.chedo.commands;

import java.io.File;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.ClientEndpointConfig.Configurator;
import javax.websocket.ContainerProvider;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

import org.eclipse.che.chedo.model.JsonRpcContent;
import org.eclipse.che.chedo.model.JsonRpcMessage;
import org.eclipse.che.chedo.service.CheWorkspaceService;

import io.quarkus.runtime.annotations.RegisterForReflection;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

/**
 * Open a file in Theia
 */
@Command(name = "open")
public class Open implements Runnable {

    @Parameters(index = "0", description = "The file to open in Che-Theia.")
    private File file;

    @Inject
    CheWorkspaceService cheWorkspaceService;

    @Override
    public void run() {
        final String theiaRoute = cheWorkspaceService.getRoute("theia");
        ClientEndpointConfig config = ClientEndpointConfig.Builder.create().configurator(new Configurator() {
            @Override
            public void beforeRequest(Map<String, List<String>> headers) {
                headers.put("Origin", Arrays.asList(theiaRoute));
            }
        }).build();

        try (Session session = ContainerProvider.getWebSocketContainer().connectToServer(Client.class, config,
                new URI("ws://127.0.0.1:3100/services"))) {

            session.getAsyncRemote() //
                    .sendText(JsonRpcMessage //
                            .createOpenMessage(0, "/services/cli-endpoint") //
                            .toJson());

            String content = JsonRpcContent.createOpenFileMethodContent(0, file.getPath()).toJson();
            session.getAsyncRemote() //
                    .sendText(JsonRpcMessage //
                            .createDataMessage(0, "/services/cli-endpoint", content) //
                            .toJson());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @RegisterForReflection
    public static class Client extends Endpoint {

        public void onOpen(Session session, EndpointConfig config) {
        }

        public void onError(Session session, Throwable thr) {
            thr.printStackTrace();
        }

    }

}
