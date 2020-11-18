package org.eclipse.che.chedo.model;

import javax.json.bind.JsonbBuilder;
import javax.xml.bind.annotation.XmlRootElement;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
@XmlRootElement
public class JsonRpcContent {

    public String jsonrpc = "2.0";
    public Integer id;
    public String method;
    public String params;

    public static JsonRpcContent createOpenFileMethodContent(Integer id, String params) {
        JsonRpcContent message = new JsonRpcContent();
        message.method = "openFile";
        message.id = id;
        message.params = params;
        return message;
    }

    public String toJson() {
        return JsonbBuilder.create().toJson(this);
    }
}