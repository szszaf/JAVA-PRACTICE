package org.example.visibilitytest.visibilitypackage2;

import org.example.visibilitytest.visibilitypackage1.A;

public class D extends A {
    public void classAFields(A a) {
        int publicField = a.publicField;
        //int protectedField = a.protectedField; not visible
        //int privateField = a.privateField;
        //int packageField = a.packageField;

        int inheritedField = protectedField;

        int protectedField = a.getProtectedField();
        int privateField = a.getPrivateField();
        int packageField = a.getPackageField();
    }
}
