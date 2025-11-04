package Model.Type;

public class BoolType implements Type {
    @Override
    public boolean equals(Object other){
        return other instanceof BoolType;
    }
    @Override
    public String toString(){
        return "BoolType";
    }
}
