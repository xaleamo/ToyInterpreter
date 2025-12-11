package model.program_state.ADTs;

public interface MyIDictionary <K,V>{
    V remove(K k);
    V add(K k, V v);

    boolean isDefined(K k);
    /**
     * Returns the value to which the key is mapped to. If this dictionary contains an entry for the specified key, the associated value is returned; otherwise, null is returned.
     @param k key
     @return the <b>value</b> k is mapped to, <b>null</b> otherwise


     */
    V lookUp(K k);
    MyIDictionary<K,V> clone();
}
