package compiler;

import types.TFun;

public class ClosureInterface extends Interface {

    public ClosureInterface(TFun type) {
        super("closure_interface_type_" + type.getClosureType());
        this.addMethod("call" + type.getCallType());
    }

    private static String getClosureInterfaceName(String name) {
        return "closure_interface_type_" + name;
    }

    public String getClosureTypeStr() {
        return this.interfaceName;
    }

    public String getCallTypeStr() {
        return this.methods.get(0);
    }

}
