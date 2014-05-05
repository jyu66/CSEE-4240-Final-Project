package edu.uga.engr.sensornetworks.jasper_gateway;
import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;

import com.rapplogic.xbee.api.ApiId;
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeResponse;
import com.rapplogic.xbee.api.zigbee.ZNetRxResponse;


public class Gateway {

	XBee xbee = new XBee();
	
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
						int reading0 = data[0];
						int reading1 = data[1];
						int reading2 = data[2];
						System.out.println("Air Temp: " +reading0);
						System.out.println("Body Temp: " +reading1);
						System.out.println("Force: " +reading2);
						
						String httpreq = Request
									.Get("http://sensornetworks.engr.uga.edu/sp14/jyu/sensornet/data_upload.php?"
									+"pw=friend"+"&"+"airTemp="+reading0+
									"&"+"bodyTemp="+reading1+
									"&"+"forceRead="+reading2+
									"&"+"radioaddr=" + URLEncoder.encode(rx.getRemoteAddress64().toString(),"UTF-8")).execute().returnContent().asString();
						
						
						System.out.println(httpreq);
			
						
					}// end if
				}// end second try
				catch (Exception e) {

				}// end catch
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


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

