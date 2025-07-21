package com.example.project.point.service;

import com.example.project.point.dto.TossConfirmResult;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class TossPaymentsImpl implements TossPayments {

    @Value("${toss.secret-key}")
    String widgetSecretKey;

    @Override
    public TossConfirmResult confirmPayment(String paymentKey, String orderId, int amount) {

        try {
            String urlString = "https://api.tosspayments.com/v1/payments/confirm";
            JSONObject obj = new JSONObject();
            obj.put("paymentKey", paymentKey);
            obj.put("orderId", orderId);
            obj.put("amount", amount);

            Base64.Encoder encoder = Base64.getEncoder();
            byte[] encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));
            String authorizations = "Basic " + new String(encodedBytes);

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Authorization", authorizations);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(obj.toString().getBytes(StandardCharsets.UTF_8));

            int code = connection.getResponseCode();
            InputStream responseStream = (code == 200) ? connection.getInputStream() : connection.getErrorStream();
            Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            responseStream.close();

            // 결제 승인 결과 status 확인 (Toss API 문서 참고)
            if (code == 200 && "DONE".equals(jsonObject.get("status"))) {
                return new TossConfirmResult(true, "SUCCESS");
            } else {
                return new TossConfirmResult(false, "FAIL");
            }

        } catch (Exception e) {
            // (로깅 또는 예외처리 추가)
            return new TossConfirmResult(false, "FAIL");
        }
    }
}
