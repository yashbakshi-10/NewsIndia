package com.yash.newsindia;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class ScrollingActivity extends AppCompatActivity {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private int totalCount;


    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        prefs = getPreferences(Context.MODE_PRIVATE);
        editor = prefs.edit();

        totalCount = prefs.getInt("counter", 0);
        totalCount++;
        editor.putInt("counter", totalCount);
        editor.commit();

        if(totalCount>=5)
        {

            trimCache();
            totalCount=0;
            editor.putInt("counter",totalCount);
            editor.commit();
        }

        Glide.get(getApplicationContext()).clearMemory();

        dialog=new Dialog(this);

        Fragment listFrag = new LoadList();

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frame, listFrag).commit();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab =findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment listFrag = new LoadList();

                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().remove(listFrag).commit();
                manager.beginTransaction().replace(R.id.frame, listFrag).commit();

                Snackbar.make(view, "Loading new content if possible", Snackbar.LENGTH_LONG)
                       .show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.contact) {
            showPopUp();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showPopUp() {
        Button button;
        dialog.setContentView(R.layout.contact);

        button=dialog.findViewById(R.id.get_email);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context=v.getContext();

                ClipboardManager manager=(ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData=ClipData.newPlainText("email",getString(R.string.email));
                manager.setPrimaryClip(clipData);

                Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }



    private void trimCache() {

        try {
            File dir = getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }
}
