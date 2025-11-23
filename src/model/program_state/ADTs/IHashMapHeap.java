package model.program_state.ADTs;

public interface IHashMapHeap<K,V> {
    V delete(K k);
    Integer add(V v);

    V update(Integer k, V v);

    boolean isDefined(K k);

    V lookUp(K k);
}
