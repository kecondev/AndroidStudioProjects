package kr.co.devinside.mylocationmap;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends Activity {

    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        startLocationService();
    }

    /**
     * 현재 위치 확인을 위해 정의한 메소드
     */
    private void startLocationService() {
        // 위치 관리자 객체 참조
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // 리스너 객체 생성
        GPSListener gpsListener = new GPSListener();
        long minTime = 10000;
        float minDistance = 0;

        // GPS 기반 위치 요청
        manager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime,
                minDistance,
                (LocationListener) gpsListener);

        // 네트워크 기반 위치 요청
        manager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                minTime,
                minDistance,
                gpsListener);

        Toast.makeText(getApplicationContext(), "위치 확인 시작함. 로그를 확인하세요.", Toast.LENGTH_SHORT).show();
    }

    /**
     * 리스너 정의
     */
    private class GPSListener implements LocationListener {
        /**
         * 위치 정보가 확인되었을 때 호출되는 메소드
         */
        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            String msg = "Latitude : "+ latitude + "\nLongitude:"+ longitude;
            Log.i("GPSLocationService", msg);

            // 현재 위치의 지도를 보여주기 위해 정의한 메소드 호출
            showCurrentLocation(latitude, longitude);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    }

    /**
     * 현재 위치의 지도를 보여주기 위해 정의한 메소드
     *
     * @param latitude
     * @param longitude
     */
    private void showCurrentLocation(Double latitude, Double longitude) {
        // 현재 위치를 이용해 LatLon 객체 생성
        LatLng curPoint = new LatLng(latitude, longitude);

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));

        // 지도 유형 설정. 지형도인 경우에는 GoogleMap.MAP_TYPE_TERRAIN, 위성 지도인 경우에는 GoogleMap.MAP_TYPE_SATELLITE
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }
}
