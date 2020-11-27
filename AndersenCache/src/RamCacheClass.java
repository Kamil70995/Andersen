import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

public class RamCacheClass<K, V> implements ICache<K, V>, IFrecquencyCallObject {

    // будет происходить кэширование
    private HashMap<K,V> hashMap;
    //частота ключей
    private TreeMap<K, Integer> frequencyMap;


    public RamCacheClass(){
        hashMap = new HashMap<K, V>();
        frequencyMap = new TreeMap<K, Integer>();
    }

    @Override
    public void cache(K key, V value) throws IOException, ClassNotFoundException {
        frequencyMap.put(key,1);
        hashMap.put(key, value);
    }

    @Override
    public V getObject(K key) throws IOException, ClassNotFoundException {
        if(hashMap.containsKey(key)){
            int frequency = frequencyMap.get(key);
            frequencyMap.put(key,++frequency);
            return hashMap.get(key);
        }


        return null;
    }

    @Override
    public void deleteObject(K key) {

        if(hashMap.containsKey(key)){
            hashMap.remove(key);
            frequencyMap.remove(key);
        }
    }

    @Override
    public void clearCache() {

        hashMap.clear();
        frequencyMap.clear();
    }

    @Override
    public V removeObject(K key) throws IOException, ClassNotFoundException {
        if(hashMap.containsKey(key)){
            V result = this.getObject(key);
            this.deleteObject(key);
            return result;
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return hashMap.containsKey(key);
    }

    @Override
    public int size() {
        return hashMap.size();
    }

    @Override
    public Set<K> getMostFrequentlyUsedKeys() {
        ComparatorClass comparator = new ComparatorClass(frequencyMap);
        TreeMap<K,Integer> sorted = new TreeMap(comparator);
        sorted.putAll(frequencyMap);
        return sorted.keySet();
    }

    @Override
    public int getFrecquencyOfCallingObject(Object key) {
        if(hashMap.containsKey(key)){
            return frequencyMap.get(key);
        }
        return 0;
    }
}
