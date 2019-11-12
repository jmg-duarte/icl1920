package value;

public class TypeErrorException extends RuntimeException {

    public TypeErrorException(){
        super("illegal arguments");
    }
}
