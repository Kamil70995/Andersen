import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class TwoLeveledCacheClass<K, V extends Serializable> extends Object implements ILeveledCache<K, V> {

    RamCacheClass<K, V> ramCache;
    MemoryCacheClass<K, V> memoryCache;
    int maxRamCacheCapacity;
    int numberOfRequests;
    int numberOfRequestsForRecahce;

    public TwoLeveledCacheClass(int _maxRamCacheCapacity, int _numberOfRequestsForRecache) {
        maxRamCacheCapacity = _maxRamCacheCapacity;
        numberOfRequestsForRecahce = _numberOfRequestsForRecache;
        ramCache = new RamCacheClass<K, V>();
        memoryCache = new MemoryCacheClass<K, V>(new HashMap<>(), new TreeMap<>());
        numberOfRequests = 0;
    }


    @Override
    public void cache(K key, V value) throws IOException, ClassNotFoundException {
        ramCache.cache(key, value);
    }


    @Override
    public V getObject(K key) throws IOException, ClassNotFoundException {
        if (ramCache.containsKey(key)) {
            numberOfRequests++;
            if (numberOfRequests > numberOfRequestsForRecahce) {
                this.recache();
                numberOfRequests = 0;
            }
            return ramCache.getObject(key);
        }
        if (memoryCache.containsKey(key)) {
            numberOfRequests++;
            if (numberOfRequests > numberOfRequestsForRecahce) {
                this.recache();
                numberOfRequests = 0;
            }
            return memoryCache.getObject(key);
        }
        return null;
    }


    @Override
    public void deleteObject(K key) {
        if (ramCache.containsKey(key)) {
            ramCache.deleteObject(key);
        }
        if (memoryCache.containsKey(key)) {
            memoryCache.deleteObject(key);
        }
    }


    @Override
    public void clearCache() {
        memoryCache.clearCache();
        ramCache.clearCache();
    }


    @Override
    public V removeObject(K key) throws IOException, ClassNotFoundException {
        if (ramCache.containsKey(key)) {
            return ramCache.removeObject(key);
        }
        if (memoryCache.containsKey(key)) {
            return memoryCache.removeObject(key);
        }
        return null;
    }


    @Override
    public boolean containsKey(K key) {
        if (ramCache.containsKey(key)) {
            return true;
        }
        if (memoryCache.containsKey(key)) {
            return true;
        }
        return false;
    }


    @Override
    public int size() {
        return ramCache.size() + memoryCache.size();
    }


    @Override
    public void recache() throws IOException, ClassNotFoundException {
        TreeSet<K> ramKeySet = new TreeSet<K>(ramCache.getMostFrequentlyUsedKeys());
        int boundFrecquency = 0;


        // вычисление среднего арифметического для отбрасывания редко вызываемых объектов
        for (K key : ramKeySet) {
            boundFrecquency += ramCache.getFrecquencyOfCallingObject(key);
        }
        boundFrecquency /= ramKeySet.size();


        for (K key : ramKeySet) {
            if (ramCache.getFrecquencyOfCallingObject(key) <= boundFrecquency) {
                memoryCache.cache(key, ramCache.removeObject(key));
            }
        }

        TreeSet<K> memoryKeySet = new TreeSet<K>(memoryCache.getMostFrequentlyUsedKeys());
        for (K key : memoryKeySet) {
            try {
                if (memoryCache.getFrecquencyOfCallingObject(key) > boundFrecquency) {
                    ramCache.cache(key, memoryCache.removeObject(key));
                }
            } catch (IOException ex) {
                memoryCache.deleteObject(key);
                continue;
            } catch (ClassNotFoundException ex) {
                // simply dummy
                continue;
            }
        }
    }


    @Override
    public Set<K> getMostFrequentlyUsedKeys() {
        TreeSet<K> set = new TreeSet<K>(ramCache.getMostFrequentlyUsedKeys());
        set.addAll(memoryCache.getMostFrequentlyUsedKeys());
        return set;
    }


    @Override
    public int getFrecquencyOfCallingObject(K key) {
        if (ramCache.containsKey(key)) {
            return ramCache.getFrecquencyOfCallingObject(key);
        }
        if (memoryCache.containsKey(key)) {
            return memoryCache.getFrecquencyOfCallingObject(key);
        }
        return 0;
    }
}
