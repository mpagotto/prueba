package com.codapay.event;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class App {
    private static final Random rand = new Random();
    static Collection<Future<?>> futures = new LinkedList<>();
    static ExecutorService executor = Executors.newFixedThreadPool(1000);


    public static void main(String[] args) {
        final RedisHelper helper= RedisHelper.getInstance();
        for (int i = 0; i < 1000000; i++) {
            final int x = i;
            futures.add(executor.submit(new Runnable() {
                @Override public void run() {
                    String id=UUID.randomUUID().toString();
                    int seconds=rand.nextInt(100)+1;
                    helper.set(id,seconds);
                }
            }));
        }
        System.out.println("Listening...");
        helper.subscribe();
    }
}