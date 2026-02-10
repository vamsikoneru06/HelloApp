import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class RateLimiter {

    private static final int MAX_ATTEMPTS = 5;
    private static final long WINDOW_SECONDS = 60;

    private static Map<String, Integer> attempts = new HashMap<>();
    private static Map<String, Instant> timestamps = new HashMap<>();

    public static boolean allowAttempt(String user) {
        Instant now = Instant.now();
        timestamps.putIfAbsent(user, now);

        if (now.minusSeconds(WINDOW_SECONDS).isAfter(timestamps.get(user))) {
            attempts.put(user, 0);
            timestamps.put(user, now);
        }

        attempts.put(user, attempts.getOrDefault(user, 0) + 1);
        return attempts.get(user) <= MAX_ATTEMPTS;
    }
}

