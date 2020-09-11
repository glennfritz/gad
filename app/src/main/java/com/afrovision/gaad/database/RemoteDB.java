package com.afrovision.gaad.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RemoteDB {
    transactionCompleted caller;
    Context context;
    DBHandler dbHandler;
    SQLiteDatabase db;

    public static final RetryPolicy POLICY = new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    public static final String BASEURL = "https://gadsapi.herokuapp.com";

    public void submitProject(String fname, String lname, String em, String url) {
        String link = "https://docs.google.com/forms/d/e/1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse";
        caller.completeTransaction("200");
    }

    public interface transactionCompleted {
        void completeTransaction(String status);
    }

    public RemoteDB( Context context, transactionCompleted caller) {
        this.caller = caller;
        this.context = context;
        this.dbHandler = new DBHandler(context,null,null,1);
    }


    public void getSkillLeaders(){
        String url = BASEURL+"/api/skilliq";
        final JsonArrayRequest request = new JsonArrayRequest (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                 storePeople("1",response);
                 caller.completeTransaction("200");
            }
        }, error);
        request.setRetryPolicy(POLICY);
        request.setShouldCache(false);
        Volley.newRequestQueue(context).add(request);
    }

    public void getLearningLeaders(){
        String url = BASEURL+"/api/hours";
        final JsonArrayRequest request = new JsonArrayRequest (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                 storePeople("0",response);
                 caller.completeTransaction("200");
            }
        }, error);
        request.setRetryPolicy(POLICY);
        request.setShouldCache(false);
        Volley.newRequestQueue(context).add(request);
    }

    Response.ErrorListener error = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            caller.completeTransaction("500");
        }
    };

    public void storePeople(String type, JSONArray people){
        dbHandler.getWritableDatabase().execSQL ("delete from people where type = "+type);
        for(int i=0; i<people.length(); i++){
            try {
                JSONObject object = people.getJSONObject(i);
                dbHandler.insertData("people",
                        new String[]{"name","image","score","country","type"},
                        new String[]{object.getString("name"),
                                object.getString("badgeUrl"),
                                type.equals("1")?object.getString("score"):object.getString("hours"),
                                object.getString("country"),
                                type });

            } catch (JSONException e) {
                e.printStackTrace();
                caller.completeTransaction("500");
            }

        }
    }

}
