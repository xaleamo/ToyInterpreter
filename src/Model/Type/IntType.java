package Model.Type;

public class IntType implements Type{
//?? static? there is no difference between type classes
    @Override
    public boolean equals(Object other){
        return (other instanceof IntType);
    }

    @Override
    public String toString(){
        return "IntType";
    }
}
