package model.program_state.ADTs;

import my_exceptions.MyException;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is a wrapper for HashMap
 * Should throw no exceptions.
 * @param <K>
 * @param <V>
 */
public class MyDictionary<K,V> implements MyIDictionary<K,V>{

    protected HashMap<K,V> map=new HashMap<>();

    @Override
    public  V remove(K k) {
        return map.remove(k);
    }

    /**
     * Associates the specified value with the specified key in this map. If the map previously contained a mapping for the key, the old value is replaced and returned.
     * @param k key
     * @param v value
     * @return old value or null
     */
    @Override
    public V add(K k, V v) {
        return map.put(k,v);
    }

    @Override
    public boolean isDefined(K k) {
        return map.containsKey(k);
    }

    @Override
    public V lookUp(K k) {
        return map.get(k);
    }

    @Override
    public MyIDictionary<K, V> clone(){throw new MyException("Can't clone MyDictionary. Try not using a template.");
    } ;



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<K,V> e:map.entrySet()){
            sb.append(e.getKey().toString()).append(" -> ").append(e.getValue().toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public Map<K, V> getContent() {
        return map;
    }
}
