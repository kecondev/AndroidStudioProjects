package DevInside.Android.SiteMng;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class InTaxList extends Activity {
	TextView tvInfo;
	ListView myListview;
	InCreditTextView temp;
	
	ProgressDialog mProgress;
	DownThread mThread;
	
	InCreditTextListAdapter adapter;
	//String xml = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listmain);				
		
		tvInfo = (TextView)findViewById(R.id.tvtaxlistinfo);
		tvInfo.setText("매입세금계산서내역");
		mProgress = ProgressDialog.show(InTaxList.this, "", "Downloading...");
		mThread = new DownThread("http://www.devinside.kr/Android/InValueTaxList.aspx?INOUT=003");
		mThread.start();
		
		myListview = (ListView)findViewById(R.id.myListview);			
			
		myListview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				InCreditTextItem  Item = (InCreditTextItem)adapter.getItem(arg2);
				String[] data = Item.getData();						
				Toast.makeText(getApplicationContext(),data[0] + "\n" + data[1] + "\n" + data[2] + "\n" + data[3] + "\n" + data[4] + "\n" + data[5] + "\n" + data[6] + "\n" + data[7] + "\n" + arg2, 0).show();	
			}
		});	
	}
	
	////////////////////////////////////////////////////////////////////////////
    public void XmlDomParser(String xml){
		//final InCreditTextListAdapter adapter = new InCreditTextListAdapter(this);	
    	adapter = new InCreditTextListAdapter(this);	
    	
		try{			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputStream istream = new ByteArrayInputStream(xml.getBytes("utf-8"));
			Document doc = builder.parse(istream);
			
			org.w3c.dom.Element order = doc.getDocumentElement();
			NodeList items = order.getElementsByTagName("VALUETAX");
			
			for(int i=0; i<items.getLength();i++){
				org.w3c.dom.Node item = items.item(i);
				org.w3c.dom.Node text = item.getFirstChild();
				String ItemName = text.getNodeValue();
				
				NamedNodeMap Attrs = item.getAttributes();
				String[] arrStr = new String[8];
				for(int j=0; j < Attrs.getLength(); j++){
					org.w3c.dom.Node attr = Attrs.item(j);
					arrStr[j] = attr.getNodeValue();					
				}				
				arrStr[7] = ItemName;
				adapter.addItem(new InCreditTextItem(arrStr[1], arrStr[3], arrStr[2], arrStr[4], arrStr[7], arrStr[5], arrStr[6], arrStr[0]));
			}
		}
		catch(Exception e){
			Toast.makeText(this, e.getMessage(), 0).show();
		}
		
		myListview.setAdapter(adapter);
		
		//myListview.requestFocus();
		//myListview.setSelection(7);
		//mThread.stop();
    }
    
	class DownThread extends Thread {
		String mAddr;
		String mResult;

		DownThread(String addr) {
			mAddr = addr;
			mResult = "";
		}

		public void run() {
			StringBuilder html = new StringBuilder(); 
			try {
				URL url = new URL(mAddr);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				if (conn != null) {
					conn.setConnectTimeout(10000);
					conn.setUseCaches(false);
					if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
						BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						for (;;) {
							String line = br.readLine();
							if (line == null) break;
							html.append(line + '\n'); 
						}
						br.close();
						mResult = html.toString();
					}
					conn.disconnect();
				}
			} 
			catch (Exception ex) {;}
			mAfterDown.sendEmptyMessage(0);
		}
	}

	Handler mAfterDown = new Handler() {
		public void handleMessage(Message msg) {
			mProgress.dismiss();
			XmlDomParser(mThread.mResult);
		}
	};	
}
