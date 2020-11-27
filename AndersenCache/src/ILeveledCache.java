import java.io.IOException;

    public interface ILeveledCache<K,V> extends ICache<K,V>, IFrecquencyCallObject<K>{

        void recache() throws IOException, ClassNotFoundException;

    }

