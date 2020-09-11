package com.afrovision.gaad.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afrovision.gaad.R;
import com.afrovision.gaad.adapter.ListAdapter;
import com.afrovision.gaad.database.DBHandler;
import com.afrovision.gaad.database.RemoteDB;
import com.afrovision.gaad.model.Person;

import java.util.ArrayList;
import java.util.List;


public class SkillLeaders extends Fragment {
    RecyclerView listview;
    List<Person> list = new ArrayList<Person>();
    public RecyclerView.Adapter adapter;
    public ProgressBar pb;

    public SkillLeaders() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_skill_leaders, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pb = view.findViewById(R.id.pb);

        listview = view.findViewById(R.id.container);
        listview.setLayoutManager(new LinearLayoutManager(getContext()));
        listview.setNestedScrollingEnabled(false);
        adapter = new ListAdapter(getContext(), list, false);
        listview.setAdapter(adapter);

        final SwipeRefreshLayout swipeRefreshLayout  = view.findViewById(R.id.swipecontainer);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener () {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                getPeople();
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {

                    }
                }, 1000);
            }
        });

        getPeople();
    }

    public void getPeople(){
        list.clear();
        pb.setVisibility(View.VISIBLE);
        new RemoteDB(getContext(), new RemoteDB.transactionCompleted() {
            @Override
            public void completeTransaction(String status) {
                if(status.equals("500")){
                    Toast.makeText(getContext(), getString(R.string.network_error),Toast.LENGTH_LONG).show();
                }
                list.addAll(new DBHandler(getContext(),null,null,1).getPeople("0"));
                pb.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }
        }).getLearningLeaders();
    }

}
