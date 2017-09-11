package demo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class JsonMerger {

	public static void main(String[] args) throws IOException {

		List<Record> recordsOutput = new ArrayList<Record>();
		
		Record[] recordsA = readRecords("./data/a.json");
		transformRecords(recordsA);
		for(Record record : recordsA) {
			recordsOutput.add(record);
		}
		
		Record[] recordsB = readRecords("./data/b.json");
		transformRecords(recordsB);
		for(Record record : recordsB) {
			recordsOutput.add(record);
		}
		
		String outputFilename = "./data/output.json";
		
		dumpRecords(outputFilename, recordsOutput);
		
		System.out.println(String.format("处理完毕...数据文件是 %s", outputFilename));

	}
	
	private static void transformRecords(Record[] records) {
		// 转换精度
		for(int i=0; i < records.length; i++) {
			//records[i].data = Arrays.copyOfRange(records[i].data, 0, 50); // 只取50条数据以便调试
			for(int k=0; k < records[i].data.length; k++) {
				BigDecimal bd = new BigDecimal(records[i].data[k]);
				records[i].data[k] = bd.setScale(2, RoundingMode.HALF_UP).floatValue();
			}
		}
	}
	
	private static Record[] readRecords(String filename) throws FileNotFoundException {
		// 按结构读取JSON
		JsonReader reader = new JsonReader(new FileReader(filename));
		Gson gson = new Gson();
		return gson.fromJson(reader, Record[].class);
	}
	
	private static void dumpRecords(String filename, List<Record> records) throws IOException {
		// 按结构写入JSON
		Gson gson = new GsonBuilder().setPrettyPrinting().create();   // 带缩进输出，便于调试，但尺寸较大
		//Gson gson = new Gson(); // 正常输出
		Writer writer = new FileWriter(filename);
		gson.toJson(records, writer);
		writer.close();
	}


}

class Header {
    int discipline;
    String disciplineName;
    int gribEdition;
    int gribLength;
    int center;
    String centerName;
    int subcenter;
    String refTime;
    int significanceOfRT;
    String significanceOfRTName;
    int productStatus;
    String productStatusName;
    int productType;
    String productTypeName;
    int productDefinitionTemplate;
    String productDefinitionTemplateName;
    int parameterCategory;
    String parameterCategoryName;
    int parameterNumber;
    String parameterNumberName;
    String parameterUnit;
    int genProcessType;
    String genProcessTypeName;
    int forecastTime;
    int surface1Type;
    String surface1TypeName;
    float surface1Value;
    int surface2Type;
    String surface2TypeName;
    float surface2Value;
    int gridDefinitionTemplate;
    String gridDefinitionTemplateName;
    int numberPoints;
    int shape;
    String shapeName;
    String gridUnits;
    int resolution;
    String winds;
    int scanMode;
    int nx;
    int ny;
    int basicAngle;
    int lo1;
    int la1;
    float lo2;
    float la2;
    float dx;
    float dy;
}

class Meta {
	String date;
}

class Record {
	Header header;
	float[] data;
	Meta meta;
}

