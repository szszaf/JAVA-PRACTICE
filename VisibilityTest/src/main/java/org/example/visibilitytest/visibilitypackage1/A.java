package org.example.visibilitytest.visibilitypackage1;

public class A {
    public final int publicField = 0;
    protected final int protectedField = 1;
    private final int privateField = 2;
    final int packageField = 3;

    public int getProtectedField() {
        return protectedField;
    }

    public int getPrivateField() {
        return privateField;
    }

    public int getPackageField() {
        return packageField;
    }
}
