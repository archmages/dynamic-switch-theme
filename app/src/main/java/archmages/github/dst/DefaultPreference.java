package archmages.github.dst;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:lmyu@wisorg.com">David.Yu</a>
 * @version V1.0, 19/9/15
 */
@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface DefaultPreference {

    /**
     * last switched theme
     * @return
     */
    int lastThemeMode();
}
