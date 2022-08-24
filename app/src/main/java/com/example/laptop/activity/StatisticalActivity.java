package com.example.laptop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.laptop.R;
import com.example.laptop.adapter.StatisticalAdapter;
import com.example.laptop.model.Order;
import com.example.laptop.retrofit.Api;
import com.example.laptop.retrofit.RetrofitClient;
import com.example.laptop.ultils.Ultils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatisticalActivity extends AppCompatActivity {
    Toolbar toolbar_statis;
    RecyclerView rcvStatis;
    TextView total;
    List<Order> lstOrder,lst;
    StatisticalAdapter statisticalAdapter;
    Api api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistical);
        initView();
        getStatistical();
    }

    private void getStatistical() {
        api.allOrder().enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                lst = response.body();
                for (Order d: lst){
                    if (d.getUsername().equals(Ultils.current_user)){
                        lstOrder.add(d);
                    }
                }
                //int total1 = sumTotal(lstOrder);
                //total.setText(String.valueOf(total1));
                statisticalAdapter = new StatisticalAdapter(getApplicationContext(),lstOrder);
                rcvStatis.setAdapter(statisticalAdapter);
            }
            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });
    }

   /* private int sumTotal(List<Order> lst) {
        int tong= 0;
        for (int i = 0; i < lst.size(); i++) {
            tong+= Integer.parseInt(lst.get(i).getTotalprice());
        }
        return tong;
    }*/

    private void initView() {
        toolbar_statis = findViewById(R.id.toolbar_statistical);
        rcvStatis = findViewById(R.id.rcv_statis);
        total = findViewById(R.id.tv_statis_total);
        rcvStatis.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcvStatis.setLayoutManager(layoutManager);
        api = RetrofitClient.getClient().create(Api.class);
        lstOrder =  new ArrayList<>();
        lst = new ArrayList<>();
    }
}