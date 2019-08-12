package com.example.drenu.testdrawer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;


public class TransferFragment extends android.app.Fragment {

    View v;
    IntentIntegrator qrScan;
    Integer tamt;
    ParseACL defaultACL;

    public TransferFragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        defaultACL=new ParseACL();

        defaultACL.setPublicWriteAccess(true);
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL,true);

        v=inflater.inflate(R.layout.fragment_transfer, container, false);

        Button transfer=(Button)v.findViewById(R.id.transfer);

        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                EditText tv=(EditText) v.findViewById(R.id.amt);
                try {
                    tamt = Integer.parseInt(tv.getText().toString());
                /*qrScan = new IntentIntegrator(getActivity());
                qrScan.initiateScan();*/
                    if(tamt.toString().equals(""))
                    {
                        Toast.makeText(getActivity(),"Please enter amount",Toast.LENGTH_SHORT);
                        return;
                    }
                    else {
                        IntentIntegrator.forFragment(TransferFragment.this).setPrompt("Scan QR").initiateScan();
                    }
                }

                catch (Exception e)
                {
                    Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();

                }

            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(getActivity(), "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews

                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Scan result");
                    alertDialog.setMessage(obj.toString());
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

                    String oid=obj.getString("objectId");
                    Log.i("the user is:",oid);

                ParseQuery<ParseObject> q=ParseQuery.getQuery("Account");
                    q.whereEqualTo("id",oid);
                    q.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {

                            if(e==null) {
                                Log.i("in", "inside");
                                Log.i("size", String.valueOf(objects.size()));

                                if (objects.size() == 1) {
                                    for (ParseObject u : objects) {
                                        Log.i("bal", u.getString("balance"));
                                        Integer amt=Integer.parseInt(u.getString("balance"));
                                        ParseUser cur=ParseUser.getCurrentUser();
                                        String curId=cur.getObjectId();
                                        ParseQuery<ParseObject> q1=ParseQuery.getQuery("Account");
                                        q1.whereEqualTo("id",curId);
                                        q1.findInBackground(new FindCallback<ParseObject>() {
                                            @Override
                                            public void done(List<ParseObject> objects, ParseException e) {
                                                if(objects.size()==1)
                                                {
                                                    for(ParseObject o : objects) {
                                                        Integer bal=Integer.parseInt(o.getString("balance"));
                                                        bal-=tamt;
                                                        o.put("balance",bal.toString());
                                                        o.saveInBackground();
                                                    }
                                                }

                                            }
                                        });
                                        amt+=tamt;
                                        Log.i("amt:",amt.toString());
                                        u.put("balance",amt.toString());
                                        //Log.i("ACL",u.getACL().toString());
                                        u.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                if(e!=null)
                                                Log.i("save: ",e.toString());

                                            }
                                        });

                                    }
                                }
                            }
                            else
                            {

                            }
                        }
                    });

/*
*/

                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(getActivity(), result.getContents(), Toast.LENGTH_LONG).show();
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
