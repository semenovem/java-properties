package ru.vtb.afsc.env;

public final class AppEnvHolder extends EnvHolder {

    @Spec(extName = "ENV_HOSTNAME", optional = false)
    public static final String HOSTNAME = null;
//
//    @Spec(extName = "ENV_HOSTNAME_BOOLEAN", optional = true)
//    public static final boolean HOSTNAMEb = true;
//
//
//    @Spec(extName = "ENV_HOSTNAME_INT", optional = true)
//    public static final int HOSTNAMEint = 234;
//
//
//    @Spec(extName = "ENV_HOSTNAME_LONG", optional = true)
//    public static final long HOSTNAMElong = 2342345245L;
//
//
//    @Spec(extName = "ENV_HOSTNAME_LONG_OBJ", optional = true)
//    public static final java.lang.Long HOSTNAMElongObj = 2342345245L;


    @Spec(extName = "ENV_H_BYTE", optional = true)
    public static final byte H_BYTE = 0;


    @Spec(extName = "ENV_H_SHORT", optional = true)
    public static final short H_SHORT = 0;


    @Spec(extName = "ENV_H_FLOAT", optional = true, sensitive = true)
    public static final float H_FLOAT = 0;


    @Spec(extName = "ENV_H_DOUBLE", optional = true)
    public static final double H_DOUBLE = 0;
}
