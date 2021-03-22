package com.jds.fpe.parquet;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jds.fpe.FPEApplication;

import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.fs.RemoteIterator;
import org.apache.parquet.column.page.PageReadStore;
import org.apache.parquet.example.data.Group;
import org.apache.parquet.example.data.simple.convert.GroupRecordConverter;
import org.apache.parquet.format.converter.ParquetMetadataConverter;
import org.apache.parquet.hadoop.ParquetFileReader;
import org.apache.parquet.hadoop.metadata.ParquetMetadata;
import org.apache.parquet.io.ColumnIOFactory;
import org.apache.parquet.io.MessageColumnIO;
import org.apache.parquet.io.RecordReader;
import org.apache.parquet.schema.MessageType;
import org.apache.parquet.schema.Type;


public class ParquetUtils {

	private static final Logger logger = LoggerFactory.getLogger(ParquetUtils.class);

	public static void printGroup(Group g) {
		int fieldCount = g.getType().getFieldCount();
		for (int field = 0; field < fieldCount; field++) {
			int valueCount = g.getFieldRepetitionCount(field);

			Type fieldType = g.getType().getType(field);
			String fieldName = fieldType.getName();

			for (int index = 0; index < valueCount; index++) {
				if (fieldType.isPrimitive()) {
					System.out.println(fieldName + " " + g.getValueToString(field, index));
				}
			}
		}
		System.out.println("");
	}

	public static void readParquet(String file) {
		Path parquetFilePath = null;
		// Find a file in case a directory was passed
//		RemoteIterator<LocatedFileStatus> it = FileSystem.get(getConf()).listFiles(new Path(inputFile), true);
//		while (it.hasNext()) {
//			FileStatus fs = it.next();
//			if (fs.isFile()) {
//				parquetFilePath = fs.getPath();
//				break;
//			}
//		}
//		if (parquetFilePath == null) {
//			logger.error("No file found for " + inputFile);
//			return 1;
//		}
		
		logger.info("Getting schema from " + parquetFilePath);
//		ParquetMetadata readFooter = ParquetFileReader.readFooter(getConf(), parquetFilePath);
//		MessageType schema = readFooter.getFileMetaData().getSchema();
//		logger.info(schema);
	}

}