package cache;

import java.util.concurrent.CopyOnWriteArrayList;

public class LruCache extends Cache {
  protected long max_size = 1000000;
  private CopyOnWriteArrayList<Object> lru = new CopyOnWriteArrayList<>();

  public LruCache(long max_size) {
    this.max_size = max_size;
  }

  public LruCache() {

  }

  @Override
  public Object add(Object key, Object value) {
    if(entries.size() >= max_size)
      removeLRU();
    
    lru.add(key);
    return super.add(key, value);
  }

  @Override
  public Object remove(Object key) {
    lru.remove(key);
    return super.remove(key);
  }

  @Override
  public Object get(Object key) throws Exception {
    Object retVal;
    try {
      retVal = super.get(key);
    } catch (Exception ex) {
      lru.remove(key);
      throw ex;
    }

    lru.remove(key);
    lru.add(key);
    return retVal;
  }

  private void removeLRU() {
    Object key = lru.remove(0);
    super.remove(key);
  }
}
