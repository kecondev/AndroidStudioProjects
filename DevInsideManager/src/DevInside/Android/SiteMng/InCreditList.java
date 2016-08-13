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
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

public class InCreditList extends Activity {
	private boolean m_first_chk = true;
	int iCntPos = 0;
	
	ListView myListview;
	InCreditTextView temp;
	
	ProgressDialog mProgress;
	DownThread mThread;
	
	InCreditTextListAdapter adapter;
	
	int iPosition = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listmain);				
		
		mProgress = ProgressDialog.show(InCreditList.this, "", "Downloading...");
		mThread = new DownThread("http://www.devinside.kr/Android/InValueTaxList.aspx?INOUT=001");
		mThread.start();
		
		myListview = (ListView)findViewById(R.id.myListview);				
		myListview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				//iPosition = myListview.
				//Toast.makeText(getApplicationContext(),Integer.toString(iPosition), 0).show();
				iPosition = arg2;
				InCreditTextItem  Item = (InCreditTextItem)adapter.getItem(arg2);
				String[] data = Item.getData();						
				//Toast.makeText(getApplicationContext(),data[0] + "\n" + data[1] +"\n"+ data[2] + "\n" + data[3] + "\n" + data[4] +"\n"+ data[5] + "\n" + data[6] +"\n"+ data[7], 0).show();
				InCreditED.SCrecit = data[1];
				InCreditED.SBizNo = data[2].replace("사업자번호[", "").replace("-", "").replace("]", "");
				InCreditED.SDate = data[3];
				InCreditED.SBizName = data[4].replace("사업자명 : ", "");
				InCreditED.SMoney = data[5].replace("공급가액 : ", "").replace(",", "");
				InCreditED.STax = data[6].replace("세액 : ", "").replace(",", "");
				InCreditED.SID = data[7];
				
				//Toast.makeText(getApplicationContext(),InCreditED.SCrecit + "\n" + InCreditED.SBizNo +"\n"+ InCreditED.SDate + "\n" + InCreditED.SBizName + "\n" + InCreditED.SMoney +"\n"+ InCreditED.STax + "\n" + InCreditED.SID, 0).show();
				Intent mInCreditED = new Intent(getApplicationContext(), InCreditED.class);
				startActivity(mInCreditED);
			}
		});	
		
		myListview.setOnScrollListener(new OnScrollListener() {			
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// 리스트뷰가 구성이 완료되어 보이는 경우
	            if(view.isShown()){
	                if(scrollState == SCROLL_STATE_IDLE) {
	                    // 리스트뷰의 0 번 인덱스 항목이 리스트뷰의 상단에 보이고 있는 경우
	                    if(view.getFirstVisiblePosition() == 0) {

	                    }
	                }
	            }
			}
		
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
	            if(view.isShown()){
	            	iCntPos = firstVisibleItem;
	            	//Toast.makeText(getApplicationContext(), Integer.toString(firstVisibleItem), Toast.LENGTH_SHORT).show();
	                // 리스트뷰의 0 번 인덱스 항목이 리스트뷰의 상단에 보이고 있는 경우                	
	                if(firstVisibleItem == totalItemCount - 4) {	                	
	                	if(m_first_chk){
	                		Toast.makeText(getApplicationContext(), Integer.toString(totalItemCount), 0).show();
	                		m_first_chk = false;
	                	}                		                	
	                }
	            }
			}
        });		
	}
	
	@Override
	protected void onRestart()
	{
		super.onRestart(); 
		
		mProgress = ProgressDialog.show(InCreditList.this, "", "Downloading...");
		mThread = new DownThread("http://www.devinside.kr/Android/InValueTaxList.aspx?INOUT=001");
		mThread.start();
		
		myListview = (ListView)findViewById(R.id.myListview);					
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
		if(iPosition > 0){
			myListview.requestFocus();
			myListview.setSelection(iPosition);
		}
		
		//4.0.3버전에서 제외딤
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
