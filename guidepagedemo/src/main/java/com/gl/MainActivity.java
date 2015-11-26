package com.gl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.gl.WebGuideActivity.WebActivity;
import com.gl.parallax.ecmobile.GalleryImageActivity;
import com.gl.parallax.redbook.RedBookActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button normal,parallax_1,parallax_2,html;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        normal = (Button) findViewById(R.id.normal);
        normal.setOnClickListener(this);
        parallax_1 = (Button) findViewById(R.id.parallax_1);
        parallax_1.setOnClickListener(this);
        parallax_2 = (Button) findViewById(R.id.parallax_2);
        parallax_2.setOnClickListener(this);
        html = (Button) findViewById(R.id.html);
        html.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.normal:
                Intent intent_1 = new Intent(MainActivity.this,NormalViewPager.class);
                startActivity(intent_1);
                break;
            case R.id.parallax_1:
                Intent intent_2 = new Intent(MainActivity.this, GalleryImageActivity.class);
                startActivity(intent_2);
                break;
            case R.id.parallax_2:
                Intent intent_3 = new Intent(MainActivity.this, RedBookActivity.class);
                startActivity(intent_3);
                break;
            case R.id.html:
                Intent intent_4 = new Intent(MainActivity.this, WebActivity.class);
                startActivity(intent_4);
                break;
        }
    }
}
