package com.util;


import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class UserSlidingWindow {

    static Logger logger = Logger.getLogger(UserSlidingWindow.class.getName());

    private static volatile Map<String, SlidingWindowRateLimiter> bucket;

    public static void populateBucketWithRequests (String id) {
        if (bucket == null) {
            synchronized (DBUtil.class) {
                if (bucket == null) {
                    bucket = new HashMap<>();
                }
                bucket.putIfAbsent(id, new SlidingWindowRateLimiter(1, 10));
            }
        }
    }

    public static boolean accessApplication(String id) {
        if (bucket.get(id).grantAccess()) {
            logger.info("Id: " + id + " able to access");
            return true;
        }
        logger.info("Id: " + id + " too many requests");
        return false;
    }
}
