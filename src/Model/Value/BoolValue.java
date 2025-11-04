package Model.Value;

import Model.Type.BoolType;
import Model.Type.Type;

public class BoolValue implements Value{
    private boolean value;

    public BoolValue(boolean val){
        this.value = val;
    }
    public boolean getValue(){return value;}
    public void setValue(boolean val){this.value = val;}

    @Override
    public String toString(){return Boolean.toString(value);}
    @Override
    public Type getType(){return new BoolType(); }
}
