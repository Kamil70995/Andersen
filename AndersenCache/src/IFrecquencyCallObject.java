import java.util.Set;

// алгоритм вытеснения
public interface IFrecquencyCallObject<K> {

        Set<K> getMostFrequentlyUsedKeys();

        int getFrecquencyOfCallingObject(K key);

}
