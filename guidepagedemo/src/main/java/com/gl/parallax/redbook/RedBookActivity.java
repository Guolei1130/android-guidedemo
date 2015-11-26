package com.gl.parallax.redbook;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.gl.R;
import com.gl.parallax.redbook.parallaxpager.parallaxContainer;

public class RedBookActivity extends Activity {
    ImageView iv_man;
    ImageView rl_weibo;
    parallaxContainer mParallaxContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FILL_PARENT,
//                WindowManager.LayoutParams.FILL_PARENT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_book);

        if (android.os.Build.VERSION.SDK_INT > 10) {
            iv_man = (ImageView) findViewById(R.id.iv_man);
            mParallaxContainer = (parallaxContainer) findViewById(R.id.parallax_container);

            if (mParallaxContainer != null) {
                mParallaxContainer.setImage(iv_man);
                mParallaxContainer.setLooping(false);

                iv_man.setVisibility(View.VISIBLE);
                mParallaxContainer.setupChildren(getLayoutInflater(),
                        R.layout.view_intro_1, R.layout.view_intro_2,
                        R.layout.view_intro_3, R.layout.view_intro_4,
                        R.layout.view_intro_5, R.layout.view_login);
            }
        }
        else{
            setContentView(R.layout.view_login);
        }

        rl_weibo = (ImageView) findViewById(R.id.rl_weibo);
        rl_weibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Uri uri = Uri.parse("market://details?id=com.xingin.xhs");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                }
            }
        });
    }


}
