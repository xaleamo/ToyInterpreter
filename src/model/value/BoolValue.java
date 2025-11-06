package model.value;

import model.type.BoolType;
import model.type.Type;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoolValue val = (BoolValue) o;
        return this.value==val.value;
    }
}
