package com.ondoor.epaisa.Utility;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;



import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utility {

    public static Context appContext;
    private static String ONDOOR_DATA = "ONDOOR_DATA";
    private static int MAX_IMAGE_DIMENSION = 720;

    // for username string preferences
    public static void setSharedPreference(Context context, String name, String value) {
        appContext = context;
        SharedPreferences settings = context.getSharedPreferences(ONDOOR_DATA, 0);
        SharedPreferences.Editor editor = settings.edit();
        // editor.clear();
        editor.putString(name, value);
        editor.commit();
    }

    public static void hideSoftKeyboard(Context context) {
        if(((Activity)context).getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(((Activity)context).INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(((Activity)context).getCurrentFocus().getWindowToken(), 0);
        }
    }



    public static void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(((Activity)view.getContext()).INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }

    public static void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();

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

        // The directory is now empty so delete it
        return dir.delete();
    }


//    public static void setCartItem(Context appContext) {
//        try {
//
//            Constant.CARD_LIST.clear();
//
//            String card = Utility.getSharedPreferences(appContext, Constant.ITEM_CARD);
//
//            if (card != null && !card.equalsIgnoreCase("")) {
//
//                JSONArray jsonArray = new JSONObject(card).getJSONArray("data");
//
//                for (int i = 0; i < jsonArray.length(); i++) {
//
//                    Constant.CARD_LIST.add(jsonArray.optJSONObject(i));
//
//                }
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    //Method For Edit Card

    public static void setEditSharedPreference(Context context, String name, String value) {
        appContext = context;
        SharedPreferences settings = context.getSharedPreferences(ONDOOR_DATA, 0);
        SharedPreferences.Editor editor = settings.edit();
// editor.clear();
        editor.putString(name, value);
        editor.commit();
    }


    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void showCustomToast(Activity mActivity, String msg) {
        //Getting the View object as defined in the customtoast.xml file
//		LayoutInflater li = mActivity.getLayoutInflater();
//		View layout = li.inflate(R.layout.custom_toast_layout, null);
//		TextView textView = (TextView)layout.findViewById(R.id.custom_toast_message);
//		textView.setText(msg);
//
//		//Creating the Toast object
//		//Log.e(TAG,"MSG: "+msg);
//		Toast toast = Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT);
//		toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 20, 80);
//		toast.setView(layout);//setting the view of custom toast layout
//		toast.show();

        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
    }

    public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {

        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        context.startService(new Intent(context, serviceClass));
        return false;
    }


    public static boolean setLocale(Context appContext, String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = appContext.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        //Utility.setSharedPreference (appContext, Constant.SET_LANGUAGE, lang);
        return true;
    }

    // for username string preferences
    public static void setIntegerSharedPreference(Context context, String name, int value) {
        appContext = context;
        SharedPreferences settings = context.getSharedPreferences(ONDOOR_DATA, 0);
        SharedPreferences.Editor editor = settings.edit();
        // editor.clear();
        editor.putInt(name, value);
        editor.commit();
    }

    //Drawable
    public static void setDrawableSharedPreference(Context context, String name, int value) {
        appContext = context;
        SharedPreferences settings = context.getSharedPreferences(ONDOOR_DATA, 0);
        SharedPreferences.Editor editor = settings.edit();
        // editor.clear();
        editor.putInt(name, value);
        editor.commit();
    }

    public static String getSharedPreferences(Context context, String name) {
        SharedPreferences settings = context.getSharedPreferences(ONDOOR_DATA, 0);
        return settings.getString(name, "");
    }

    public static int getIngerSharedPreferences(Context context, String name) {
        SharedPreferences settings = context.getSharedPreferences(ONDOOR_DATA, 0);
        return settings.getInt(name, 0);
    }


    public static void setSharedPreferenceBoolean(Context context, String name, boolean value) {
        appContext = context;
        SharedPreferences settings = context.getSharedPreferences(ONDOOR_DATA, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(name, value);
        editor.commit();
    }

    public static boolean getSharedPreferencesBoolean(Context context, String name) {
        SharedPreferences settings = context.getSharedPreferences(ONDOOR_DATA, 0);
        return settings.getBoolean(name, false);
    }

    public static Bitmap decodeSampledBitmapFromResource(String path, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.

            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

/*	public static Drawable getImageFromURL(String photoDomain) {
        Drawable drawable = null;
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet request = new HttpGet(photoDomain.trim());
			HttpResponse response = httpClient.execute(request);
			InputStream is = response.getEntity().getContent();
			drawable = Drawable.createFromStream (is, "src");
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
		return drawable;
	}*/

/*	public static String postParamsAndfindJSON(String url,
                                               ArrayList<NameValuePair> params) {
		String result = "";

		System.out.println("URL comes in jsonparser class is:  " + url+params);
		try {
			int TIMEOUT_MILLISEC = 100000; // = 10 seconds
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout (httpParams, TIMEOUT_MILLISEC);
			HttpConnectionParams.setSoTimeout (httpParams, TIMEOUT_MILLISEC);

			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			// httpGet.setURI(new URI(url));

			HttpResponse httpResponse = httpClient.execute(httpPost);
			InputStream is = httpResponse.getEntity().getContent();
			BufferedReader reader = new BufferedReader (new InputStreamReader (is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder ();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}

			is.close();
			result = sb.toString();

		} catch (Exception e) {
			System.out.println("exception in jsonparser class ........");
			e.printStackTrace();
			result="";
			return result;
		}
		return result;
	}*/

	/*public static String multiPart(String url,MultipartEntity entity) {
		String result="";
		HttpClient httpclient;

		try {
			httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);

			httppost.setEntity(entity);
			System.out.println("cvsc" + httppost);
			HttpResponse response = httpclient.execute(httppost);
			result = EntityUtils.toString(response.getEntity());
			System.out.println("Given Result to photo  " + result);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;

	}*/


    public static Bitmap DownloadImageDirect(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        // String imageEncoded = Base64Coder.encodeTobase64(image);

        // Log.d("LOOK", imageEncoded);
        return imageEncoded;
    }

    public static void alertBoxShow(Context context, int msg) {
        // set dialog for user's current location set for searching theaters.
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Alert");
        dialog.setMessage(msg);
        dialog.setPositiveButton(" Ok ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();

            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void ShowStringAlertWithMessage(Context context, int alerttitle,
                                                  int locationvalidation) {
        // Assign the alert builder , this can not be assign in Click events
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setMessage(locationvalidation);
        builder.setTitle(alerttitle);
        // Set behavior of negative button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cancel the dialog
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void ShowStringAlert2WithMessage(final Context context, int alerttitle,
                                                   int locationvalidation) {
        // Assign the alert builder , this can not be assign in Click events
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setMessage(locationvalidation);
        builder.setTitle(alerttitle);
        // Set behavior of negative button
        builder.setPositiveButton("GET STARTED", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cancel the dialog
                dialog.cancel();
                //((HomeActivity) context).moveToNextActivity("GET STARTED");
                //Intent intent=new Intent(context,GetStartedActivity.class);
            }
        });
        builder.setNegativeButton("FAQ", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cancel the dialog
                dialog.cancel();
                //((HomeActivity) context).moveToNextActivity("FAQ");
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

	/*public static String findJSONFromUrl(String url) {
		String result = "";

		System.out.println("URL comes in jsonparser class is:  " + url);
		try {
			int TIMEOUT_MILLISEC = 100000; // = 10 seconds
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout (httpParams,
					TIMEOUT_MILLISEC);
			HttpConnectionParams.setSoTimeout (httpParams, TIMEOUT_MILLISEC);
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			// httpGet.setURI(new URI(url));

			HttpResponse httpResponse = httpClient.execute(httpGet);
			InputStream is = httpResponse.getEntity().getContent();
			BufferedReader reader = new BufferedReader (new InputStreamReader (is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder ();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}

			is.close();
			result = sb.toString();
			System.out.println("result  in jsonparser class ........" + result);

		} catch (Exception e) {
			System.out.println("exception in jsonparser class ........");
			result=null;
		}
		return result;
	}*/

    public static Bitmap getBitmap(String url) {
        Bitmap imageBitmap = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            try {
                imageBitmap = BitmapFactory.decodeStream(new FlushedInputStream(is));
            } catch (OutOfMemoryError error) {
                error.printStackTrace();
                System.out.println("exception in get bitma putility");
            }

            bis.close();
            is.close();
            final int IMAGE_MAX_SIZE = 50;
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            int scale = 1;
            while ((o.outWidth * o.outHeight) * (1 / Math.pow(scale, 2)) > IMAGE_MAX_SIZE) {
                scale++;
            }
            if (scale > 1) {
                scale--;
                // scale to max possible inSampleSize that still yields an image
                // larger than target
                o = new BitmapFactory.Options();
                o.inSampleSize = scale;
                // b = BitmapFactory.decodeStream(in, null, o);

                // resize to desired dimensions
                int height = imageBitmap.getHeight();
                int width = imageBitmap.getWidth();

                double y = Math.sqrt(IMAGE_MAX_SIZE / (((double) width) / height));
                double x = (y / height) * width;
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(imageBitmap, (int) x, (int) y, true);
                imageBitmap.recycle();
                imageBitmap = scaledBitmap;

                System.gc();
            } else {
                // b = BitmapFactory.decodeStream(in);
            }

        } catch (OutOfMemoryError error) {
            error.printStackTrace();
            System.out.println("exception in get bitma putility");
        } catch (Exception e) {
            System.out.println("exception in get bitma putility");
            e.printStackTrace();
        }
        return imageBitmap;
    }

    static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }
    }

    public static byte[] scaleImage(Context context, Uri photoUri)
            throws IOException {
        InputStream is = context.getContentResolver().openInputStream(photoUri);
        BitmapFactory.Options dbo = new BitmapFactory.Options();
        dbo.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, dbo);
        is.close();

        int rotatedWidth, rotatedHeight;
        int orientation = 0;// getOrientation(context, photoUri);

        if (orientation == 90 || orientation == 270) {
            rotatedWidth = dbo.outHeight;
            rotatedHeight = dbo.outWidth;
        } else {
            rotatedWidth = dbo.outWidth;
            rotatedHeight = dbo.outHeight;
        }

        Bitmap srcBitmap;
        is = context.getContentResolver().openInputStream(photoUri);
        if (rotatedWidth > MAX_IMAGE_DIMENSION
                || rotatedHeight > MAX_IMAGE_DIMENSION) {
            float widthRatio = ((float) rotatedWidth)
                    / ((float) MAX_IMAGE_DIMENSION);
            float heightRatio = ((float) rotatedHeight)
                    / ((float) MAX_IMAGE_DIMENSION);
            float maxRatio = Math.max(widthRatio, heightRatio);

            // Create the bitmap from file
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = (int) maxRatio;
            srcBitmap = BitmapFactory.decodeStream(is, null, options);
        } else {
            srcBitmap = BitmapFactory.decodeStream(is);
        }
        is.close();

		/*
		 * if the orientation is not 0 (or -1, which means we don't know), we
		 * have to do a rotation.
		 */
        if (orientation > 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(orientation);

            srcBitmap = Bitmap.createBitmap(srcBitmap, 0, 0,
                    srcBitmap.getWidth(), srcBitmap.getHeight(), matrix, true);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        srcBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		/*
		 * if (type.equals("image/png")) {
		 * srcBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); } else if
		 * (type.equals("image/jpg") || type.equals("image/jpeg")) {
		 * srcBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); }
		 */
        byte[] bMapArray = baos.toByteArray();
        baos.close();
        return bMapArray;
    }

    static int mMaxWidth, mMaxHeight;

    @SuppressWarnings("deprecation")
    public static Bitmap loadResizedImage(Context mContext, final File imageFile) {
        WindowManager windowManager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        mMaxWidth = display.getWidth();
        mMaxHeight = display.getHeight();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
        int scale = calculateInSampleSize(options, mMaxWidth, mMaxHeight);
        while (options.outWidth / scale > mMaxWidth
                || options.outHeight / scale > mMaxHeight) {
            scale++;
        }
        Bitmap bitmap = null;
        Bitmap scaledBitmap = null;
        if (scale > 1) {
            try {
                scale--;
                options = new BitmapFactory.Options();
                options.inSampleSize = scale;
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                options.inPurgeable = true;
                options.inTempStorage = new byte[16 * 100];
                bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(),
                        options);
                if (bitmap == null) {
                    return null;
                }

                // resize to desired dimensions
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                double newWidth;
                double newHeight;
                if ((double) width / mMaxWidth < (double) height / mMaxHeight) {
                    newHeight = mMaxHeight;
                    newWidth = (newHeight / height) * width;
                } else {
                    newWidth = mMaxWidth;
                    newHeight = (newWidth / width) * height;
                }

                scaledBitmap = Bitmap.createScaledBitmap(bitmap,
                        Math.round((float) newWidth),
                        Math.round((float) newHeight), true);
                bitmap.recycle();
                bitmap = scaledBitmap;
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
                bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            }
            System.gc();
        } else {
            bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        }

        return rotateImage(bitmap, imageFile);
    }

    public static Bitmap rotateImage(final Bitmap bitmap,
                                     final File fileWithExifInfo) {
        if (bitmap == null) {
            return null;
        }
        Bitmap rotatedBitmap = bitmap;
        int orientation = 0;
        try {
            orientation = getImageOrientation(fileWithExifInfo
                    .getAbsolutePath());
            if (orientation != 0) {
                Matrix matrix = new Matrix();
                matrix.postRotate(orientation, (float) bitmap.getWidth() / 2,
                        (float) bitmap.getHeight() / 2);
                rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                        bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                bitmap.recycle();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotatedBitmap;
    }

    public static int getImageOrientation(final String file) throws IOException {
        ExifInterface exif = new ExifInterface(file);
        int orientation = exif
                .getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return 0;
            case ExifInterface.ORIENTATION_ROTATE_90:
                return 90;
            case ExifInterface.ORIENTATION_ROTATE_180:
                return 180;
            case ExifInterface.ORIENTATION_ROTATE_270:
                return 270;
            default:
                return 0;
        }
    }

    public static Typeface Appttf(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "AE100132.TTF");
    }

    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }


    // remove for preferences

    public static void removepreference(Context context, String name) {
        SharedPreferences settings = context.getSharedPreferences(ONDOOR_DATA, 0);
        settings.edit().remove(name).commit();
    }

    public static boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /* public static void ShowAlertDialog(String msg)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(appContext);
            builder.setTitle("SwipeMe");
            builder.setMessage(msg);
            builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });

    // Create the AlertDialog
            AlertDialog dialog = builder.create();
            dialog.show();

        }
    */

    public static boolean validateFirstName(String firstName) {
        return firstName.matches("^([a-zA-Z ])+$");
    } // end

    /**
     * Function to show settings alert dialog
     */
    public static void showSettingsAlert(final Context context) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public static boolean PanCardValidator(String pancard) {
        Pattern pattern;
        Matcher matcher;
        final String PAN_CARD_PATTERN = "^[A-Z|a-z]{5}\\d{4}[A-Z|a-z]{1}$";
        pattern = Pattern.compile(PAN_CARD_PATTERN);
        matcher = pattern.matcher(pancard);
        return matcher.matches();
    }


    public static boolean AadharCardValidator(String aadhar) {
        Pattern pattern;
        Matcher matcher;
        final String AADHAR_PATTERN = "^[0-9]{12}$";
        pattern = Pattern.compile(AADHAR_PATTERN);
        matcher = pattern.matcher(aadhar);
        return matcher.matches();
    }

    public static boolean VoterCardValidator(String votercard) {
        Pattern pattern;
        Matcher matcher;
        final String VOTER_PATTERN = "^[A-Z|a-z]{3}\\d{7}$";
        pattern = Pattern.compile(VOTER_PATTERN);
        matcher = pattern.matcher(votercard);
        return matcher.matches();
    }

    public static boolean VoterCardValidator1(String votercard) {
        Pattern pattern;
        Matcher matcher;
        final String VOTER_PATTERN = "^[0-9]{5}[.]\\d[0-9]{2}$";
        pattern = Pattern.compile(VOTER_PATTERN);
        matcher = pattern.matcher(votercard);
        return matcher.matches();
    }

    public static boolean decimalAkanksha(String decimal_no) {
        Pattern pattern;
        Matcher matcher;
        final String Decimal_PATTERN = "^[0-9]{5}[.][0-9]{2}$";
        pattern = Pattern.compile(Decimal_PATTERN);
        matcher = pattern.matcher(decimal_no);
        return matcher.matches();
    }

    public static boolean DrivingLisncValidator(String votercard) {
        Pattern pattern, pattern1;
        Matcher matcher, matcher1;
        final String VOTER_PATTERN = "^[A-Z|a-z]{2}\\d{2}[A-Z|a-z]{1}\\d{11}$";
        final String VOTER_PATTERN1 = "^[A-Z|a-z-]{2}\\d{2}[A-Z|a-z]{1}-\\d{4}-\\d{7}$";
        //var docregex1 = /^[A-Z|a-z]{2}\d{2}[A-Z|a-z]{1}\d{11}$/;
        //var docregex = /^[A-Z|a-z-]{2}\d{2}[A-Z|a-z]{1}-\d{4}-\d{7}$/;
        pattern = Pattern.compile(VOTER_PATTERN);
        pattern1 = Pattern.compile(VOTER_PATTERN1);
        matcher = pattern.matcher(votercard);
        matcher1 = pattern1.matcher(votercard);

        return matcher.matches() || matcher1.matches();
    }

    public static boolean IfscValidator(String Ifsccode) {
        Pattern pattern;
        Matcher matcher;
        final String Ifsccode_PATTERN = "^[A-Z|a-z]{4}[0][\\d]{6}$"; //^[A-Z|a-z]{4}[0][\d]{6}$
        pattern = Pattern.compile(Ifsccode_PATTERN);
        matcher = pattern.matcher(Ifsccode);
        return matcher.matches();
    }

//public static String getMonth(String month){
//
//	switch (month)
//	{
//		case "1":
//			return "January";
//		case "2":
//			return "February";
//		case "3":
//			return "March";
//		case "4":
//			return "April";
//		case "5":
//			return "May";
//		case "6":
//			return "June";
//		case "7":
//			return "July";
//		case "8":
//			return "August";
//		case "9":
//			return "September";
//		case "10":
//			return "October";
//		case "11":
//			return "November";
//		case "12":
//			return "December";
//		default:
//			return "January";
//
//
//	}
//=======
//>>>>>>> Detail_Screen
//
//        }

//    }


//}


    public static boolean CheckDates(Date currentDate, Date statementDate) {

        SimpleDateFormat dfDate = new SimpleDateFormat("dd-MM-yyyy");

        boolean b = false;

        try {
            if (currentDate.before(statementDate)) {
                //Toast.makeText(c,"lessthan",Toast.LENGTH_LONG).show();
//                Log.d("","b "+b);
                b = false;  // If start date is before end date.

            } else if (currentDate.equals(statementDate)) {
                b = true;  // If two dates are equal.
//                Log.d("","b "+b);

                // Toast.makeText(c,"equals",Toast.LENGTH_LONG).show();
            } else {
//                Log.d("","b "+b);
                b = true; // If start date is after the end date.
                //Toast.makeText(c,"greaterthan",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return b;
    }


    public static boolean specialCharecter(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "[^&%$#@!~]+";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public static SpannableStringBuilder customSubStringColor(int start, int end, String msg, ForegroundColorSpan foregroundColorSpan) {

        final SpannableStringBuilder sb = new SpannableStringBuilder(msg);

// Span to set text color to some RGB value

// Span to make text bold
        final StyleSpan bss = new StyleSpan(Typeface.NORMAL);

// Set the text color for first 4 characters
        sb.setSpan(foregroundColorSpan, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

// make them also bold
        sb.setSpan(bss, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);


        return sb;
    }

    public static SpannableStringBuilder customSTermAndConditionColor(int start, int end, String msg, ForegroundColorSpan foregroundColorSpan) {

        final SpannableStringBuilder sb = new SpannableStringBuilder(msg);

// Span to set text color to some RGB value

// Span to make text bold
        final StyleSpan bss = new StyleSpan(Typeface.BOLD);

// Set the text color for first 4 characters
        sb.setSpan(foregroundColorSpan, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        sb.setSpan(new UnderlineSpan(), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

// make them also bold
        sb.setSpan(bss, start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        return sb;
    }

}

