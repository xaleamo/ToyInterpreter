package model.program_state;

import model.program_state.ADTs.MyDictionary;
import model.program_state.ADTs.MyIDictionary;
import model.type.Type;
import model.value.Id;
import model.value.Value;

import java.util.Map;

public class TypeEnv extends MyDictionary<Id, Type> {
    @Override
    public TypeEnv clone() {
        TypeEnv newE=new TypeEnv();

        for (Map.Entry<Id, Type> e : map.entrySet()) {
            newE.map.put(e.getKey().clone(),e.getValue().clone());
        }
        return newE;
    }
}
