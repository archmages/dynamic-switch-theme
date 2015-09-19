package archmages.github.dst;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:lmyu@wisorg.com">David.Yu</a>
 * @version V1.0, 19/9/15
 */
public abstract class BaseActivity extends AppCompatActivity{

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override protected void onResume() {
        super.onResume();
        refreshTheme();
    }

    protected abstract void refreshTheme();
}
