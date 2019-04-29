package com.qipai.gansuactivity;

public class RSAEntity {


    /**
     * modulus : 00916baea024399caea6566f1350b1181de23bec451f754127faf9651a7892da9 dafb3a9aaec992b0b9428dd3772e7607c0c5e8447d1d8588c628f2cca136589967 6f3a50c41850f631193de75de600c54e628b2bbfcbb6e1a27090799d3412cc7bf5 7a87429f1feeb503155fdaeda8a3f588386a781d8fdce6d5abb50eb579491
     * exponent : 010001
     */

    private String modulus;
    private String exponent;

    public String getModulus() {
        return modulus;
    }

    public void setModulus(String modulus) {
        this.modulus = modulus;
    }

    public String getExponent() {
        return exponent;
    }

    public void setExponent(String exponent) {
        this.exponent = exponent;
    }

    @Override
    public String toString() {
        return "RSAEntity{" +
                "modulus='" + modulus + '\'' +
                ", exponent='" + exponent + '\'' +
                '}';
    }
}
