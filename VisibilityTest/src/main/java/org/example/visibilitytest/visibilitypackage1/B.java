package org.example.visibilitytest.visibilitypackage1;

public class B {
    public void classAFields(A a) {
        int publicField = a.publicField;
        int protectedField = a.protectedField;
        //int privateField = a.privateField; not visible
        int packageField = a.packageField;

        int privateField = a.getPrivateField();
    }
}
