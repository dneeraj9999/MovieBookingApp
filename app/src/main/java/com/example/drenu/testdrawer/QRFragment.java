package com.example.drenu.testdrawer;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class QRFragment extends android.app.Fragment {

    ImageView imageViewQrCode;
    TextView tv;

    public QRFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Context context;
        View myview;
        Bitmap bitmap = null;
        try {
            String user = ParseUser.getCurrentUser().getUsername();
            String email = ParseUser.getCurrentUser().getEmail();
            String oid = ParseUser.getCurrentUser().getObjectId();

            JSONObject obj=new JSONObject();

            obj.put("username",user);
            obj.put("email",email);
            obj.put("objectId",oid);


            bitmap = TextToImageEncode(obj.toString());


        } catch (WriterException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        myview = inflater.inflate(R.layout.fragment_qr2, container, false);
        imageViewQrCode = (ImageView) myview.findViewById(R.id.myqr);
        imageViewQrCode.setImageBitmap(bitmap);

        tv=(TextView)myview.findViewById(R.id.balance);
        String oid=ParseUser.getCurrentUser().getObjectId();
        ParseQuery<ParseObject> q=ParseQuery.getQuery("Account");
        q.whereEqualTo("id",oid);
        q.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(e==null)
                {
                    if(objects.size()==1)
                    {
                        for(ParseObject o : objects) {
                            String bal=o.getString("balance");
                            tv.setText(bal);
                        }
                    }
                }
                else
                {
                    Log.i("qr bal:",e.toString());
                }
            }
        });

        return myview;
    }

    private Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    500, 500, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.colorBlack) : getResources().getColor(R.color.colorWhite);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }



}
