package archmages.github.dst;

import android.widget.RelativeLayout;
import android.widget.TextView;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends BaseActivity {

    @ViewById TextView textview;

    @ViewById RelativeLayout rlBg;

    @Bean
    ThemeUtil themeUtil;

    @OptionsItem(R.id.action_light)
    void swithThemeToLight(){
        switchTheme(ThemeUtil.ThemeMode.LIGHT);
    }

    @OptionsItem(R.id.action_night)
    void swithThemeToNight(){
        switchTheme(ThemeUtil.ThemeMode.NIGHT);
    }

    @OptionsItem(R.id.action_dusk)
    void swithThemeToDusk(){
        switchTheme(ThemeUtil.ThemeMode.DUSK);
    }

    private void switchTheme(ThemeUtil.ThemeMode themeMode) {
        themeUtil.switchTheme(themeMode);
        refreshTheme();
    }

    @Override
    protected void refreshTheme() {
        ThemeUtil.ThemeMode theme = themeUtil.getLastTheme();
        textview.setTextColor(getResources().getColor(theme.getId(R.color.text_color_l)));
        rlBg.setBackgroundColor(getResources().getColor(theme.getId(R.color.bg_color_l)));
    }
}
