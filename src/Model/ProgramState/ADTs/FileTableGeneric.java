package Model.ProgramState.ADTs;

import MyExceptions.MyException;

import java.util.Hashtable;

public class FileTableGeneric<K,V> implements IFileTableGeneric<K,V>{

    Hashtable<K,V> table=new Hashtable<>();

    /**
     *
     * @param k key
     * @param v value
     * @return previous value or null if key did not exist
     * @throws
     */
    @Override
    public V add(K k,V v) {
        if(k==null || v==null) throw new MyException("K or V is null");
        return table.put(k,v);
    }
    @Override
    public V remove(K k) {
        if(k==null) throw new MyException("K is null");
        return table.remove(k);
    }
}
