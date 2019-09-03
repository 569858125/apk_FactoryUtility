package th.dtv;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class DtvService extends Service {
	private static final String TAG = "DtvService";
	private boolean threadDisable;
	// private int test_data = 0;

	private mw_data.service tmpServiceInfo;

	public static final String DTV_PLAYER_ACTION = "com.dtv.player";
	public static final String DTV_STOP_ACTION = "com.dtv.stop";
	public static final String DTV_PLAY_STATUS_ACTION = "com.dtv.play.status";
	public static final String SHOW_DIALOG = "bookevent_showtimerdialog";
	private static final String DTV_UPDATE_TIMEZONE_ACTION = "th.dtv.timezone";

	public static final String DTV_PASSWORD_PASS_ACTION = "com.dtv.pswd.ok";
	public static final String DTV_SERVICE_TYPE = "com.dtv.service_type";

	static {

		System.loadLibrary("th_dtv_mw");
	}

	public static native int register_dtv_service_cb(Object i_clazz);

	@Override
	public void onCreate() {
		super.onCreate();

//		SETTINGS.initAudio(getApplicationContext(), false);
		register_dtv_service_cb(this);

		IntentFilter filter_mute = new IntentFilter();

		filter_mute.addAction(DTV_PLAYER_ACTION);
		filter_mute.addAction(DTV_STOP_ACTION);
		filter_mute.addAction(DTV_PLAY_STATUS_ACTION);
		filter_mute.addAction(DTV_PASSWORD_PASS_ACTION);
		filter_mute.addAction(Intent.ACTION_SCREEN_OFF);
		filter_mute.addAction(DTV_UPDATE_TIMEZONE_ACTION);
		filter_mute.setPriority(999);



		Log.e(TAG, " onCreate ");
	}

	// mw_data.book_callback_event_data g_paramsInfo =null;
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	
	@Override
	public void onDestroy() {
		super.onDestroy();

		Log.e(TAG, " onDestroy ");
	}
}
