package DevInside.Android.SiteMng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class DevInsideManager extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ImageButton ibtnCreate = (ImageButton)findViewById(R.id.btnCreditM);
        ibtnCreate.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
	           	 Intent mCredit = new Intent(getApplicationContext(), InCreditMng.class);
	           	 startActivity(mCredit);
			}
		});
        
        ImageButton ibtnTax = (ImageButton)findViewById(R.id.btnTaxM);
        ibtnTax.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
	           	 Intent mTax = new Intent(getApplicationContext(), InTaxMng.class);
		         startActivity(mTax);
			}
		});
        
        ImageButton ibtnCreditList = (ImageButton)findViewById(R.id.btnCreditList);
        ibtnCreditList.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
	           	 Intent mTax = new Intent(getApplicationContext(), InCreditList.class);
		         startActivity(mTax);
			}
		});     
        
        ImageButton ibtnTaxList = (ImageButton)findViewById(R.id.btnTaxList);
        ibtnTaxList.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
	           	 Intent mTax = new Intent(getApplicationContext(), InTaxList.class);
		         startActivity(mTax);
			}
		});       
        
        ImageButton ibtnQuaterInfo = (ImageButton)findViewById(R.id.btnQuaterInfo);
        ibtnQuaterInfo.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
	           	 Intent mQuater = new Intent(getApplicationContext(), QuaterList.class);
		         startActivity(mQuater);
			}
		});  
        
        ImageButton ibtnVisitCnt = (ImageButton)findViewById(R.id.btnVisitCnt);
        ibtnVisitCnt.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
	           	 Intent mQuater = new Intent(getApplicationContext(), HealthMng.class);
		         startActivity(mQuater);
			}
		});         
    }
}