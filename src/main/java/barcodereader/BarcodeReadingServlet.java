package barcodereader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.dynamsoft.dbr.*;
import com.google.gson.Gson;

@SuppressWarnings("serial")
public class BarcodeReadingServlet extends HttpServlet   {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("read barcodes");
		StringBuilder responseStrBuilder = new StringBuilder();
        DecodingResult decodingResult = new DecodingResult();
        Gson gson = new Gson();
		try {
            BufferedReader streamReader = new BufferedReader( new InputStreamReader(request.getInputStream(), "UTF-8"));
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            
            DecodingRequestBody body = gson.fromJson(responseStrBuilder.toString(), DecodingRequestBody.class);
            String base64 = body.base64.replaceFirst("data:.*?;base64,","");
            BarcodeReader reader = new BarcodeReader();
            long startTime = System.nanoTime();
            TextResult[] results = reader.decodeBase64String(base64, "");
            long endTime = System.nanoTime();
            List<BarcodeResult> barcodeResults = new ArrayList<BarcodeResult>();
            for (TextResult result:results) {
            	BarcodeResult barcodeResult = new BarcodeResult();
            	barcodeResult.barcodeText = result.barcodeText;
            	barcodeResult.barcodeFormat = result.barcodeFormatString;
            	barcodeResult.barcodeBytes = Base64.getEncoder().encodeToString(result.barcodeBytes);
            	barcodeResult.x1 = result.localizationResult.resultPoints[0].x;
            	barcodeResult.x2 = result.localizationResult.resultPoints[1].x;
            	barcodeResult.x3 = result.localizationResult.resultPoints[2].x;
            	barcodeResult.x4 = result.localizationResult.resultPoints[3].x;
            	barcodeResult.y1 = result.localizationResult.resultPoints[0].y;
            	barcodeResult.y2 = result.localizationResult.resultPoints[1].y;
            	barcodeResult.y3 = result.localizationResult.resultPoints[2].y;
            	barcodeResult.y4 = result.localizationResult.resultPoints[3].y;
            	barcodeResults.add(barcodeResult);
            }
            decodingResult.elapsedTime = endTime - startTime;
            decodingResult.results = barcodeResults;
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.getWriter().write(gson.toJson(decodingResult));
	}

}
