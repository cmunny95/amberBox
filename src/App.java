import java.util.concurrent.TimeUnit;

import cache.LruExpiringCache;

public class App {
    public static void main(String[] args) throws Exception {
        LruExpiringCache cache = new LruExpiringCache(1, 1);
        cache.remove(1);
        cache.add(1, 1);
        System.out.println(cache.get(1));
        cache.add(2, 2);
        System.out.println(cache.get(2));
        try {
            cache.get(1);
        } catch (Exception e) {
            System.out.println("No cache entry exists for key of 1");
        }
        TimeUnit.SECONDS.sleep(2);
        try {
            cache.get(2);
        } catch (Exception e) {
            System.out.println("No cache entry exists for key of 2");
        }
        cache.add(1, 1, 5);
        TimeUnit.SECONDS.sleep(2);
        try {
            System.out.println(cache.get(1));
        } catch (Exception e) {
            System.out.println("No cache entry exists for key of 1");
        }

        LruExpiringCache cache2 = new LruExpiringCache(30, 30);
        for(int i = 0; i < 30; i++) {
            cache2.add(i, "value " + String.valueOf(i));
        }
        try {
            System.out.println(cache2.get(0));
        } catch (Exception e) {
            System.out.println("No cache entry exists for key of 0");
        }
        cache2.add("new key", "new value");
        try {
            System.out.println(cache2.get(1));
        } catch (Exception e) {
            System.out.println("No cache entry exists for key of 1");
        }
        System.out.println(cache2.get("new key"));
    }
}
