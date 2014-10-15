package com.resolve.qa.framework.common;

public class Log
{
    public static final String INDENT = new String("  ");
    private static StringBuilder log = new StringBuilder("");
    public static void clean() {
        log = new StringBuilder("");; 
    }
    public static void log(String newLog) {
        log.append(newLog).append('\n'); 
    }
    public static String out() {
        return log.toString(); 
    }
    public static String getIndent() {
        return INDENT; 
    }
}

