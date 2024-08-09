package cache;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CacheTest {
    protected Cache cache;

    protected void setUp(){
      cache = new Cache();
    }

    @Test
    public void testAdd() {
      assertEquals(2, cache.add(1,2));
      try {
        assertEquals(2, cache.get(1));
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    @Test
    public void testGet() {

    }

    @Test
    public void testRemove() {

    }
}
