package model.program_state.ADTs;

import java.util.Hashtable;

public interface IFileTableGeneric<K,V> {
    V add(K k,V v);
    void remove(K k);
    boolean isDefined(K k);
    V lookUp(K k);

    Hashtable<K, V> getContent();
}
