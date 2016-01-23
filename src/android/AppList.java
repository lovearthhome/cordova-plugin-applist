//参考：http://blog.csdn.net/lancees/article/details/7616735
//参考：http://grokbase.com/t/gg/tasker/151nst55me/about-java-cell-ids-lac-mcc-mnc-gsm-cdma-lte-wcdma-and-all-that-funny-stuff-how-to-brew-your-own

package com.ict.yimingyu;
import org.apache.cordova.CallbackContext;  
import org.apache.cordova.CordovaPlugin;  
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException; 
import java.util.ArrayList;
import java.util.List;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.BitmapDrawable;
import java.io.ByteArrayOutputStream;
import android.util.Base64;

public class AppList extends CordovaPlugin {
	public static final String ACTION_APPLIST = "getAppList";

	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		if (ACTION_APPLIST.equals(action)) {
			PackageManager pm=(PackageManager) cordova.getActivity().getPackageManager();
			List<PackageInfo> ps = pm.getInstalledPackages(0);					
			JSONArray ja = new JSONArray();

            for (PackageInfo p : ps) {
					JSONObject jo=new JSONObject();
					String aname=p.applicationInfo.loadLabel(pm).toString();
					jo.put("aname",aname==null?"na":aname);
//					Bitmap bitmap=((BitmapDrawable)p.applicationInfo.loadIcon(pm)).getBitmap();
//					jo.put("icon",encodeToBase64(bitmap)); //这个代码可以工作，但是图片数据量太大
					jo.put("pname",p.packageName);
					jo.put("version",p.versionName==null?"na":p.versionName);
					jo.put("code",p.versionCode);
					jo.put("system",((p.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1)?1:0);
					ja.put(jo);
            }
			callbackContext.success(ja);
			return true;
		}
		callbackContext.error("failure");
		return false;
	}

	public static String encodeToBase64(Bitmap image)
	{
		Bitmap immagex=Bitmap.createScaledBitmap(image, 16, 16, false);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		immagex.compress(Bitmap.CompressFormat.JPEG, 75/*ignored for PNG*/, baos);
		byte[] b = baos.toByteArray();
		String imageEncoded	= Base64.encodeToString(b,Base64.DEFAULT);
		return imageEncoded;
	}
	public static Bitmap decodeBase64(String input)	
	{
		byte[] decodedByte = Base64.decode(input, 0);
		return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length); 
	}
}