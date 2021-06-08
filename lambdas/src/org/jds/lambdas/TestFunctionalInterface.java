package org.jds.lambdas;

public class TestFunctionalInterface {
	
    public static void main(String args[])
    {
	    int a = 2;
	    
	    AnnotatedFunctionalInterfaceTest s = (int x)->x*x;
	
	    int ans = s.function1(a);
	    System.out.println(ans);
    }
}
