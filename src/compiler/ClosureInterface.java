package compiler;

import types.IType;
import types.TFun;

public class ClosureInterface extends Interface {

    private final IType returnType;

    public ClosureInterface(TFun type) {
        super("closure_interface_type_" + type.getClosureType());
        this.addMethod("call" + type.getCallType());
        this.returnType = type.getType();
    }

    public IType getReturnType() {
        return returnType;
    }

    public String getClosureTypeStr() {
        return this.interfaceName;
    }

    public String getCallTypeStr() {
        return this.methods.get(0);
    }

}
