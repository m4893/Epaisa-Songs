package com.ondoor.epaisa;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ondoor.epaisa.Adapter.SongsListAdapter;
import com.ondoor.epaisa.Constants.Constant;
import com.ondoor.epaisa.HTTP.APIRequest;
import com.ondoor.epaisa.InterFace.RequestCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    LinkedList<JSONObject> list;
    RecyclerView recyclerView;
    SongsListAdapter songsListAdapter;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        init();
    }

    public void init(){
        list=new LinkedList<>();
        recyclerView=((RecyclerView)findViewById(R.id.recyclerView));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        songsListAdapter=new SongsListAdapter(list);
        recyclerView.setAdapter(songsListAdapter);



        new APIRequest(findViewById(R.id.activity_main),false).post(Constant.api_name,new JSONObject(), new RequestCallBack() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                try {

                    JSONArray jsonArray = jsonObject.getJSONArray("results");

                    list.clear();

                    for(int i=0;i<jsonArray.length();i++){
                        list.add(jsonArray.getJSONObject(i));
                    }

                    songsListAdapter.notifyDataSetChanged();

                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(context,"Please try again...",Toast.LENGTH_LONG).show();
                }


            }
        });


    }

}
