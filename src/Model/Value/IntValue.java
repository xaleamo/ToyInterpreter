package Model.Value;
import Model.Type.*;

public class IntValue implements Value{
    private int value;

    public IntValue(int val){
        this.value = val;
    }
    public int getValue(){return value;}
    public void setValue(int val){this.value = val;}

    @Override
    public String toString(){return Integer.toString(value);}
    @Override
    public Type getType(){return new IntType(); }
}
