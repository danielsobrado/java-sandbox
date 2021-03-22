package com.jds.fpe.junit;

import static org.apache.spark.sql.functions.callUDF;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataTypes;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.holdenkarau.spark.testing.JavaDatasetSuiteBase;
import com.jds.fpe.Encryption;

//import com.holdenkarau.spark.testing.SharedJavaSparkContext;

//public class SparkUDFTest extends SharedJavaSparkContext{

public class SparkUDFTest extends JavaDatasetSuiteBase implements Serializable {
	
	  private Configuration conf;
	  private FileSystem fs;
	  private MiniDFSCluster cluster;
	  private JavaSparkContext sparkContext;
	  private SparkSession spark;
	  private SparkConf sconf;
	  
	  @BeforeClass
	  public static void setupTests() throws Exception {
	    // Force logging level for a job class
//	    LogManager.getLogger(SparkUDFTest.class).setLevel(Level.DEBUG);
	  }
	  
	  @Before
	  public void setup() throws Exception{
	        SparkConf sconf = new SparkConf()
	                .setMaster("local[*]")
	                .setAppName("FPE")
	                .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
	                .set("spark.io.compression.codec", "lz4")
	                .set("spark.acls.enable", "false")
	                .set("spark.ui.enabled", "false");

	        spark = SparkSession
	                .builder()
	                .appName("FPE")
	                .config(sconf)
	                .getOrCreate();
	  }
	
    @Test
    public void testSparkFPEUDF() throws Exception{
        
		// Registers a new internal UDF
        spark.udf().register("encryptStr", new UDF1<String, String>() {
			private static final long serialVersionUID = -5372447039252716846L;

			@Override
			public String call(String x) {
				Encryption encryption = new Encryption();
		        return encryption.encryptStr(x);
			}
		}, DataTypes.StringType);

        spark.udf().register("encryptInt", new UDF1<Integer, Integer>() {
			private static final long serialVersionUID = -5372447039252716846L;

			@Override
			public Integer call(Integer x) {
				
				Encryption encryption = new Encryption();
		        return encryption.encryptInt(x);
			}
		}, DataTypes.IntegerType);

		String filename = "data/encryption-test-file.csv";
		Dataset<Row> df = spark.read().format("csv").option("inferSchema", "true").option("header", "false")
				.load(filename);
		df = df.withColumn("label", df.col("_c0")).drop("_c0");
		df = df.withColumn("integer", df.col("_c1")).drop("_c1");
		df = df.withColumn("string", df.col("_c2")).drop("_c2");
		df = df.withColumn("encInt", callUDF("encryptInt", df.col("integer").cast(DataTypes.IntegerType)));
		df = df.withColumn("encStr", callUDF("encryptStr", df.col("string").cast(DataTypes.StringType)));
		df.show();    
	
    }
    
    @After
    public void tearDown() {
	    if (spark != null) {
	    	spark.stop();
	    	spark = null;
	      }
    }


}
