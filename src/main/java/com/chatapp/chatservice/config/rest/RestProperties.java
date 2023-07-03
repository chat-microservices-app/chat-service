package com.chatapp.chatservice.config.rest;


public final class RestProperties {

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String ROOT = "/api";

    public static class CHATS {
        public static final String ROOT = "/chats";
    }


    public static class AUTH {
        public static final String ROOT = "/auth";
        public static final String CHECK_TOKEN = "/check-token";
    }

}
