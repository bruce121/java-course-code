package com.bruce121._11cache;

import com.bruce121._11cache.service.RedisService;
import org.redisson.api.RLock;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;
import java.util.concurrent.*;

import static java.lang.String.format;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RedisService redisService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // 1.分布式锁
        distributeLock();

        // 2.计数器
        redisCounter();

        // 3.pubsub
        // https://github.com/redisson/redisson/blob/master/redisson/src/test/java/org/redisson/RedissonTopicTest.java
        pubsubExample();
    }

    private void pubsubExample() throws ExecutionException, InterruptedException {

        RTopic topic = redissonClient.getTopic("PUBSUB-test");
        topic.addListener(String.class, new MessageListener<String>() {
            @Override
            public void onMessage(CharSequence channel, String msg) {
                System.out.println(format("Channel: [%s], Message: [%s]", channel.toString(), msg));
            }
        });

        for (int i = 0; i < 10; i++) {
            topic.publish(UUID.randomUUID().toString());
        }
    }

    private void redisCounter() {
        ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>();

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        int times = 10000;
        CountDownLatch latch = new CountDownLatch(times);

        for (int i = 0; i < times; i++) {
            executorService.execute(() -> {
                try {
                    long count = redisService.incr("redisCounter");
                    map.put(count, count);
                } finally {
                    latch.countDown();
                }
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assert map.size() == 100000;
        System.out.println("map size() = " + map.size());
    }

    private void distributeLock() {
        final String lockKey = "user_id:order_id:lalala";
        RLock lock = redissonClient.getLock(lockKey);

        try {
            lock.lock(30, TimeUnit.SECONDS);
            // boolean getLock = lock.tryLock(5, 30, TimeUnit.SECONDS);

            doSomething();
        } finally {
            releaseLock(lock);
        }
    }

    private void releaseLock(RLock lock) {
        // 释放锁之前判断当前的锁是否被当前线程所持有
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

    private void doSomething() {
        System.out.println("do something().");
    }
}
