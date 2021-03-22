package com.jds.fpe.junit;

import static org.apache.spark.sql.functions.callUDF;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataTypes;
import org.junit.Test;

//import com.holdenkarau.spark.testing.SharedJavaSparkContext;

//public class SparkUDFTest extends SharedJavaSparkContext{

public class SparkUDFTest {
	
//	@Test
//	public void testSparkFPEUDF() {
//		SparkSession spark = SparkSession.builder().appName("FPE").master("local").getOrCreate();
//
//		// registers a new internal UDF
//		spark.udf().register("encrypt", new UDF1<Integer, Integer>() {
//			private static final long serialVersionUID = -5372447039252716846L;
//
//			@Override
//			public Integer call(Integer x) {
//				return x * 2;
//			}
//		}, DataTypes.IntegerType);
//
//		String filename = "data/encryption-test-file.csv";
//		Dataset<Row> df = spark.read().format("csv").option("inferSchema", "true").option("header", "false")
//				.load(filename);
//		df = df.withColumn("label", df.col("_c0")).drop("_c0");
//		df = df.withColumn("value", df.col("_c1")).drop("_c1");
//		df = df.withColumn("x2", callUDF("internal", df.col("value").cast(DataTypes.IntegerType)));
//		df.show();
//	}
	
    @Test
    public void testSparkFPEUDF() {
        SparkConf conf = new SparkConf()
                .setMaster("local[*]")
                .setAppName("FPE")
                .set("fs.s3.canned.acl","BucketOwnerFullControl")
                .set("spark.acls.enable", "false")
                .set("spark.ui.enabled", "false");

        JavaSparkContext jsc = new JavaSparkContext(conf);
        jsc.hadoopConfiguration().set("fs.s3.canned.acl","BucketOwnerFullControl" );
        // use it
        jsc.stop();
    }


}
