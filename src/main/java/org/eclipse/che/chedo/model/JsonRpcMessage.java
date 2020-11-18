package org.eclipse.che.chedo.model;

import javax.json.bind.JsonbBuilder;
import javax.xml.bind.annotation.XmlRootElement;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
@XmlRootElement
public class JsonRpcMessage {
    public String kind;
    public Integer id;
    public String path;
    public String data;
    public String content;

    public static JsonRpcMessage createOpenMessage(Integer id, String path) {
        JsonRpcMessage message = new JsonRpcMessage();
        message.kind = "open";
        message.id = id;
        message.path = path;
        return message;
    }

    public static JsonRpcMessage createDataMessage(Integer id, String path, String content) {
        JsonRpcMessage message = new JsonRpcMessage();
        message.kind = "data";
        message.id = id;
        message.path = path;
        message.content = content;
        return message;
    }

    public String toJson() {
        return JsonbBuilder.create().toJson(this);
    }
}