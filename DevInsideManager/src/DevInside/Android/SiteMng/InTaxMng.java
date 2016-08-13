package DevInside.Android.SiteMng;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InTaxMng extends Activity {
	EditText etDate;
	EditText etBizName;
	EditText etBizNo;
	EditText etMoney;
	EditText etTax;
    Button btSend;
    TextView tvBizName;
    
    private String strDate;
    private String strBizName;
    private String strBizNo;
    private String strMoney;
    private String strTax;   
    
	ProgressDialog mProgress;
	DownThread mThread;
	
	private String strGbn="0";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intaxmng);
        
        etDate = (EditText)findViewById(R.id.etDateT);
        etBizName = (EditText)findViewById(R.id.etBizNameT);
        etBizNo = (EditText)findViewById(R.id.etBizNoT);
        etMoney = (EditText)findViewById(R.id.etMoneyT);
        etTax = (EditText)findViewById(R.id.etTaxT);

        //�������ڿ� ���� ��¥�� �����Ѵ�.
        Calendar c = Calendar.getInstance();
        int nYear = c.get(Calendar.YEAR);
        int nMonth = c.get(Calendar.MONTH)+1;
        int nDay = c.get(Calendar.DAY_OF_MONTH);
        String strY = Integer.toString(nYear);
        String strM = Integer.toString(nMonth);
        if(strM.length() == 1)
        	strM = "0" + strM;
        String strD = Integer.toString(nDay);
        if(strD.length() == 1)
        	strD = "0" + strD;        
        etDate.setText(strY + "-" + strM + "-" + strD);        

        tvBizName = (TextView)findViewById(R.id.tvBizNameT);
        tvBizName.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				if(etBizNo.getText().toString().length() < 10){
					Toast.makeText(getBaseContext(), "����ڹ�ȣ�� 10�ڸ� ���ڸ� �Է����ּ���.", Toast.LENGTH_LONG).show();
				}
				else{
					strGbn = "0";
					//http://www.devinside.kr/Android/CompanyNameSearch.aspx?BizNum=1011221844
					String sURL = "http://www.devinside.kr/Android/CompanyNameSearch.aspx";
					strBizNo = "?BizNum=" + etBizNo.getText().toString();
					
					sURL += strBizNo;
					mProgress = ProgressDialog.show(InTaxMng.this, "", "����Ÿ�� �����ɴϴ�...");
					mThread = new DownThread(sURL);
					mThread.start();
				}
			}        	
        });
        
        btSend = (Button)findViewById(R.id.btSendT);        
        btSend.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				//�������� ����
				strDate = etDate.getText().toString();
				strDate = strDate.replace("-", "").replace(".", "");
				if(strDate.length() != 8){
					etDate.requestFocus();
					etDate.setSelection(0, etDate.length());
					Toast.makeText(getBaseContext(), "�������ڴ� ****-**-** �������� �Է����ּ���.", Toast.LENGTH_SHORT).show();
					return;
				}
										
				//����ڹ�ȣ ����
				if(etBizNo.getText().toString().length() < 10){
					etBizNo.requestFocus();
					etBizNo.setSelection(0, etBizNo.length());					
					Toast.makeText(getBaseContext(), "����ڹ�ȣ�� 10�ڸ� ���ڸ� �Է����ּ���.", Toast.LENGTH_LONG).show();
					return;
				}
				
				//����ڸ� ����
				if(etBizName.getText().toString().length() == 0){
					etBizName.requestFocus();				
					Toast.makeText(getBaseContext(), "����ڸ��� �Է����ּ���.", Toast.LENGTH_LONG).show();
					return;
				}
				
				//�����ݾ� ����
				if(etMoney.getText().toString().length() == 0){
					etMoney.requestFocus();
					Toast.makeText(getBaseContext(), "�����ݾ��� �Է����ּ���.", Toast.LENGTH_LONG).show();
					return;
				}
				
				//�ΰ����� ����
				if(etTax.getText().toString().length() == 0){
					etTax.requestFocus();
					Toast.makeText(getBaseContext(), "�ΰ������� �Է����ּ���.", Toast.LENGTH_LONG).show();
					return;
				}
				
				strGbn = "1";
				String sURL = "http://www.devinside.kr/AndroidProcess/InValueTaxForAndroid.aspx?Gubun=2";
				strBizNo = "&BizNum=" + etBizNo.getText().toString();	
				
				try {
					//strBizName = new String(etBizName.getText().toString().getBytes("euc-kr"), "8859_1");
					strBizName = URLEncoder.encode(etBizName.getText().toString(), "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				strBizName = "&Company=" + strBizName;
				strDate = "&Date=" + strDate;
				
				strMoney = "&Cost=" + etMoney.getText().toString();
				strTax = "&Tax=" + etTax.getText().toString();
				String strParam = strBizNo + strBizName + strDate + strMoney + strTax;				
				sURL += strParam;
				
				mProgress = ProgressDialog.show(InTaxMng.this, "", "������...");
				mThread = new DownThread(sURL);
				mThread.start();
				//Toast.makeText(getBaseContext(), sURL, Toast.LENGTH_LONG).show();				
			}
		});        
    }
    
	////////////////////////////////////////////////////////////////////////////
	
    // �������� �ŷ�ó���� �����´�.
    public void GetCompanyDataParser(String xml){
    	try
    	{
    	     etBizName.setText(xml);
    	}
    	catch(Exception e)
    	{    	
    		Toast.makeText(getBaseContext(), e.getMessage(), 0).show();
    	}   	
    	finally
    	{    		
    	}
    }
    
    // �������� �������� ����Ÿ�� ó���Ѵ�.
    public void GetDataParser(String xml){
    	try
    	{
    		int iValue = Integer.parseInt(xml);
    	    if(iValue==1)
    	    	Toast.makeText(getBaseContext(), "�ԷµǾ����ϴ�.", 0).show();
    	    else
    	    	Toast.makeText(getBaseContext(), "����Ÿ�� Ȯ���ϼ���. �Էµ��� �ʾҽ��ϴ�.", 0).show();  
    	}
    	catch(Exception e)
    	{    	
    		Toast.makeText(getBaseContext(), e.getMessage(), 0).show();
    	}   	
    	finally
    	{    		
    	}
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
							html.append(line); 
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
			if(strGbn=="0")
				GetCompanyDataParser(mThread.mResult);
			else if(strGbn=="1"){
				GetDataParser(mThread.mResult);
			}				
		}
	};	
	
	////////////////////////////////////////////////////////////////////////////
	
}
