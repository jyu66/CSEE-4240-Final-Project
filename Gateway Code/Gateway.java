package edu.uga.engr.sensornetworks.jasper_gateway;
import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rapplogic.xbee.api.ApiId;
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeAddress64;
import com.rapplogic.xbee.api.XBeePacket;
import com.rapplogic.xbee.api.XBeeResponse;
import com.rapplogic.xbee.api.wpan.TxRequest64;
import com.rapplogic.xbee.api.zigbee.ZNetRxResponse;
import com.rapplogic.xbee.api.zigbee.ZNetTxRequest;


public class Gateway {

	XBee xbee = new XBee();
	int reading0;
	int reading1;
	int reading2;
	int reading3;
	
	String radio;
	int[] payload = new int [72];
	public Gateway() throws Exception {
		try {
			xbee.open("COM5", 9600);

			while (true) {

				try {
					// wait until packet is received
					XBeeResponse response = xbee.getResponse();

					if (response.getApiId() == ApiId.ZNET_RX_RESPONSE) {
						ZNetRxResponse rx = (ZNetRxResponse) response;
						System.out.println("got a packet");
						System.out.println(rx.getRemoteAddress64());
						
						//reading in sensor data
						int [] data = rx.getData();
						//System.out.println(data[0]+","+data[1]);
						//int reading = data[0] | (data[1] <<8);
						reading0 = data[0]; //air temp
						reading1 = data[1]; //body temp
						reading2 = data[2]; //force sensor
						reading3 = data[3]; //motion sensor
						radio = rx.getRemoteAddress64().toString();

						
						System.out.println("Air Temp: " +reading0);
						System.out.println("Body Temp: " +reading1);
						System.out.println("Force: " +reading2);
						System.out.println("Motion: "+reading3);
						
						//http request that will be send to data_upload.php
						String httpreq = Request
									.Get("http://sensornetworks.engr.uga.edu/sp14/jyu/sensornet/data_upload.php?"
									+"pw=friend"+"&"+"airTemp="+reading0+
									"&"+"bodyTemp="+reading1+
									"&"+"forceRead="+reading2+
									"&"+"hasmotion="+reading3+
									"&"+"radioaddr=" + URLEncoder.encode(rx.getRemoteAddress64().toString(),"UTF-8")).execute().returnContent().asString();
						
						
						System.out.println(httpreq);
			
						
					}// end if
				}// end second try
				catch (Exception e) {

				}// end catch
				
				//requesting data from database
				String httpreq = Request
						.Get("http://sensornetworks.engr.uga.edu/sp14/jyu/sensornet/getdat.php?"
						+"pw=friend").execute().returnContent().asString();
				System.out.println(httpreq);
				
				//parsing json encoded data that came from getdat.php
				JsonParser parser = new JsonParser();
				JsonElement f = parser.parse(httpreq);
				JsonObject j = f.getAsJsonObject();
				JsonArray motes = j.get("data").getAsJsonArray();

				//getting radio address and name associated with address
				for(int i=0; i<motes.size(); i++){
					JsonObject obj = motes.get(i).getAsJsonObject();
					String radio_address = obj.get("radioaddr").getAsString();
					String name = obj.get("name").getAsString();
					
					
					//check by matching radio addresses to see if nearby people are friendly or not.
					if(radio_address.equals(radio)){
						System.out.println(radio_address.equals(radio));
						System.out.println(name+" Is Friendly!"+" radio addr: " +radio_address);
						//parse radio address to something readable by XBeeAddress64s
						String val = radio_address.replace(",","");
						val = val.replace("0x","");
						String s1 = val.substring(0,2);
						String s2 = val.substring(2,4);
						String s3 = val.substring(4,6);
						String s4 = val.substring(6,8);
						String s5 = val.substring(8,10);
						String s6 = val.substring(10,12);
						String s7 = val.substring(12,14);
						String s8 = val.substring(14,16);
						val = s1 + " "+ s2+ " " + s3+ " " + s4+ " " +
								s5+ " " +s6+ " " +s7+ " " +s8;
						System.out.println("PARSE2: "+val);
							
						XBeeAddress64 addr = new XBeeAddress64(val);
						System.out.println(addr);
						
							//sending data that tells mote user is friendly.
							
							payload[0]=1 & 0xFF;
							payload[1]=(1 >>8) &0xFF;
							//TxRequest64 tx = new TxRequest64 (addr, payload);
							ZNetTxRequest tx = new ZNetTxRequest(addr,payload);
							xbee.sendAsynchronous(tx);
							Thread.sleep(2000);
						
							
					}
					else{
						System.out.println(name+" Is Not Friendly!"+" radio addr: " +radio_address);
					}
				}
			}// end while
		}// end first try
		finally {
			if (xbee.isConnected()) {
				xbee.close();
			}
		}// end finally

	}// end public Gateway()
	
	

	

	public static void main(String[] args) throws Exception{
		new Gateway();

	}
}//end gateway class


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

