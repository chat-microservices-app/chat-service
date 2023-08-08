package com.chatapp.chatservice.config.api.rest;


public final class RestProperties {

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String ROOT = "/api";

    public static class CHATS {
        public static final String ROOT = "/chats";

        public static class ROOM {
            public static final String ROOT = "/rooms";

            public static final String PUBLIC = "/public";

            public static final String JOIN = "/join";
        }

        public static class MESSAGE {
            public static final String ROOT = "/messages";
        }


    }


    public static class AUTH {
        public static final String ROOT = "/auth";
        public static final String CHECK_TOKEN = "/check-token";
    }

}
