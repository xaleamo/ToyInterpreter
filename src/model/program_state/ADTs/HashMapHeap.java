package model.program_state.ADTs;

import model.value.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HashMapHeap<V extends Value> implements IHashMapHeap<Integer,V> {
    protected HashMap<Integer,V> map=new HashMap<>();
    private int address;
    public HashMapHeap(){
        address=0;
    }

    @Override
    public V delete(Integer k) {
        return map.remove(k);
    }

    /**
     * Associates the specified value with the specified key in this map.
     * @param v value
     * @return address where we added v
     */
    @Override
    public Integer add(V v) {
        address++;
        map.put(address,v);
        return address;
    }

    /**
     * Associates the specified value with the specified key in this map. If the map previously contained a mapping for the key, the old value is replaced.
     * @param k->address
     * @param v->new value
     * @return previous value
     */
    @Override
    public V update(Integer k, V v){
        return map.put(k,v);
    }

    @Override
    public boolean isDefined(Integer k) {
        return map.containsKey(k);
    }
    /**
     * Returns the value to which the key is mapped to. If this dictionary contains an entry for the specified key, the associated value is returned; otherwise, null is returned.
     @param k key
     @return the <b>value</b> k is mapped to, <b>null</b> otherwise


     */
    @Override
    public V lookUp(Integer k) {
        return map.get(k);
    }

    @Override
    public Set<Map.Entry<Integer,V>> entrySet(){
        return map.entrySet();
    }

    @Override
    public HashMap<Integer,V> getContent() {
        return map;
    }
    @Override
    public void setContent(Map<Integer, V> aux){
        map.clear();
        for(Map.Entry<Integer,V> entry:aux.entrySet()){
            map.put(entry.getKey(),(V)entry.getValue().clone());//how else??
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<Integer,V> e:map.entrySet()){
            sb.append(e.getKey().toString()).append(" -> ").append(e.getValue().toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
