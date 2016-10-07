package com.ondoor.epaisa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.ondoor.epaisa.DetailsActivity;
import com.ondoor.epaisa.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;


public class SongsListAdapter extends RecyclerView.Adapter<SongsListAdapter.ViewHolder> {

    private static final int TYPE_ITEM = 1;
    private LinkedList<JSONObject> mItemList;
    Context context;


    public SongsListAdapter(LinkedList<JSONObject> itemList) {
        mItemList = itemList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        final View view = LayoutInflater.from(context).inflate(R.layout.custom_sings_list, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        try {

            final JSONObject object = mItemList.get(position);

            holder.track_name.setText("Track Name : " + object.getString("trackName"));
            holder.artist_name.setText("Artist Name : " + object.getString("artistName"));

          if (object.has("play")&&object.getString("play").equalsIgnoreCase("1"))
            holder.progressBar.setVisibility(View.VISIBLE);
            else
            holder.progressBar.setVisibility(View.GONE);

            holder.play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        object.put("play","1");
                        new Play(object.getString("previewUrl"),position).execute();
                        notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            holder.details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {

                        Intent intent=new Intent(context, DetailsActivity.class);
                        intent.putExtra("data",object+"");
                        context.startActivity(intent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemViewType(int position) {

        return TYPE_ITEM;

    }


    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView artist_name, track_name;
        Button play,details;
        ProgressBar progressBar;

        public ViewHolder(View parent) {
            super(parent);
            artist_name = (TextView) parent.findViewById(R.id.artist_name);
            track_name = (TextView) parent.findViewById(R.id.track_name);
            play = (Button) parent.findViewById(R.id.play);
            details = (Button) parent.findViewById(R.id.details);
            progressBar = (ProgressBar) parent.findViewById(R.id.progressBar);
        }
    }

    MediaPlayer mp;

    class Play extends AsyncTask<String,String,String>{


        boolean isPLAYING;

        String URL;
        int position;
        public Play(String URL,int position){
            this.URL=URL;

            this.position=position;
        }


        @Override
        protected String doInBackground(String... strings) {

            try {

                    try {
                        mp.release();
                        mp = null;
                    }catch (Exception ee){ee.printStackTrace();}

                    mp = new MediaPlayer();
                    try {
                        mp.setDataSource(URL);
                        mp.prepare();
                        mp.start();

                    } catch (Exception e) {
                        Log.e("prepare : ", "prepare() failed");
                    }


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            mp.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
                    try {
                        mItemList.get(position).put("play","0");
                    }catch (Exception e){e.printStackTrace();}

                    notifyDataSetChanged();

                }
            });


            super.onPostExecute(s);
        }
    }




}
