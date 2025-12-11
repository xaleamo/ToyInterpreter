package model.program_state.ADTs;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface IHashMapHeap<K,V> {
    V delete(K k);
    Integer add(V v);

    V update(Integer k, V v);

    boolean isDefined(K k);

    V lookUp(K k);

    Set<Map.Entry<K, V>> entrySet();

    HashMap<Integer, V> getContent();

    void setContent(Map<Integer, V> aux);
}
