package com.example.advanceSuggest;

public class NativeTest {

    static {
        System.loadLibrary("native-lib");

    }


    public static native String getString();
}
