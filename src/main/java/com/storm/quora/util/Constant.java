package com.storm.quora.util;

public class Constant {
    public final static String CACHE_URI = "redis://:topica_123@171.244.3.242:6379";
    public static final String UP_VOTE_QUESTION_CACHE_FORMAT = "vote:question:%s:up";
    public static final String DOWN_VOTE_QUESTION_CACHE_FORMAT = "vote:question:%s:down";
    public static final String UP_VOTE_ANSWER_CACHE_FORMAT = "vote:answer:%s:up";
    public static final String DOWN_VOTE_ANSWER_CACHE_FORMAT = "vote:answer:%s:down";
}
