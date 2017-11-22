package kmitl.kawin58070006.horyuni.model;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by Kawin on 22/11/2560.
 */

public class Screenshot {

    public static Bitmap takescreenshot(View view){
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public static Bitmap takescreenshotofRootView(View view){
        return  takescreenshot(view.getRootView());
    }
}
