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

public class InCreditMng extends Activity {
	EditText etDate;
	EditText etBizName;
	EditText etBizNo;
	Spinner spCredit;
	EditText etMoney;
	EditText etTax;
    Button btSend;
    Button btValueCompute;
    TextView tvBizName;
    
    private String strDate;
    private String strBizName;
    private String strBizNo;
    private String strCrecit;
    private String strCrecitURL;
    private String strMoney;
    private String strTax;
    
    ArrayAdapter<CharSequence> adspin;
    
	ProgressDialog mProgress;
	DownThread mThread;
	
	private String strGbn="0";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.increditmng);
        
        etDate = (EditText)findViewById(R.id.etDate);
        etBizName = (EditText)findViewById(R.id.etBizName);
        etBizNo = (EditText)findViewById(R.id.etBizNo);
        spCredit = (Spinner)findViewById(R.id.spCredit);
        etMoney = (EditText)findViewById(R.id.etMoney);
        etTax = (EditText)findViewById(R.id.etTax);

        //매입일자에 현재 날짜를 셋팅한다.
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
        
        spCredit.setPrompt("카드사를 선택하세요.");
        adspin = ArrayAdapter.createFromResource(this, R.array.cardname, android.R.layout.simple_spinner_item);
        adspin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCredit.setAdapter(adspin);
        
        spCredit.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
				strCrecit = parent.getItemAtPosition(position).toString();
			}
			public void onNothingSelected(AdapterView<?> arg0) {
						    
			}
        });

        //spCredit.setSelection(2);
        tvBizName = (TextView)findViewById(R.id.tvBizName);
        tvBizName.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
				if(etBizNo.getText().toString().length() < 10){
					Toast.makeText(getBaseContext(), "사업자번호는 10자리 숫자만 입력해주세요.", Toast.LENGTH_LONG).show();
				}
				else{
					strGbn = "0";
					//http://www.devinside.kr/Android/CompanyNameSearch.aspx?BizNum=1011221844
					String sURL = "http://www.devinside.kr/Android/CompanyNameSearch.aspx";
					strBizNo = "?BizNum=" + etBizNo.getText().toString();
					
					sURL += strBizNo;
					mProgress = ProgressDialog.show(InCreditMng.this, "", "데이타를 가져옵니다...");
					mThread = new DownThread(sURL);
					mThread.start();
				}
			}        	
        });
        
        //부가세 계산
        btValueCompute = (Button)findViewById(R.id.btnValueCompute);      
        btValueCompute.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				String strAmt = etMoney.getText().toString();
				Integer iAmt = new Integer(strAmt);
				int iValue = iAmt / 11;
				iAmt = iAmt - iValue;
				
				etMoney.setText(iAmt.toString());
				etTax.setText(Integer.toString(iValue));
			}
		});
		
        //전송
        btSend = (Button)findViewById(R.id.btSend);        
        btSend.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				//매입일자 검증
				strDate = etDate.getText().toString();
				strDate = strDate.replace("-", "").replace(".", "");
				if(strDate.length() != 8){
					etDate.requestFocus();
					etDate.setSelection(0, etDate.length());
					Toast.makeText(getBaseContext(), "매입일자는 ****-**-** 형식으로 입력해주세요.", Toast.LENGTH_SHORT).show();
					return;
				}
										
				//사업자번호 검증
				if(etBizNo.getText().toString().length() < 10){
					etBizNo.requestFocus();
					etBizNo.setSelection(0, etBizNo.length());					
					Toast.makeText(getBaseContext(), "사업자번호는 10자리 숫자만 입력해주세요.", Toast.LENGTH_LONG).show();
					return;
				}
				
				//사업자명 검증
				if(etBizName.getText().toString().length() == 0){
					etBizName.requestFocus();				
					Toast.makeText(getBaseContext(), "사업자명을 입력해주세요.", Toast.LENGTH_LONG).show();
					return;
				}
				
				//과세금액 검증
				if(etMoney.getText().toString().length() == 0){
					etMoney.requestFocus();
					Toast.makeText(getBaseContext(), "과세금액을 입력해주세요.", Toast.LENGTH_LONG).show();
					return;
				}
				
				//부가세액 검증
				if(etTax.getText().toString().length() == 0){
					etTax.requestFocus();
					Toast.makeText(getBaseContext(), "부가세액을 입력해주세요.", Toast.LENGTH_LONG).show();
					return;
				}
				
				strGbn = "1";
				String sURL = "http://www.devinside.kr/AndroidProcess/InValueTaxForAndroid.aspx?Gubun=1";
				strBizNo = "&BizNum=" + etBizNo.getText().toString();	
				
				try {
					//strBizName = new String(etBizName.getText().toString().getBytes("euc-kr"), "8859_1");
					strBizName = URLEncoder.encode(etBizName.getText().toString(), "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				strBizName = "&Company=" + strBizName;
				strDate = "&Date=" + strDate;
				
				try {
					//strCrecit = new String(strCrecit.getBytes("euc-kr"), "8859_1");
					//Toast.makeText(getBaseContext(), strCrecit, 0).show();
					strCrecitURL = URLEncoder.encode(strCrecit, "utf-8");
					//Toast.makeText(getBaseContext(), strCrecitURL, 0).show();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				strCrecitURL = "&Credit=" + strCrecitURL;
				strMoney = "&Cost=" + etMoney.getText().toString();
				strTax = "&Tax=" + etTax.getText().toString();
				String strParam = strBizNo + strBizName + strDate + strCrecitURL + strMoney + strTax;				
				sURL += strParam;
				
				mProgress = ProgressDialog.show(InCreditMng.this, "", "전송중...");
				mThread = new DownThread(sURL);
				mThread.start();
				//Toast.makeText(getBaseContext(), sURL, Toast.LENGTH_LONG).show();				
			}
		});        
    }
    
	////////////////////////////////////////////////////////////////////////////
	
    // 서버에서 거래처명을 가져온다.
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
    
    // 서버에서 내려받은 데이타를 처리한다.
    public void GetDataParser(String xml){
    	try
    	{
    		int iValue = Integer.parseInt(xml);
    	    if(iValue==1)
    	    	Toast.makeText(getBaseContext(), "입력되었습니다.", 0).show();
    	    else
    	    	Toast.makeText(getBaseContext(), "데이타를 확인하세요. 입력되지 않았습니다.", 0).show();  
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
