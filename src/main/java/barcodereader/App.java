package barcodereader;

import com.dynamsoft.dbr.*;

public class App {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: path");
		    System.exit(1);
	    }
		try {
			String path = args[0];
			BarcodeReader.initLicense("DLS2eyJoYW5kc2hha2VDb2RlIjoiMjAwMDAxLTE2NDk4Mjk3OTI2MzUiLCJvcmdhbml6YXRpb25JRCI6IjIwMDAwMSIsInNlc3Npb25QYXNzd29yZCI6IndTcGR6Vm05WDJrcEQ5YUoifQ==");
			BarcodeReader reader = new BarcodeReader();
			TextResult[] results = reader.decodeFile(path, "");
			for (TextResult result:results) {
				System.out.println(result.barcodeFormatString+": "+result.barcodeText);
			}
		} catch (BarcodeReaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		System.exit(0);
	}

}
