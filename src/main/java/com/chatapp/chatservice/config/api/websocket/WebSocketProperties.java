package com.chatapp.chatservice.config.api.websocket;

public class WebSocketProperties {


    public static final String ROOT_CHANNEL = "/topic/";


    public static class Rooms {
        public static final String ROOT = "rooms";

        public static class Messages {

            public static final String ROOT = "messages";

            public static final String SEND = ".send";

            public static final String DELETE = ".delete";

            public static final String UPDATE = ".update";

        }
    }

}
