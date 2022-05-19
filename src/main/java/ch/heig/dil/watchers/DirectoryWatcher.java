package ch.heig.dil.watchers;

import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/** Source: https://www.waitingforcode.com/java-i-o/watching-files-with-watchservice/read */
public class DirectoryWatcher implements Runnable {

    private final WatchService watchService;

    private final CountDownLatch countDownLatch;

    private boolean invalidKey;

    private final Map<WatchEvent.Kind<Path>, Integer> stats =
            new HashMap<>(
                    Map.of(
                            StandardWatchEventKinds.ENTRY_CREATE,
                            0,
                            StandardWatchEventKinds.ENTRY_MODIFY,
                            0,
                            StandardWatchEventKinds.ENTRY_DELETE,
                            0));

    public DirectoryWatcher(WatchService watchService, CountDownLatch countDownLatch) {
        this.watchService = watchService;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        while (true) {
            WatchKey watchKey;
            try {
                //                watchKey = watchService.poll(1, TimeUnit.SECONDS);
                watchKey = watchService.take();
                System.out.println(watchKey);
                if (watchKey != null) {
                    for (WatchEvent<?> event : watchKey.pollEvents()) {
                        stats.put(
                                (WatchEvent.Kind<Path>) event.kind(), stats.get(event.kind()) + 1);
                        System.out.println("FILE CHANGED");
                    }
                    // is mandatory, otherwise it blocks further notifications for the file
                    // (example: edit the same file twice,
                    // without reset() only the first change will be notified)
                    if (!watchKey.reset()) {
                        invalidKey = true;
                        // check if reset was correctly made, otherwise it can mean that the
                        // directory is not accessible anymore
                        countDownLatch.countDown();
                        break;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                countDownLatch.countDown();
            }
        }
    }

    public boolean wasInvalidKey() {
        return invalidKey;
    }

    public int getCreates() {
        return stats.get(StandardWatchEventKinds.ENTRY_CREATE);
    }

    public int getModifications() {
        return stats.get(StandardWatchEventKinds.ENTRY_MODIFY);
    }

    public int getDeletes() {
        return stats.get(StandardWatchEventKinds.ENTRY_DELETE);
    }
}
