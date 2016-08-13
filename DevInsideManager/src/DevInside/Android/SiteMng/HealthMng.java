package DevInside.Android.SiteMng;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
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

public class HealthMng extends Activity {
	Button btnSend;
	TextView etHealthDate;
	EditText etWeight;
	EditText etPushUp;
	EditText etSitUp;
	EditText etCycleTime;
	EditText etCycleDist;
	
	TextView tvWeightAvgD;
	TextView tvPushUpAvgD;
	TextView tvCycleDistSumD;
	
	String JSONData = "";
	String URL_SEARCH = "";
	String URL_UPDATE = "";
	
	String URL_KIND = "SEARCH";
	
	ProgressDialog mProgress;
	DownThread mThread;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.healthmng);
        
        URL_SEARCH = "http://www.devinside.kr/Android/HealthMng.aspx?USERID=admin&DATE=%s";        
        
        etHealthDate = (EditText)findViewById(R.id.etHealthDate);
        etWeight = (EditText)findViewById(R.id.etWeight);
        etPushUp = (EditText)findViewById(R.id.etPushUp);
        etSitUp = (EditText)findViewById(R.id.etSitUp);
        etCycleTime = (EditText)findViewById(R.id.etCycleTime);
        etCycleDist = (EditText)findViewById(R.id.etCycleDist);
        
        tvWeightAvgD = (TextView)findViewById(R.id.tvWeightAvgD);
        tvPushUpAvgD = (TextView)findViewById(R.id.tvPushUpAvgD);
        tvCycleDistSumD = (TextView)findViewById(R.id.tvCycleDistSumD);
        
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
        etHealthDate.setText(strY + "-" + strM + "-" + strD);       
        
        URL_SEARCH = String.format(URL_SEARCH, etHealthDate.getText());
        //Toast.makeText(getBaseContext(), URL_SEARCH, Toast.LENGTH_LONG).show();
        
        mProgress = ProgressDialog.show(HealthMng.this, "", "Downloading...");
		mThread = new DownThread(URL_SEARCH);              
		mThread.start();
                
        btnSend = (Button)findViewById(R.id.btnSend);           
        btnSend.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				URL_KIND = "UPDATE";
				URL_UPDATE = "http://www.devinside.kr/Android/HealthMng_Update.aspx?DATE=%s";
				URL_UPDATE = String.format(URL_UPDATE, etHealthDate.getText()) + "&WEIGHT=" + etWeight.getText();
				URL_UPDATE += "&PUSHUP=" + etPushUp.getText();
				URL_UPDATE += "&SITUP=" + etSitUp.getText();
				URL_UPDATE += "&CYCLETIME=" + etCycleTime.getText();
				URL_UPDATE += "&CYCLEDIST=" + etCycleDist.getText();
				mProgress = ProgressDialog.show(HealthMng.this, "", "처리...");
				mThread = new DownThread(URL_UPDATE);
				mThread.start();				
			}
        });        
    }    
    
    //JSON����Ÿ�� �����´�.
    private void getJSONData(String jString) {    	
    	JSONData = jString;
    	//JSONData = "[{"date":"2013-06-09", "weight":"64.2", "pushup":"0", "situp":"0", "cycletime":"0", "cycledist":"0", "weightavg":"63.6", "pushupcntavg":"38", "cycledistsum":"183.88"}]";
    	setJSONData(JSONData);
    	
    	//4.0.3�������� ���ܵ�
    	//mThread.stop();
    }
        
    //JSON����Ÿ�� �����Ѵ�.
    private void setJSONData(String jString) {
    	try {
			JSONArray jsonArray = new JSONArray(jString);
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			
			etHealthDate.setText(jsonObject.getString("date"));
			etWeight.setText(jsonObject.getString("weight"));
			etPushUp.setText(jsonObject.getString("pushup"));
			etSitUp.setText(jsonObject.getString("situp"));
			etCycleTime.setText(jsonObject.getString("cycletime"));
			etCycleDist.setText(jsonObject.getString("cycledist"));
			
			tvWeightAvgD.setText(" : " + jsonObject.getString("weightavg"));
			tvPushUpAvgD.setText(" : " + jsonObject.getString("pushupcntavg"));
			tvCycleDistSumD.setText(" : " + jsonObject.getString("cycledistsum"));
				
		} catch (JSONException e) {
			Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();;
		}    
    }
    
    private void getUpdateResult(String result) {    	
    	if(result.trim().equals("1"))
    	{
    		Toast.makeText(getBaseContext(), R.string.healthmng_save_success, Toast.LENGTH_SHORT).show();
    		
            //mProgress = ProgressDialog.show(HealthMng.this, "", "Downloading...");
    		mThread = new DownThread(URL_SEARCH);              
    		mThread.start();
    	}
    	else
    		Toast.makeText(getBaseContext(), R.string.healthmng_save_fail, Toast.LENGTH_SHORT).show();
    	
    	URL_KIND = "SEARCH";
    			
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
	
	@SuppressLint("HandlerLeak")
	Handler mAfterDown = new Handler() {
		public void handleMessage(Message msg) {
			mProgress.dismiss();
			if(URL_KIND=="SEARCH")
				getJSONData(mThread.mResult);
			else
				getUpdateResult(mThread.mResult);
		}
	};	    
}
