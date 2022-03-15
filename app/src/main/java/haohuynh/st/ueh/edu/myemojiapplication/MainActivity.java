package haohuynh.st.ueh.edu.myemojiapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    GridView mygridview;
    TextView textview_icon;
    final int total = 16;
    int wrong = 0;
    String emoji;
    int num;
    int unicode = 0x1F600;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mygridview = findViewById(R.id.mygridview);
        textview_icon = findViewById(R.id.textview_icon);
        runGame();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(getApplicationContext(), "Restarting game...", Toast.LENGTH_SHORT).show();
        wrong = 0;
        runGame();
    }
    public String getEmoji(int uni)
    {
        return new String(Character.toChars(uni));
    }
    protected void runGame(){


        List data = new ArrayList();
        for(int i=0; i<total; i++) {
            int unicode_1 = 0x1F600 + i;
            String emoji_1 = getEmoji(unicode_1);
            data.add(emoji_1);
        }
        Collections.shuffle(data);

        MyAdapter myadapter = new MyAdapter(getApplicationContext(), R.layout.items, data);
        mygridview.setAdapter(myadapter);

        ArrayList temp = new ArrayList(data);
        Random rand_icon = new Random();
        num=rand_icon.nextInt(temp.size());
        emoji =temp.get(num).toString() ;
        textview_icon.setText(emoji);

        mygridview.setOnItemClickListener((parent, view, position, id) -> {
            TextView v = (TextView) view;
            String selected = v.getText().toString().trim();
            if(selected.equals(emoji)){
                temp.remove(emoji);
                num=rand_icon.nextInt(temp.size());
                emoji =temp.get(num).toString() ;
                textview_icon.setText(emoji);
                v.setText("");
                if(temp.size()<total-2){
                    startActivity(new Intent(getApplicationContext(), SuccessActivity.class));
                }
            }else{
                wrong++;
                Toast.makeText(getApplicationContext(),
                        "Failed! Remaining: "+(3-wrong)+" lives",
                        Toast.LENGTH_SHORT).show();
                if(wrong>2){
                    startActivity(new Intent(getApplicationContext(), FailedActivity.class));
                }
            }
        });
    }
}