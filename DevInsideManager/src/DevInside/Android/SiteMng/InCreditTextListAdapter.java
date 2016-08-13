package DevInside.Android.SiteMng;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class InCreditTextListAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<InCreditTextItem> mItems = new ArrayList<InCreditTextItem>();

	public InCreditTextListAdapter(Context context) {
		mContext = context;
	}
	
	public void addItem(InCreditTextItem it) {
		mItems.add(it);
	}

	public void setListItems(List<InCreditTextItem> lit) {
		mItems = lit;
	}

	public int getCount() {
		return mItems.size();
	}

	public Object getItem(int position) {
		return mItems.get(position);
	}

	public boolean areAllItemsSelectable() {
		return false;
	}

	public boolean isSelectable(int position) {
		try {
			return mItems.get(position).isSelectable();
		} catch (IndexOutOfBoundsException ex) {
			return false;
		}
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		InCreditTextView itemView;
		
		if (convertView == null) {
			itemView = new InCreditTextView(mContext, mItems.get(position));
		} else {
			itemView = (InCreditTextView) convertView;
			
			itemView.setText(0, mItems.get(position).getData(0));
			itemView.setText(1, mItems.get(position).getData(1));
			itemView.setText(2, mItems.get(position).getData(2));
			itemView.setText(3, mItems.get(position).getData(3));
			itemView.setText(4, mItems.get(position).getData(4));
			itemView.setText(5, mItems.get(position).getData(5));
			itemView.setText(6, mItems.get(position).getData(6));
			itemView.setText(7, mItems.get(position).getData(7));			
		}

		return itemView;
	}	
}
