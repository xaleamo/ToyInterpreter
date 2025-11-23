package model.type;

import model.value.BoolValue;
import model.value.Value;

public class BoolType implements Type {
    private static final BoolValue defaultValue=new BoolValue(false);

    @Override
    public BoolType clone() {return new BoolType();}

    @Override
    public Value defaultValue() {
        return defaultValue;
    }
    @Override
    public boolean equals(Object other){
        return other instanceof BoolType;
    }
    @Override
    public String toString(){
        return "BoolType";
    }
}
