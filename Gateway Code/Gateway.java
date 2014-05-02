package edu.uga.engr.sensornetworks.jasper_gateway;
import java.io.IOException;

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


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

