package DevInside.Android.SiteMng;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class QuaterList extends Activity {
	private static final int INVISIBLE = 0x00000004;
	private static final int VISIBLE = 0x00000000;
	EditText etYear;
	Spinner spHalf;
	Button btQuater;
	
	LinearLayout lya1;
	LinearLayout.LayoutParams lpa1;
	LinearLayout lya2;
	LinearLayout.LayoutParams lpa2;
	LinearLayout lya3;
	LinearLayout.LayoutParams lpa3;
	LinearLayout lyas;
	LinearLayout.LayoutParams lpas;
	LinearLayout lyb1;
	LinearLayout.LayoutParams lpb1;
	LinearLayout lyb2;
	LinearLayout.LayoutParams lpb2;
	LinearLayout lyb3;
	LinearLayout.LayoutParams lpb3;
	LinearLayout lybs;
	LinearLayout.LayoutParams lpbs;
	LinearLayout lys;
	LinearLayout.LayoutParams lps;
	
	TextView tvAFQ;
	TextView tvAFK;
	TextView tvAFM;
	TextView tvAFV;
	TextView tvAFS;
	TextView tvASQ;
	TextView tvASK;
	TextView tvASM;
	TextView tvASV;
	TextView tvASS;
	TextView tvATQ;
	TextView tvATK;
	TextView tvATM;
	TextView tvATV;
	TextView tvATS;
	TextView tvASTQ;
	TextView tvASTK;
	TextView tvASTM;
	TextView tvASTV;
	TextView tvASTS;
	TextView tvBFQ;
	TextView tvBFK;
	TextView tvBFM;
	TextView tvBFV;
	TextView tvBFS;
	TextView tvBSQ;
	TextView tvBSK;
	TextView tvBSM;
	TextView tvBSV;
	TextView tvBSS;
	TextView tvBTQ;
	TextView tvBTM;
	TextView tvBTK;
	TextView tvBTV;
	TextView tvBTS;
	TextView tvBSTQ;
	TextView tvBSTM;
	TextView tvBSTK;
	TextView tvBSTV;
	TextView tvBSTS;
	TextView tvSTQ;
	TextView tvSTM;
	TextView tvSTK;
	TextView tvSTV;
	TextView tvSTS;
	
	private String strHalf="";
	private String strHalfCode="";
	
	ArrayAdapter<CharSequence> adspin;
	
	ProgressDialog mProgress;
	DownThread mThread;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quaterlist);
        
        etYear = (EditText)findViewById(R.id.etYear);
        spHalf = (Spinner)findViewById(R.id.spQuater);
        btQuater = (Button)findViewById(R.id.btSearchQ);             
        
        lya1 = (LinearLayout)findViewById(R.id.lya1);    
        lya1.setVisibility(INVISIBLE);
        lpa1 = (LinearLayout.LayoutParams) lya1.getLayoutParams();
        lya2 = (LinearLayout)findViewById(R.id.lya2);    
        lya2.setVisibility(INVISIBLE);
        lpa2 = (LinearLayout.LayoutParams) lya2.getLayoutParams();
        lya3 = (LinearLayout)findViewById(R.id.lya3);    
        lya3.setVisibility(INVISIBLE);
        lpa3 = (LinearLayout.LayoutParams) lya3.getLayoutParams();
        lyas = (LinearLayout)findViewById(R.id.lyas);    
        lyas.setVisibility(INVISIBLE);        
        lpas = (LinearLayout.LayoutParams) lyas.getLayoutParams();
        lyb1 = (LinearLayout)findViewById(R.id.lyb1);    
        lyb1.setVisibility(INVISIBLE);
        lpb1 = (LinearLayout.LayoutParams) lyb1.getLayoutParams();
        lyb2 = (LinearLayout)findViewById(R.id.lyb2);    
        lyb2.setVisibility(INVISIBLE);
        lpb2 = (LinearLayout.LayoutParams) lyb2.getLayoutParams();
        lyb3 = (LinearLayout)findViewById(R.id.lyb3);    
        lyb3.setVisibility(INVISIBLE);
        lpb3 = (LinearLayout.LayoutParams) lyb3.getLayoutParams();
        lybs = (LinearLayout)findViewById(R.id.lybs);    
        lybs.setVisibility(INVISIBLE);
        lpbs = (LinearLayout.LayoutParams) lybs.getLayoutParams();
        lys = (LinearLayout)findViewById(R.id.lys);    
        lys.setVisibility(INVISIBLE); 
        lps = (LinearLayout.LayoutParams) lys.getLayoutParams();
        
    	tvAFQ = (TextView)findViewById(R.id.tvAFQ);
    	tvAFK = (TextView)findViewById(R.id.tvAFK);
    	tvAFM = (TextView)findViewById(R.id.tvAFM);
    	tvAFV = (TextView)findViewById(R.id.tvAFV);
    	tvAFS = (TextView)findViewById(R.id.tvAFS);
    	
    	tvASQ = (TextView)findViewById(R.id.tvASQ);
    	tvASK = (TextView)findViewById(R.id.tvASK);
    	tvASM = (TextView)findViewById(R.id.tvASM);
    	tvASV = (TextView)findViewById(R.id.tvASV);
    	tvASS = (TextView)findViewById(R.id.tvASS);
    	
    	tvATQ = (TextView)findViewById(R.id.tvATQ);
    	tvATK = (TextView)findViewById(R.id.tvATK);
    	tvATM = (TextView)findViewById(R.id.tvATM);
    	tvATV = (TextView)findViewById(R.id.tvATV);
    	tvATS = (TextView)findViewById(R.id.tvATS);

    	tvASTQ = (TextView)findViewById(R.id.tvASTQ);
    	tvASTK = (TextView)findViewById(R.id.tvASTK);
    	tvASTM = (TextView)findViewById(R.id.tvASTM);
    	tvASTV = (TextView)findViewById(R.id.tvASTV);
    	tvASTS = (TextView)findViewById(R.id.tvASTS);
    	
    	tvBFQ = (TextView)findViewById(R.id.tvBFQ);
    	tvBFK = (TextView)findViewById(R.id.tvBFK);
    	tvBFM = (TextView)findViewById(R.id.tvBFM);
    	tvBFV = (TextView)findViewById(R.id.tvBFV);
    	tvBFS = (TextView)findViewById(R.id.tvBFS);
    	
    	tvBSQ = (TextView)findViewById(R.id.tvBSQ);
    	tvBSK = (TextView)findViewById(R.id.tvBSK);
    	tvBSM = (TextView)findViewById(R.id.tvBSM);
    	tvBSV = (TextView)findViewById(R.id.tvBSV);
    	tvBSS = (TextView)findViewById(R.id.tvBSS);
    	
    	tvBTQ = (TextView)findViewById(R.id.tvBTQ);
    	tvBTK = (TextView)findViewById(R.id.tvBTK);
    	tvBTM = (TextView)findViewById(R.id.tvBTM);
    	tvBTV = (TextView)findViewById(R.id.tvBTV);
    	tvBTS = (TextView)findViewById(R.id.tvBTS);
    	
    	tvBSTQ = (TextView)findViewById(R.id.tvBSTQ);
    	tvBSTK = (TextView)findViewById(R.id.tvBSTK);
    	tvBSTM = (TextView)findViewById(R.id.tvBSTM);
    	tvBSTV = (TextView)findViewById(R.id.tvBSTV);
    	tvBSTS = (TextView)findViewById(R.id.tvBSTS);
    	
    	tvSTQ = (TextView)findViewById(R.id.tvSTQ);
    	tvSTK = (TextView)findViewById(R.id.tvSTK);
    	tvSTM = (TextView)findViewById(R.id.tvSTM);
    	tvSTV = (TextView)findViewById(R.id.tvSTV);
    	tvSTS = (TextView)findViewById(R.id.tvSTS);
    	
        //매입일자에 현재 날짜를 셋팅한다.
        Calendar c = Calendar.getInstance();
        int nYear = c.get(Calendar.YEAR);
        String strY = Integer.toString(nYear);
        etYear.setText(strY);
        
        spHalf.setPrompt("반기를 선택하세요.");
        adspin = ArrayAdapter.createFromResource(this, R.array.halfyearname, android.R.layout.simple_spinner_item);
        adspin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spHalf.setAdapter(adspin);
        spHalf.setSelection(0);
        
        spHalf.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
				strHalf = parent.getItemAtPosition(position).toString();
			}
			public void onNothingSelected(AdapterView<?> arg0) {
						    
			}
        });
        
        btQuater.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				if(etYear.getText().length() < 4){
					Toast.makeText(getBaseContext(),  "년도는 숫자 4자리입니다.", Toast.LENGTH_SHORT).show();
					return;
				}	
				
				if(strHalf.indexOf("전반기") > -1){
					strHalfCode = etYear.getText() + "1" + etYear.getText() + "2";
				}else if(strHalf.indexOf("후반기") > -1){
					strHalfCode = etYear.getText() + "3" + etYear.getText() + "4";
				}else{
					strHalfCode = "";
				}
				
				String sURL = "http://www.devinside.kr/Android/ValueTaxSummary.aspx";
				String sQuater = "?QUATER=" + strHalfCode;
				
				sURL += sQuater;
				mProgress = ProgressDialog.show(QuaterList.this, "", "데이타를 가져옵니다...");
				mThread = new DownThread(sURL);
				mThread.start();
			}
		});          
    }

    public void SetClearView()
    {
		tvAFK.setText("");
		tvAFM.setText("");
		tvAFV.setText("");
		tvAFS.setText("");
		tvASK.setText("");
		tvASM.setText("");
		tvASV.setText("");
		tvASS.setText("");		
		tvATK.setText("");
		tvATM.setText("");
		tvATV.setText("");
		tvATS.setText("");	
		tvASTK.setText("");
		tvASTM.setText("");
		tvASTV.setText("");
		tvASTS.setText("");		
		tvBFK.setText("");
		tvBFM.setText("");
		tvBFV.setText("");
		tvBFS.setText("");
		tvBSK.setText("");
		tvBSM.setText("");
		tvBSV.setText("");
		tvBSS.setText("");		
		tvBTK.setText("");
		tvBTM.setText("");
		tvBTV.setText("");
		tvBTS.setText("");    
		tvBSTK.setText("");
		tvBSTM.setText("");
		tvBSTV.setText("");
		tvBSTS.setText("");	
		tvSTK.setText("");
		tvSTM.setText("");
		tvSTV.setText("");
		tvSTS.setText("");	
		
        lya1.setVisibility(INVISIBLE);        
        lya2.setVisibility(INVISIBLE);    
        lya3.setVisibility(INVISIBLE);
        lyas.setVisibility(INVISIBLE); 
        lyb1.setVisibility(INVISIBLE);    
        lyb2.setVisibility(INVISIBLE);    
        lyb3.setVisibility(INVISIBLE);
        lybs.setVisibility(INVISIBLE);
        lys.setVisibility(INVISIBLE); 
        
        lpa1.height =0;
        lpa1.topMargin = 0;
        lpa2.height =0;
        lpa2.topMargin = 0;
        lpa3.height =0;
        lpa3.topMargin = 0;
        lpas.height =0;
        lpas.topMargin = 0;
        lpb1.height =0;
        lpb1.topMargin = 0;
        lpb2.height =0;
        lpb2.topMargin = 0;
        lpb3.height =0;
        lpb3.topMargin = 0;
        lpbs.height =0;
        lpbs.topMargin = 0;
        lps.height =0;
        lps.topMargin = 0;
    }
    
    public void GetDataParser(String xml){
    	try
    	{	
    		SetClearView();
    		
    	    //Toast.makeText(getBaseContext(), xml, 1).show();  
    		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputStream istream = new ByteArrayInputStream(xml.getBytes("utf-8"));
			Document doc = builder.parse(istream);
			
			org.w3c.dom.Element order = doc.getDocumentElement();
			NodeList items = order.getElementsByTagName("VALUE");
			//int iC = items.getLength();
			//Toast.makeText(getBaseContext(), Integer.toString(iC), 0).show();
			int iSumA = 0;
			int iSumB = 0;
			int iSumC = 0;
			int iSumD = 0;
			int iSumE = 0;
			int iSumF = 0;
			
	        DecimalFormat df = new DecimalFormat("#,##0");
	        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
	        dfs.setGroupingSeparator(',');
	        df.setGroupingSize(3);
	        df.setDecimalFormatSymbols(dfs);  
	        
			for(int i=0; i<items.getLength();i++){
				org.w3c.dom.Node item = items.item(i);
				NamedNodeMap Attrs = item.getAttributes();
				//int iC = Attrs.getLength();
				//Toast.makeText(getBaseContext(), Integer.toString(iC), 0).show();				
				String[] arrStr = new String[5];
				for(int j=0; j < Attrs.getLength(); j++){
					org.w3c.dom.Node attr = Attrs.item(j);
					arrStr[j] = attr.getNodeValue();					
				}				
				//Toast.makeText(getBaseContext(), arrStr[0].substring(4, 5), 0).show();
				
				if(arrStr[0].substring(4, 5).indexOf("1") > -1 || arrStr[0].substring(4, 5).indexOf("3") > -1){
					//Toast.makeText(getBaseContext(), arrStr[1], 0).show();	
					lyas.setVisibility(VISIBLE);
					lyas.setBackgroundColor(Color.parseColor("#777777"));
					lpas.height = 150;
					lpas.topMargin = 3;					
					//Toast.makeText(getBaseContext(), Integer.toString(lyas.getHeight()), 0).show();;
					tvASTQ.setText(arrStr[0].substring(0, 4) + "년 " + arrStr[0].substring(4, 5) + "분기");
					tvASTK.setText("부가세 합계 금액");
					
					lys.setVisibility(VISIBLE);
					lys.setBackgroundColor(Color.parseColor("#999999"));
					lps.height = 150;
					lps.topMargin = 3;
					tvSTQ.setText(arrStr[0].substring(0, 4) + "년 " + strHalf);
					tvSTK.setText("부가세 합계 금액");
							
					if(arrStr[1].indexOf("매입신용카드") > -1){
						lya1.setVisibility(VISIBLE);
						lpa1.height = 150;
						lpa1.topMargin = 3;
						tvAFQ.setText(arrStr[0].substring(0, 4) + "년 " + arrStr[0].substring(4, 5) + "분기");
						tvAFK.setText("종류 : " + arrStr[1]);
						tvAFM.setText("과세계 : " + arrStr[2]);
						tvAFV.setText("부가세계 : " + arrStr[3]);
						tvAFS.setText("합계 : " + arrStr[4]);
						iSumA += Integer.parseInt(arrStr[2].replace(",", ""));
						iSumB += Integer.parseInt(arrStr[3].replace(",", ""));
						iSumC += Integer.parseInt(arrStr[4].replace(",", ""));						
					} else if(arrStr[1].indexOf("매입세금계산서") > -1){
						lya2.setVisibility(VISIBLE);
						lpa2.height = 150;
						lpa2.topMargin = 3;
						tvASQ.setText(arrStr[0].substring(0, 4) + "년 " + arrStr[0].substring(4, 5) + "분기");
						tvASK.setText("종류 : " + arrStr[1]);
						tvASM.setText("과세계 : " + arrStr[2]);
						tvASV.setText("부가세계 : " + arrStr[3]);
						tvASS.setText("합계 : " + arrStr[4]);			
						iSumA += Integer.parseInt(arrStr[2].replace(",", ""));
						iSumB += Integer.parseInt(arrStr[3].replace(",", ""));
						iSumC += Integer.parseInt(arrStr[4].replace(",", ""));						
					} else if(arrStr[1].indexOf("매출세금계산서") > -1){
						lya3.setVisibility(VISIBLE);
						lpa3.height = 150;
						lpa3.topMargin = 3;
						tvATQ.setText(arrStr[0].substring(0, 4) + "년 " + arrStr[0].substring(4, 5) + "분기");
						tvATK.setText("종류 : " + arrStr[1]);
						tvATM.setText("과세 : " + arrStr[2]);
						tvATV.setText("부가세 : " + arrStr[3]);
						tvATS.setText("합계 : " + arrStr[4]);			
						iSumA -= Integer.parseInt(arrStr[2].replace(",", ""));
						iSumB -= Integer.parseInt(arrStr[3].replace(",", ""));
						iSumC -= Integer.parseInt(arrStr[4].replace(",", ""));						
					}					
				}
				else if(arrStr[0].substring(4, 5).indexOf("2") > -1 || arrStr[0].substring(4, 5).indexOf("4") > -1){
					lybs.setVisibility(VISIBLE);
					lybs.setBackgroundColor(Color.parseColor("#777777"));
					lpbs.height = 150;
					lpbs.topMargin = 3;
					tvBSTQ.setText(arrStr[0].substring(0, 4) + "년 " + arrStr[0].substring(4, 5) + "분기");
					tvBSTK.setText("부가세 합계 금액");
					
					if(arrStr[1].indexOf("매입신용카드") > -1){
						lyb1.setVisibility(VISIBLE);
						lpb1.height = 150;
						lpb1.topMargin = 3;
						tvBFQ.setText(arrStr[0].substring(0, 4) + "년 " + arrStr[0].substring(4, 5) + "분기");
						tvBFK.setText("종류 : " + arrStr[1]);
						tvBFM.setText("과세계 : " + arrStr[2]);
						tvBFV.setText("부가세계 : " + arrStr[3]);
						tvBFS.setText("합계 : " + arrStr[4]);
						iSumD += Integer.parseInt(arrStr[2].replace(",", ""));
						iSumE += Integer.parseInt(arrStr[3].replace(",", ""));
						iSumF += Integer.parseInt(arrStr[4].replace(",", ""));							
					} else if(arrStr[1].indexOf("매입세금계산서") > -1){
						lyb2.setVisibility(VISIBLE);
						lpb2.height = 150;
						lpb2.topMargin = 3;
						tvBSQ.setText(arrStr[0].substring(0, 4) + "년 " + arrStr[0].substring(4, 5) + "분기");
						tvBSK.setText("종류 : " + arrStr[1]);
						tvBSM.setText("과세계 : " + arrStr[2]);
						tvBSV.setText("부가세계 : " + arrStr[3]);
						tvBSS.setText("합계 : " + arrStr[4]);				
						iSumD += Integer.parseInt(arrStr[2].replace(",", ""));
						iSumE += Integer.parseInt(arrStr[3].replace(",", ""));
						iSumF += Integer.parseInt(arrStr[4].replace(",", ""));							
					} else if(arrStr[1].indexOf("매출세금계산서") > -1){
						lyb3.setVisibility(VISIBLE);
						lpb3.height = 150;
						lpb3.topMargin = 3;
						tvBTQ.setText(arrStr[0].substring(0, 4) + "년 " + arrStr[0].substring(4, 5) + "분기");
						tvBTK.setText("종류 : " + arrStr[1]);
						tvBTM.setText("과세 : " + arrStr[2]);
						tvBTV.setText("부가세 : " + arrStr[3]);
						tvBTS.setText("합계 : " + arrStr[4]);			
						iSumD -= Integer.parseInt(arrStr[2].replace(",", ""));
						iSumE -= Integer.parseInt(arrStr[3].replace(",", ""));
						iSumF -= Integer.parseInt(arrStr[4].replace(",", ""));							
					}					
				}			
			}			  
	        
			if(lyas.getVisibility() == VISIBLE){
				tvASTM.setText("과세 : " + df.format(iSumA).toString());
				tvASTV.setText("부가세 : " + df.format(iSumB).toString());
				tvASTS.setText("합계 : " + df.format(iSumC).toString());
			}
			
			if(lybs.getVisibility() == VISIBLE){
				tvBSTM.setText("과세 : " + df.format(iSumD).toString());
				tvBSTV.setText("부가세 : " + df.format(iSumE).toString());
				tvBSTS.setText("합계 : " + df.format(iSumF).toString());
			}
			
			if(lyas.getVisibility() == VISIBLE){
				tvSTM.setText("과세 : " + df.format(iSumA + iSumD).toString());
				tvSTV.setText("부가세 : " + df.format(iSumB + iSumE).toString());
				tvSTS.setText("합계 : " + df.format(iSumC + iSumF).toString());
			}
			
    	}
    	catch(Exception e)
    	{    	
    		Toast.makeText(getBaseContext(), e.getMessage(), 0).show();
    	}   	
    	finally
    	{    		
    		//mThread.stop();
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
			GetDataParser(mThread.mResult);			
		}
	};	    
}
