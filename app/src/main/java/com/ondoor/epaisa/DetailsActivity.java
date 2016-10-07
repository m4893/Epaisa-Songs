package com.ondoor.epaisa;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONObject;

/**
 * Created by ASUS on 10/7/2016.
 */

public class DetailsActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        JSONObject jsonObject=null;



        try{

            jsonObject=new JSONObject(getIntent().getStringExtra("data"));

            ((TextView)findViewById(R.id.d1)).setText("Track Id : "+jsonObject.getString("trackId"));
            ((TextView)findViewById(R.id.d2)).setText("Artist Name : "+jsonObject.getString("artistName"));
            ((TextView)findViewById(R.id.d3)).setText("Collection Name : "+jsonObject.getString("collectionName"));
            ((TextView)findViewById(R.id.d4)).setText("Collection Censored Name : "+jsonObject.getString("collectionCensoredName"));
            ((TextView)findViewById(R.id.d5)).setText("Track Time Millis : "+jsonObject.getString("trackTimeMillis"));
            ((TextView)findViewById(R.id.d6)).setText("DiscCount : "+jsonObject.getString("discCount"));
            ((TextView)findViewById(R.id.d7)).setText("Release Date : "+jsonObject.getString("releaseDate"));
            ((TextView)findViewById(R.id.d8)).setText("Track Price : "+jsonObject.getString("trackPrice"));
            ((TextView)findViewById(R.id.d9)).setText("Track Number : "+jsonObject.getString("trackNumber"));
            ((TextView)findViewById(R.id.d10)).setText("Country : "+jsonObject.getString("country"));
            ((TextView)findViewById(R.id.d11)).setText("Collection Price : "+jsonObject.getString("collectionPrice"));
            ((TextView)findViewById(R.id.d12)).setText("Preview Url : "+jsonObject.getString("previewUrl"));



        }catch (Exception e){e.printStackTrace();}

    }
//
//    {
//        "wrapperType": "track",
//            "kind": "song",
//            "artistId": 32940,
//            "collectionId": 159292399,
//            "trackId": 159294296,
//            "artistName": "Michael Jackson",
//            "collectionName": "The Essential Michael Jackson",
//            "trackName": "P.Y.T. (Pretty Young Thing)",
//            "collectionCensoredName": "The Essential Michael Jackson",
//            "trackCensoredName": "P.Y.T. (Pretty Young Thing)",
//            "artistViewUrl": "https:\/\/itunes.apple.com\/us\/artist\/michael-jackson\/id32940?uo=4",
//            "collectionViewUrl": "https:\/\/itunes.apple.com\/us\/album\/p.y.t.-pretty-young-thing\/id159292399?i=159294296&uo=4",
//            "trackViewUrl": "https:\/\/itunes.apple.com\/us\/album\/p.y.t.-pretty-young-thing\/id159292399?i=159294296&uo=4",
//            "previewUrl": "http:\/\/a1673.phobos.apple.com\/us\/r1000\/003\/Music4\/v4\/8f\/e4\/b3\/8fe4b30e-9edf-5c67-8684-f998f5284ba2\/mzaf_5297073659138878253.plus.aac.p.m4a",
//            "artworkUrl30": "http:\/\/is4.mzstatic.com\/image\/thumb\/Music6\/v4\/68\/b5\/27\/68b5273f-7044-8dbb-4ad1-82473837a136\/source\/30x30bb.jpg",
//            "artworkUrl60": "http:\/\/is4.mzstatic.com\/image\/thumb\/Music6\/v4\/68\/b5\/27\/68b5273f-7044-8dbb-4ad1-82473837a136\/source\/60x60bb.jpg",
//            "artworkUrl100": "http:\/\/is4.mzstatic.com\/image\/thumb\/Music6\/v4\/68\/b5\/27\/68b5273f-7044-8dbb-4ad1-82473837a136\/source\/100x100bb.jpg",
//            "collectionPrice": 16.99,
//            "trackPrice": 1.29,
//            "releaseDate": "2005-07-05T07:00:00Z",
//            "collectionExplicitness": "notExplicit",
//            "trackExplicitness": "notExplicit",
//            "discCount": 2,
//            "discNumber": 1,
//            "trackCount": 21,
//            "trackNumber": 20,
//            "trackTimeMillis": 239763,
//            "country": "USA",
//            "currency": "USD",
//            "primaryGenreName": "Pop",
//            "isStreamable": true
//    }
}
