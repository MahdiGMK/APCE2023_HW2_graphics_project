package com.mahdigmk.apaa.Controller;


import java.util.HashMap;

public class ControllerResponse {
    public static final ControllerResponse SUCCESS = new ControllerResponse(0, "Success");
    private final int errorCode;
    private final HashMap<String, Object> output;

    public ControllerResponse(int errorCode, String responseMessage) {
        this.errorCode = errorCode;
        output = new HashMap<>(1);
        output.put("response message", responseMessage);
    }

    public ControllerResponse(HashMap<String, Object> output) {
        errorCode = 0;
        this.output = new HashMap<>(output);
    }

    public String getResponseMessage() {
        if (output.get("response message") instanceof String message) return message;
        return null;
    }

    public Object getOutput(String propertyName) {
        return output.get(propertyName);
    }
}
