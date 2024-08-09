package cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
  protected Map<Object, Object> entries = new ConcurrentHashMap<>();
  
  public Cache() { 
    // default constructor 
  }

  public Object add(Object key, Object value) {
    return entries.put(key, value);
  }

  public Object remove(Object key) {
    return entries.remove(key);
  }

  public Object get(Object key) throws Exception {
    if(entries.containsKey(key))
      return entries.get(key);

    throw new Exception();
  }
}
