package DevInside.Android.SiteMng;

public class InCreditTextItem {
	private String[] mData;				//Data array
	private boolean mSelectable = true; //True if this item is selectable
	
	/**
	 * Initialize with icon and data array
	 * @param obj
	 */
	public InCreditTextItem(String[] obj) {
		mData = obj;
	}	
	
	//Initialize with icon and strings
	public InCreditTextItem(String obj01, String obj02, String obj03, String obj04, String obj05, String obj06, String obj07, String obj08) {	
		mData = new String[8];
		mData[0] = obj01;
		mData[1] = obj02;
		mData[2] = obj03;
		mData[3] = obj04;
		mData[4] = obj05;
		mData[5] = obj06;
		mData[6] = obj07;
		mData[7] = obj08;		
	}	
	
	//True if this item is selectable
	public boolean isSelectable() {
		return mSelectable;
	}
	
	//Set selectable flag
	public void setSelectable(boolean selectable) {
		mSelectable = selectable;
	}
	
	//Get data array
	public String[] getData() {
		return mData;
	}
	
	//Get data
	public String getData(int index) {
		if (mData == null || index >= mData.length) {
			return null;
		}
		
		return mData[index];
	}
	
	//Set data array
	public void setData(String[] obj) {
		mData = obj;
	}	
	
	//Compare with the input object
	public int compareTo(InCreditTextItem other) {
		if (mData != null) {
			String[] otherData = other.getData();
			if (mData.length == otherData.length) {
				for (int i = 0; i < mData.length; i++) {
					if (!mData[i].equals(otherData[i])) {
						return -1;
					}
				}
			} else {
				return -1;
			}
		} else {
			throw new IllegalArgumentException();
		}
		
		return 0;
	}	
}
