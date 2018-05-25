package com.test.wheelstreettest.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.wheelstreettest.R;
import com.test.wheelstreettest.model.Datum;
import com.test.wheelstreettest.model.QuationsModel;
import com.test.wheelstreettest.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Harish on 5/9/2018.
 */

public class QuationsAdapter extends RecyclerView.Adapter<QuationsAdapter.myHolder> {

    QuationsModel model;

    List<Datum> quations;
    List<Datum> answers;

    List<String> listquations;
    List<String> listanswers;

    public static List<String> listall;

    Context ctx;
    int i = 1;

    public QuationsAdapter(){

    }
    public QuationsAdapter(Context ctx, QuationsModel model,List<String> listall) {
        this.ctx = ctx;
        this.model = model;
        this.listall = listall;
        this.model = model;
        answers = new ArrayList<>();
        quations = model.getData();
    }

    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.quations_adapter, parent, false);
        return new myHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myHolder holder, int position) {


        if(listall.get(position).contains("-")) {
            final FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) holder.quation.getLayoutParams();
            params.gravity = GravityCompat.START;
            holder.quation.setLayoutParams(params);
            holder.quation.setBackgroundResource(R.drawable.text_background);
            holder.quation.setTypeface(null, Typeface.BOLD);
            holder.quation.setTextColor(Color.WHITE);
            String quation=listall.get(position).replaceAll("-","");
            holder.quation.setText(quation);

        }
        else{
            final FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) holder.quation.getLayoutParams();
            params.gravity = GravityCompat.END;
            holder.quation.setLayoutParams(params);
            holder.quation.setTypeface(null, Typeface.NORMAL);
            holder.quation.setTextColor(Color.DKGRAY);
            holder.quation.setBackgroundResource(R.drawable.text_background_aeaea);
            holder.quation.setText(listall.get(position));
        }

    }


    public void insertanswer(List<String> listanswers){
        this.listanswers=listanswers;
        notifyDataSetChanged();
    }

    public void listall(String listall){
        this.listall.add(listall);
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return listall.size();
    }


    public class myHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.quation)
        TextView quation;

        public myHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
