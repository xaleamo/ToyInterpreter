package model.type;

import model.value.IntValue;
import model.value.Value;

public class IntType implements Type{
//?? static? there is no difference between type classes
    private static final IntValue defaultValue = new IntValue(0);

    @Override
    public IntType clone() {return new IntType();}

    @Override
    public Value defaultValue(){
        return defaultValue;
    }

    @Override
    public boolean equals(Object other){
        return (other instanceof IntType);
    }

    @Override
    public String toString(){
        return "IntType";
    }
}
