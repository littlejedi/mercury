package com.liangzhi.mercury.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liangzhi.mercury.message.Message;
import com.liangzhi.mercury.message.MessageType;
import com.liangzhi.mercury.message.payload.SensorDataPoint;

public class TestClient {
    
    public static final ObjectMapper MAPPER = new ObjectMapper();
    
    public static void main(String[] args) throws Exception {
        
        String sentence;
        String modifiedSentence;
        BufferedReader inFromUser = new BufferedReader(
           new InputStreamReader(System.in));
        Socket clientSocket = new Socket("localhost", 8090);
        DataOutputStream outToServer = new DataOutputStream(
           clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(
           clientSocket.getInputStream()));
        String json = generateMessage();
        outToServer.writeBytes(json + '\n');
        //modifiedSentence = inFromServer.readLine();
        //System.out.println("FROM SERVER: " + 
        //        modifiedSentence);
        clientSocket.close();
    }
    
    public static String generateMessage() throws Exception {
        Message m = new Message();
        m.setCreated(DateTime.now(DateTimeZone.UTC).getMillis());
        m.setDeviceId(UUID.randomUUID().toString());
        m.setDeviceKey(UUID.randomUUID().toString());
        m.setId(UUID.randomUUID().toString());
        m.setType(MessageType.SENSOR_DATA_PUSH);
        // create payload
        SensorDataPoint p = new SensorDataPoint();
        p.setSensorDataTimestamp(DateTime.now(DateTimeZone.UTC).getMillis());
        p.setSensorDataType(0);
        p.setSensorDataValue("12345");
        List<SensorDataPoint> pts = new ArrayList<SensorDataPoint>();
        pts.add(p);
        JsonNode payload = MAPPER.valueToTree(pts);
        m.setPayload(payload);
        return MAPPER.writeValueAsString(m);
    }

}
