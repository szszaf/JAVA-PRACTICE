package org.example.visibilitytest.visibilitypackage2;

import org.example.visibilitytest.visibilitypackage1.A;

public class C {
    public void classAFields(A a) {
        int publicField = a.publicField;
        //int protectedField = a.protectedField; not visible
        //int privateField = a.privateField; not visible
        //nt packageField = a.packageField; not visible

        int protectedField = a.getProtectedField();
        int privateField = a.getPrivateField();
        int packageField = a.getPackageField();
    }
}
