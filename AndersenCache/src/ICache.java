import java.io.IOException;

public interface ICache<K, V> {

        void cache(K key, V value) throws IOException, ClassNotFoundException;
        V getObject(K key) throws IOException, ClassNotFoundException;

        void  deleteObject(K key);

        void clearCache();

        V removeObject(K key) throws IOException, ClassNotFoundException;

        boolean containsKey(K key);

        int size();

}
