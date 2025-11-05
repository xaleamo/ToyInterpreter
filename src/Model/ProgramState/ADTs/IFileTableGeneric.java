package Model.ProgramState.ADTs;

public interface IFileTableGeneric<K,V> {
    public V add(K k,V v);
    public V remove(K k);
}
