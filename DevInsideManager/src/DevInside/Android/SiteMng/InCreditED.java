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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class InCreditED extends Activity {
	EditText etDateED;
	EditText etBizNameED;
	EditText etBizNoED;
	Spinner spCreditED;
	EditText etMoneyED;
	EditText etTaxED;
    Button btEditED;
    Button btDelED;
    Button btRegED;
    Button btToDay;
    TextView tvBizNameED;
    
    private String strBtnInfo;
    
    private String strDate;
    private String strBizName;
    private String strBizNo;
    private String strCrecit;
    private String strCrecitURL;
    private String strMoney;
    private String strTax;
    private String strSaveType;
    
    static String SID;
    static String SDate;
    static String SBizName;
    static String SBizNo;
    static String SCrecit;
    static String SMoney;
    static String STax;    
    
    ArrayAdapter<CharSequence> adspin;
    
	ProgressDialog mProgress;
	DownThread mThread;
	
	private String strGbn="0";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incredited);
        
        etDateED = (EditText)findViewById(R.id.etDateED);
        etBizNameED = (EditText)findViewById(R.id.etBizNameED);
        etBizNoED = (EditText)findViewById(R.id.etBizNoED);
        spCreditED = (Spinner)findViewById(R.id.spCreditED);
        etMoneyED = (EditText)findViewById(R.id.etMoneyED);
        etTaxED = (EditText)findViewById(R.id.etTaxED);
        
        spCreditED.setPrompt("ī��縦 �����ϼ���.");
        adspin = ArrayAdapter.createFromResource(this, R.array.cardname, android.R.layout.simple_spinner_item);
        adspin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCreditED.setAdapter(adspin);
        
        spCreditED.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
				strCrecit = parent.getItemAtPosition(position).toString();
			}
			public void onNothingSelected(AdapterView<?> arg0) {
						    
			}
        });
        
        etDateED.setText(SDate);
        etBizNoED.setText(SBizNo);
        etBizNameED.setText(SBizName);       
        //Toast.makeText(getBaseContext(), SCrecit, 0).show();
        if(SCrecit.equals("����ī��P"))
        	spCreditED.setSelection(0);
        else if(SCrecit.equals("����ī��"))
        	spCreditED.setSelection(1);        
        else if(SCrecit.equals("�Ե�ī��"))
        	spCreditED.setSelection(2);   
        else if(SCrecit.equals("����ī��"))
        	spCreditED.setSelection(3);
        else
        	spCreditED.setSelection(0);
        	
        etMoneyED.setText(SMoney);
        etTaxED.setText(STax);        
        
        tvBizNameED = (TextView)findViewById(R.id.tvBizNameED);
        tvBizNameED.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				if(etBizNoED.getText().toString().length() < 10){
					Toast.makeText(getBaseContext(), "����ڹ�ȣ�� 10�ڸ� ���ڸ� �Է����ּ���.", Toast.LENGTH_LONG).show();
				}
				else{
					strGbn = "0";
					//http://www.devinside.kr/Android/CompanyNameSearch.aspx?BizNum=1011221844
					String sURL = "http://www.devinside.kr/Android/CompanyNameSearch.aspx";
					strBizNo = "?BizNum=" + etBizNoED.getText().toString();
					
					sURL += strBizNo;
					mProgress = ProgressDialog.show(InCreditED.this, "", "����Ÿ�� �����ɴϴ�...");
					mThread = new DownThread(sURL);
					mThread.start();
				}
			}        	
        });
        
        btToDay = (Button)findViewById(R.id.btnToDay);        
        btToDay.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {	
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
		        etDateED.setText(strY + "-" + strM + "-" + strD);		
			}
		}); 
        
        btRegED = (Button)findViewById(R.id.btRegED);        
        btRegED.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {				
				strBtnInfo = "��ϵǾ����ϴ�.";
				
				//�������� ����
				strDate = etDateED.getText().toString();
				strDate = strDate.replace("-", "").replace(".", "");
				if(strDate.length() != 8){
					etDateED.requestFocus();
					etDateED.setSelection(0, etDateED.length());
					Toast.makeText(getBaseContext(), "�������ڴ� ****-**-** �������� �Է����ּ���.", Toast.LENGTH_SHORT).show();
					return;
				}
										
				//����ڹ�ȣ ����
				if(etBizNoED.getText().toString().length() < 10){
					etBizNoED.requestFocus();
					etBizNoED.setSelection(0, etBizNoED.length());					
					Toast.makeText(getBaseContext(), "����ڹ�ȣ�� 10�ڸ� ���ڸ� �Է����ּ���.", Toast.LENGTH_LONG).show();
					return;
				}
				
				//����ڸ� ����
				if(etBizNameED.getText().toString().length() == 0){
					etBizNameED.requestFocus();				
					Toast.makeText(getBaseContext(), "����ڸ��� �Է����ּ���.", Toast.LENGTH_LONG).show();
					return;
				}
				
				//�����ݾ� ����
				if(etMoneyED.getText().toString().length() == 0){
					etMoneyED.requestFocus();
					Toast.makeText(getBaseContext(), "�����ݾ��� �Է����ּ���.", Toast.LENGTH_LONG).show();
					return;
				}
				
				//�ΰ����� ����
				if(etTaxED.getText().toString().length() == 0){
					etTaxED.requestFocus();
					Toast.makeText(getBaseContext(), "�ΰ������� �Է����ּ���.", Toast.LENGTH_LONG).show();
					return;
				}
				       
				strGbn = "1";
				String sURL = "http://www.devinside.kr/AndroidProcess/InValueTaxForAndroid.aspx?Gubun=1";
				strBizNo = "&BizNum=" + etBizNoED.getText().toString();	
				
				try {
					strBizName = URLEncoder.encode(etBizNameED.getText().toString(), "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				strBizName = "&Company=" + strBizName;
				strDate = "&Date=" + strDate;
				
				try {
					strCrecitURL = URLEncoder.encode(strCrecit, "utf-8");
					//Toast.makeText(getBaseContext(), strCrecitURL, 0).show();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				strCrecitURL = "&Credit=" + strCrecitURL;
				strMoney = "&Cost=" + etMoneyED.getText().toString();
				strTax = "&Tax=" + etTaxED.getText().toString();
				String strParam = strBizNo + strBizName + strDate + strCrecitURL + strMoney + strTax;				
				sURL += strParam;
				
				mProgress = ProgressDialog.show(InCreditED.this, "", "������...");
				mThread = new DownThread(sURL);
				mThread.start();			
			}
		});        
        
        btEditED = (Button)findViewById(R.id.btEditED);        
        btEditED.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {				
				strBtnInfo = "�����Ǿ����ϴ�.";
				
				//�������� ����
				strDate = etDateED.getText().toString();
				strDate = strDate.replace("-", "").replace(".", "");
				if(strDate.length() != 8){
					etDateED.requestFocus();
					etDateED.setSelection(0, etDateED.length());
					Toast.makeText(getBaseContext(), "�������ڴ� ****-**-** �������� �Է����ּ���.", Toast.LENGTH_SHORT).show();
					return;
				}
										
				//����ڹ�ȣ ����
				if(etBizNoED.getText().toString().length() < 10){
					etBizNoED.requestFocus();
					etBizNoED.setSelection(0, etBizNoED.length());					
					Toast.makeText(getBaseContext(), "����ڹ�ȣ�� 10�ڸ� ���ڸ� �Է����ּ���.", Toast.LENGTH_LONG).show();
					return;
				}
				
				//����ڸ� ����
				if(etBizNameED.getText().toString().length() == 0){
					etBizNameED.requestFocus();				
					Toast.makeText(getBaseContext(), "����ڸ��� �Է����ּ���.", Toast.LENGTH_LONG).show();
					return;
				}
				
				//�����ݾ� ����
				if(etMoneyED.getText().toString().length() == 0){
					etMoneyED.requestFocus();
					Toast.makeText(getBaseContext(), "�����ݾ��� �Է����ּ���.", Toast.LENGTH_LONG).show();
					return;
				}
				
				//�ΰ����� ����
				if(etTaxED.getText().toString().length() == 0){
					etTaxED.requestFocus();
					Toast.makeText(getBaseContext(), "�ΰ������� �Է����ּ���.", Toast.LENGTH_LONG).show();
					return;
				}
				
				//http://www.devinside.kr/AndroidProcess/InValueTaxForAndroidED.aspx?Gubun=1&SaveType=E&BizNum=217-07-92408&Company=�����λ��̵�&Date=20110309&Credit=����ī��&Cost=1000&Tax=100       
				strGbn = "1";
				strSaveType="E";
				String sURL = "http://www.devinside.kr/AndroidProcess/InValueTaxForAndroidED.aspx?Gubun=1&SaveType=" + strSaveType + "&Id=" + SID;
				strBizNo = "&BizNum=" + etBizNoED.getText().toString();	
				
				try {
					strBizName = URLEncoder.encode(etBizNameED.getText().toString(), "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				strBizName = "&Company=" + strBizName;
				strDate = "&Date=" + strDate;
				
				try {
					strCrecitURL = URLEncoder.encode(strCrecit, "utf-8");
					//Toast.makeText(getBaseContext(), strCrecitURL, 0).show();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				strCrecitURL = "&Credit=" + strCrecitURL;
				strMoney = "&Cost=" + etMoneyED.getText().toString();
				strTax = "&Tax=" + etTaxED.getText().toString();
				String strParam = strBizNo + strBizName + strDate + strCrecitURL + strMoney + strTax;				
				sURL += strParam;
				
				mProgress = ProgressDialog.show(InCreditED.this, "", "������...");
				mThread = new DownThread(sURL);
				mThread.start();			
			}
		});        
        
        btDelED = (Button)findViewById(R.id.btDelED);        
        btDelED.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {	
				strBtnInfo = "�����Ǿ����ϴ�.";
				
				//http://www.devinside.kr/AndroidProcess/InValueTaxForAndroidED.aspx?Gubun=1&SaveType=D&Id=495       
				strGbn = "1";
				strSaveType="D";
				String sURL = "http://www.devinside.kr/AndroidProcess/InValueTaxForAndroidED.aspx?Gubun=1&SaveType=" + strSaveType + "&Id=" + SID;;				
				
				mProgress = ProgressDialog.show(InCreditED.this, "", "������...");
				mThread = new DownThread(sURL);
				mThread.start();			
			}
		});                
    }
    
	////////////////////////////////////////////////////////////////////////////
	
    // �������� �ŷ�ó���� �����´�.
    public void GetCompanyDataParser(String xml){
    	try
    	{
    	     etBizNameED.setText(xml);
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
    	    	Toast.makeText(getBaseContext(), strBtnInfo, 0).show();
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
