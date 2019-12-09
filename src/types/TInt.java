package types;

import value.TypeErrorException;

public class TInt implements IType {

    @Override
    public String getType() {
        return "I";
    }

    @Override
    public String toString() {
        return getType();
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj instanceof TInt)) {
            return true;
        }
        throw new TypeErrorException();
    }
}
