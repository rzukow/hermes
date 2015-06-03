package pl.allegro.tech.hermes.consumers.consumer.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.allegro.tech.hermes.common.util.ValueWithTimestamp;

import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

public class TimeoutedSendMessagesChecker implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(TimeoutedSendMessagesChecker.class);

    private static final long TIMEOUT = 5000;

    private final Queue<ValueWithTimestamp<CompletableFuture<MessageSendingResult>>> queue;

    public TimeoutedSendMessagesChecker(Queue<ValueWithTimestamp<CompletableFuture<MessageSendingResult>>> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                while (!queue.isEmpty()) {
                    ValueWithTimestamp<CompletableFuture<MessageSendingResult>> elem = queue.poll();
                    if (elem.age() < TIMEOUT) {
                        Thread.sleep(TIMEOUT);
                    }
                    if (!elem.getValue().isDone()) {
                        elem.getValue().complete(MessageSendingResult.loggedFailResult(new TimeoutException("Timeout after " + elem.age())));
                    }
                }
                Thread.sleep(TIMEOUT);
            }
        } catch (InterruptedException e) {
            logger.info("SendMessagesChecker stopped");
        }
    }
}
