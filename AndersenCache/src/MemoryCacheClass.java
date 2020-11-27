import java.io.*;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

public class MemoryCacheClass<K, V extends Serializable> implements ICache<K, V>, IFrecquencyCallObject<K> {

    HashMap<K,String> hashMap;
    TreeMap<K,Integer> frequencyMap;

    public MemoryCacheClass(HashMap<K, String> hashMap, TreeMap<K, Integer> frequencyMap) {
        this.hashMap = new HashMap<K, String>();
        this.frequencyMap = new TreeMap<K, Integer>();

        File tempFolder = new File("temp\\");
        if(!tempFolder.exists()){
            tempFolder.mkdirs();
        }
    }
    // UUID - для получения уникального имени для хранения на жестком диске в temp
    @Override
    public void cache(K key, V value) throws IOException, ClassNotFoundException {

        String pathToObject;
        pathToObject = "temp\\" + UUID.randomUUID().toString() + ".temp";

        frequencyMap.put(key,1);
        hashMap.put(key, pathToObject);


        FileOutputStream fileStream = new FileOutputStream(pathToObject);
        ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);

        objectStream.writeObject(value);
        objectStream.flush();
        objectStream.close();
        fileStream.flush();
        fileStream.close();
    }

    @Override
    public V getObject(K key) throws IOException, ClassNotFoundException {
        // получаем объект по ключу хэш мап
        if(hashMap.containsKey(key)){

            String pathToObject = hashMap.get(key);


            try
            {
                FileInputStream fileStream = new FileInputStream(pathToObject);
                ObjectInputStream objectStream = new ObjectInputStream(fileStream);


                V deserializedObject =  (V)objectStream.readObject();

                int frecquency = frequencyMap.remove(key);
                // увеличиваем на 1 частоту вызова
                frequencyMap.put(key,++frecquency);


                fileStream.close();
                objectStream.close();

                return deserializedObject;
            }
            catch (IOException ex)
            {
                return null;
            }
            catch (ClassNotFoundException ex)
            {
                return null;
            }
        }

        return null;
    }

    @Override
    public void deleteObject(K key) {

        if(hashMap.containsKey(key))
        {
            File deletingFile = new File(hashMap.remove(key));
            frequencyMap.remove(key);
            deletingFile.delete();
        }
    }

    @Override
    public void clearCache() {

        for(K key : hashMap.keySet()){
            File deletingFile = new File(hashMap.get(key));
            deletingFile.delete();
        }

        hashMap.clear();
        frequencyMap.clear();

    }

    @Override
    public V removeObject(K key) throws IOException, ClassNotFoundException {
        if(hashMap.containsKey(key))
        {
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
    public int getFrecquencyOfCallingObject(K key) {
        if(hashMap.containsKey(key)){
            return frequencyMap.get(key);
        }
        return  0;
    }
}
