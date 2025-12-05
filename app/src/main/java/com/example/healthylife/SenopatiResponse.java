package com.example.healthylife;

public class SenopatiResponse {
    private boolean success;
    private String error;
    private Data data;

    public boolean isSuccess() { return success; }
    public String getError() { return error; }
    public Data getData() { return data; }

    public static class Data {
        private String reply;

        public String getReply() { return reply; }
    }
}

