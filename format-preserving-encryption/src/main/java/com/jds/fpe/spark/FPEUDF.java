package com.jds.fpe.spark;

import org.apache.spark.sql.api.java.UDF1;

public class FPEUDF implements UDF1<String, String> {
	
    @Override
    public String call(String arg) throws Exception {
        if (validateString(arg))
            return arg;
        return "INVALID";
    }

	public boolean validateString(String arg) {
	    if (arg == null | arg.length() != 11)
	        return false;
	    else
	        return true;
	}
}
