dynamic-switch-theme
---
![icon](https://github.com/archmages/dynamic-switch-theme/blob/master/imgs/icon.png?raw=true)

By this, we can switch app theme dynamically without recreating activity

The sample app: [click me](https://github.com/archmages/dynamic-switch-theme/blob/master/imgs/app-debug.apk?raw=true)

Here is a show case:

![gif](https://github.com/archmages/dynamic-switch-theme/blob/master/imgs/dst.gif?raw=true)

Usage
---
Create a BaseActivity, in the "onResume" lifecycle we should add an abstract method: refreshTheme

```
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
```

Then each our activity should extends BaseActivity and implete "refreshTheme"

```
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
  
  ```
  
  The library's core is: [ThemeUtil](https://raw.githubusercontent.com/archmages/dynamic-switch-theme/master/app/src/main/java/archmages/github/dst/ThemeUtil.java)
  
  
  Developed By
---
 * Email: <mailto:yuleiming198701@gmail.com>
 * G+: <https://plus.google.com/113098790396354410175/posts>
 * 微博：[archmages](http://weibo.com/archmages)
 * QQ：512505302

License
---

    Copyright 2015 archmages

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
