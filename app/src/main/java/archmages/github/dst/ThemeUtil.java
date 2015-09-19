package archmages.github.dst;

import android.content.Context;
import android.util.Log;
import android.util.SparseIntArray;
import java.lang.reflect.Field;
import java.util.HashMap;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:lmyu@wisorg.com">David.Yu</a>
 * @version V1.0, 19/9/15
 */
@EBean(scope = EBean.Scope.Singleton)
public class ThemeUtil {

    @Pref
    DefaultPreference_ defaultPreference;

    private static final String TAG = "ThemeUtil";

    static HashMap<String, Integer> resourceHashMap = new HashMap<>();

    // define the using resource class & name array
    public static Class drawableClass = null;
    public static Class colorClass = null;
    public static String[] drawableResource;
    public static String[] colorResource;

    public static ThemeMode ApplicationThemeMode;

    // this is base to match mode resource
    public static String BASE_MATCH = ".*";

    public enum ThemeMode {
        LIGHT("_l", 0), NIGHT("_n", 1) , DUSK("_d", 2) ;

        private String suffix;
        private int status;
        private SparseIntArray mapResource = new SparseIntArray();

        ThemeMode(String suffix, int status) {
            this.suffix = suffix;
            this.status = status;
        }

        /**
         * need to init
         */
        public void dealMapResource() {
            dealThemeModeMap(this, this.mapResource);
        }

        public static String getSuffix(int status) {
            for (ThemeMode t : ThemeMode.values()) {
                if (t.getStatus() == status) {
                    return t.suffix;
                }
            }
            return "";
        }

        public String getSuffix() {
            return suffix;
        }

        public int getSuffixLength() {
            return suffix.length();
        }

        public int getStatus() {
            return status;
        }

        public int getId(int id) {
            return this.mapResource.get(id);
        }
    }

    public ThemeUtil(Context context) {
        initResource();
    }

    public void switchTheme(ThemeMode theme) {
        defaultPreference.lastThemeMode().put(theme.getStatus());
    }

    public ThemeMode getLastTheme(){
        switch (defaultPreference.lastThemeMode().get()){
            case 0:
                return ThemeMode.LIGHT;
            case 1:
                return ThemeMode.NIGHT;
            case 2:
                return ThemeMode.DUSK;
            default:
                return ThemeMode.LIGHT;
        }
    }

    /**
     * init get the resource & create theme mode map
     */
    public void initResource() {
        ApplicationThemeMode = ThemeMode.LIGHT;

        drawableClass = R.drawable.class;
        colorClass = R.color.class;
        StringBuffer modeMatch = new StringBuffer(BASE_MATCH);
        // get the default Theme mode resource
        modeMatch.append(ThemeMode.getSuffix(0));
        modeMatch.append("$");
        drawableResource = getModeMatch(drawableClass, modeMatch.toString());
        colorResource = getModeMatch(colorClass, modeMatch.toString());

        for (ThemeMode t : ThemeMode.values()) {
            t.dealMapResource();
        }
    }

    public String[] getModeMatch(Class tClass, String modeMatch) {
        String temp = null;
        Field[] field = tClass.getDeclaredFields();
        String[] themeResource;
        int trNumber = 0;
        for (int i = 0; i < field.length; i++) {
            temp = field[i].getName();
            if (temp.matches(modeMatch)) {
                trNumber++;
            }
        }
        for (int i = 0; i < field.length; i++) {
            resourceHashMap.put(field[i].getName(), 1);
        }
        themeResource = new String[trNumber];
        trNumber = 0;
        for (int i = 0; i < field.length; i++) {
            temp = field[i].getName();
            if (temp.matches(modeMatch)) {
                themeResource[trNumber] = temp;
                trNumber++;
            }
        }

        Log.i(TAG, "match resource name");
        for (int i = 0; i < themeResource.length; i++) {
            Log.i(TAG, themeResource[i]);
        }
        return themeResource;

    }

    public static void dealThemeModeMap(ThemeMode mode,
            SparseIntArray mapResource) {
        dealResourceMap(drawableClass, drawableResource, mode, mapResource);
        dealResourceMap(colorClass, colorResource, mode, mapResource);
    }

    public static void dealResourceMap(Class resourceClass,
            String[] matchResource, ThemeMode mode, SparseIntArray mapResource) {
        Field defaultField = null;
        Field toField = null;
        int len = 0;
        String base;
        StringBuffer newThemeResource;
        for (int i = 0; i < matchResource.length; i++) {
            len = matchResource[i].length();
            base = matchResource[i].substring(0,
                    len - ApplicationThemeMode.getSuffixLength());
            newThemeResource = new StringBuffer(base);
            newThemeResource.append(mode.getSuffix());
            try {
                defaultField = resourceClass.getDeclaredField(matchResource[i]);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            if (resourceHashMap.get(newThemeResource.toString()) == null) {
                try {
                    mapResource.put((Integer) defaultField.get(null),
                            (Integer) defaultField.get(null));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                continue;
            }

            try {
                toField = resourceClass.getDeclaredField(newThemeResource
                        .toString());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            try {
                mapResource.put((Integer) defaultField.get(null),
                        (Integer) toField.get(null));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }
}
