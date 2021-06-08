package org.jds.lambdas;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class TestPredicateInterface {
	
	    public static void main(String args[])
	    {
	  
	        List<String> hello =
	            Arrays.asList("Hello","world", "my", "friend!");
	  
	        Predicate<String> p = (s)->s.contains("l");
	  
	        for (String st:hello)
	        {
	            if (p.test(st))
	                System.out.println(st);
	        }
	    }
	}