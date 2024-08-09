package cache;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LruExpiringCache extends LruCache {
  private long max_age_seconds = 30;
  private Map<Object, Long> expirations = new ConcurrentHashMap<>();

  public LruExpiringCache(long max_size, long max_age_seconds) {
    this.max_size = max_size;
    this.max_age_seconds = max_age_seconds;
    initialize();
  }

  public LruExpiringCache(long max_age_seconds) {
    this.max_age_seconds = max_age_seconds;
    initialize();
  }

  public LruExpiringCache() {
    initialize();
  }

  private void initialize() {
    new ExpirationChecker().start();
  }

  public Object add(Object key, Object value, long max_age_seconds) {
    expirations.put(key, new Date().getTime() + max_age_seconds * 1000);
    return super.add(key, value);
  }

  @Override
  public Object add(Object key, Object value) {
    return add(key, value, max_age_seconds);
  }

  @Override
  public Object remove(Object key) {
    expirations.remove(key);
    return super.remove(key);
  }

  @Override
  public Object get(Object key) throws Exception {
    if(new Date().getTime() > expirations.get(key))
      remove(key);
    
    return super.get(key);
  }

  private class ExpirationChecker extends Thread {
    public ExpirationChecker() {}

    @Override
    public void run() {
      expireEntries();
      try {
        sleep(1000);
      } catch(Exception ex) {
        ex.printStackTrace();
      }
    }

    private void expireEntries() {
      long currentTime = new Date().getTime();
      for(Map.Entry<Object, Long> entry : expirations.entrySet()){
        if(currentTime >= entry.getValue()){
          remove(entry.getKey());
        }
      }
    }
  }
}
