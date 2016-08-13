package DevInside.Android.SiteMng;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InCreditTextView extends LinearLayout{
	private TextView tvQuater;
	private TextView tvCredit;
	private TextView tvBizNo;
	private TextView tvDate;
	private TextView tvBizName;
	private TextView tvMoney;
	private TextView tvTax;
	private TextView tvSum;
	
	private InCreditTextItem aItem;
	
	public InCreditTextView(Context context, InCreditTextItem aItem) {
		super(context);
		
		//Layout Inflation
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
		inflater.inflate(R.layout.increditlist, this, true);	
		
		//분기(0) 신용카드(1) 사업자번호(2) 매입일자(3) 사업자명(4) 과세금액(5) 부가세(6) 키(7)
		// 분기
		tvQuater = (TextView) findViewById(R.id.dataQuater);
		tvQuater.setText(aItem.getData(0));		
		// 신용카드
		tvCredit = (TextView) findViewById(R.id.dataCredit);
		if(aItem.getData(1).length() == 0){
			tvCredit.setText(aItem.getData(1));
			tvCredit.setVisibility(INVISIBLE);
		} else {
			//tvCredit.setText(aItem.getData(1));
			tvCredit.setText(aItem.getData(2));
		}
		// 사업자번호
		tvBizNo = (TextView) findViewById(R.id.dataBizNo);
		//tvBizNo.setText(aItem.getData(2));	
		tvBizNo.setText(aItem.getData(4));
		// 매입일자
		tvDate = (TextView) findViewById(R.id.dataDate);
		tvDate.setText(aItem.getData(3));		
		// 사업자명
		tvBizName = (TextView) findViewById(R.id.dataBizName);
		//tvBizName.setText(aItem.getData(4));
		tvBizName.setText(aItem.getData(1));
		// 과세금액
		tvMoney = (TextView) findViewById(R.id.dataMoney);
		tvMoney.setText(aItem.getData(5));	
		// 부가세
		tvTax = (TextView) findViewById(R.id.dataTax);
		tvTax.setText(aItem.getData(6));				
		// 키
		tvSum = (TextView) findViewById(R.id.dataSum);
		tvSum.setText(aItem.getData(7));	
		tvSum.setVisibility(INVISIBLE);
		
		this.aItem = aItem;
	}	
	
	public void setText(int index, String data) {
		if (index == 0) {
			tvQuater.setText(data);
		} else if (index == 2) {
			tvCredit.setText(data);
		} else if (index == 4) {
			tvBizNo.setText(data);
		} else if (index == 3) {
			tvDate.setText(data);
		} else if (index == 1) {
			tvBizName.setText(data);
		} else if (index == 5) {
			tvMoney.setText(data);
		} else if (index == 6) {
			tvTax.setText(data);
		} else if (index == 7) {
			tvSum.setText(data);	
		} else {
			//throw new IllegalArgumentException();
		}
	}
	
	public TextView getText1()
	{
		return tvBizName;
	}
	
	public InCreditTextItem getInCreditTextItem()
	{
		return aItem;
	}
}
