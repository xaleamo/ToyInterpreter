package model.value;
import model.type.*;

public class IntValue implements Value{
    private int value;

    public IntValue(int val){
        this.value = val;
    }

    @Override
    public IntValue clone(){
        return new IntValue(value);
    }

    public int getValue(){return value;}
    public void setValue(int val){this.value = val;}

    @Override
    public String toString(){return Integer.toString(value);}
    @Override
    public Type getType(){return new IntType(); }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntValue val = (IntValue) o;
        return this.value==val.value;
    }
}
