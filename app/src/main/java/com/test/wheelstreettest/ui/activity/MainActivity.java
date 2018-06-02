package com.test.wheelstreettest.ui.activity;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.test.wheelstreettest.R;
import com.test.wheelstreettest.adapter.QuationsAdapterSQ;
import com.test.wheelstreettest.main.IMainView;
import com.test.wheelstreettest.main.Presenter;
import com.test.wheelstreettest.model.AnswerModel;
import com.test.wheelstreettest.model.AnsweredQuations;
import com.test.wheelstreettest.model.Datum;
import com.test.wheelstreettest.model.QANDA;
import com.test.wheelstreettest.model.QuationsModel;
import com.test.wheelstreettest.model.UserDetails;
import com.test.wheelstreettest.utils.Constents;
import com.test.wheelstreettest.utils.DBHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements IMainView {

    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.send)
    ImageView send;
    @BindView(R.id.answeredit)
    EditText answeredit;
    @BindView(R.id.yes)
    TextView yes;
    @BindView(R.id.no)
    TextView no;
    @BindView(R.id.yesno)
    LinearLayout yesno;
    @BindView(R.id.typing)
    LinearLayout typing;
    @BindView(R.id.completed)
    RelativeLayout completed;
    @BindView(R.id.allover)
    RelativeLayout allover;
    @BindView(R.id.submit)
    FloatingActionButton submit;
    int i = 0;
    List<AnsweredQuations> answeredQuationsList;
    AnsweredQuations answeredQuations;
    String currentAnswer;
    List<Datum> data;
    List<QANDA> finalqndid;
    QuationsAdapterSQ adapter;
    Presenter presenter;
    Constents constents;
    DBHandler dbHandler;
    UserDetails userDetails;
    String id;
    String name;
    String age;
    String gender;
    String email;
    String mobile;
    List<String> listAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    protected void init() {
        adapter = new QuationsAdapterSQ();
        dbHandler = new DBHandler(this);
        userDetails = dbHandler.getProfile();
        id = userDetails.getId();
        name = userDetails.getName();
        age = userDetails.getDob();
        gender = userDetails.getGender();
        email = userDetails.getEmail();
        mobile = userDetails.getMobile();
        answeredQuations = new AnsweredQuations();
        answeredQuationsList = new ArrayList<>();
        recycle.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        constents = new Constents();
        presenter = new Presenter(this);
        listAll = new ArrayList<>();
        get();
    }

    public void get() {
        finalqndid = dbHandler.getQuations();
        if (dbHandler.hasloaded()) {

            if (dbHandler.getQandA() != null) {

                List<QANDA> list = dbHandler.getQandA();
                for (int j = 0; j < list.size(); j++) {
                    listAll.add("-" + list.get(j).getQUA());
                    listAll.add(list.get(j).getANS());
                }
                adapter = new QuationsAdapterSQ(listAll);
                recycle.setAdapter(adapter);
                getNextQuestion(listAll.size() - (listAll.size() / 2));

            } else {

                listAll.add("-" + finalqndid.get(i).getQUA());
                adapter = new QuationsAdapterSQ(listAll);
                recycle.setAdapter(adapter);
            }

        } else {
            constents.showProgressdialog(this);
            presenter.attemptToGetQuations();

        }
    }


    private void getNextQuestion(int index) {
        answeredit.setText("");
        if (index < finalqndid.size()) {
            QANDA data = finalqndid.get(index);
            listAll.add("-" + data.getQUA());
            adapter.notifyDataSetChanged();
            recycle.scrollToPosition(listAll.size() - 1);

            if (index == 1) {

                answeredit.setInputType(InputType.TYPE_CLASS_TEXT);
                setEditTextMaxLength(100);
            } else if (index == 2) {

                answeredit.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                setEditTextMaxLength(10);

            } else if (index == 3) {

                typing.setVisibility(View.GONE);
                yesno.setVisibility(View.VISIBLE);

            } else if (index == 4) {

                typing.setVisibility(View.VISIBLE);
                yesno.setVisibility(View.GONE);
                answeredit.requestFocus();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(answeredit, InputMethodManager.SHOW_IMPLICIT);


                answeredit.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                setEditTextMaxLength(5);
            }


        } else {
            completed.setVisibility(View.VISIBLE);
            typing.setVisibility(View.GONE);
            yesno.setVisibility(View.GONE);
        }
        recycle.scrollToPosition(listAll.size() - 1);

    }


    @Override
    public void onSuccess(QuationsModel model) {
        constents.dismissProgress();

        data = model.getData();

        for (int i = 0; i < data.size(); i++) {
            QANDA qanda = new QANDA();
            qanda.setTYPE(String.valueOf(data.get(i).getDataType()));
            qanda.setQ_ID(String.valueOf(data.get(i).getId()));
            qanda.setANS("0");
            qanda.setQUA(String.valueOf("-" + data.get(i).getQuestion()));
            dbHandler.insertQandA(qanda);
        }
        get();
    }

    @Override
    public void onSuccessAnswers() {
        allover.setVisibility(View.VISIBLE);
        constents.dismissProgress();

    }

    @Override
    public void onFailure() {
        constents.dismissProgress();

    }

    public void setEditTextMaxLength(int length) {
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(length);
        answeredit.setFilters(filterArray);
    }

    @OnClick(R.id.yes)
    public void yes(View v) {
        send("Yes");
    }

    @OnClick(R.id.submit)
    public void submit(View v) {
        AnswerModel model = new AnswerModel();
        List<QANDA> list = dbHandler.getQandA();
        for (int i = 0; i < list.size(); i++) {
            AnsweredQuations a = new AnsweredQuations();
            a.setAnswer(list.get(i).getANS());
            a.setId(String.valueOf(list.get(i).getQ_ID()));
            answeredQuationsList.add(a);
        }
        model.setQlist(answeredQuationsList);
        model.setAge(age);
        model.setEmail(email);
        model.getFbUserName(name);
        model.setGender(gender);
        model.setMobile(mobile);
        model.setId(id);
        model.setName(name);
        presenter.sendAnswers(model);
        constents.showProgressdialog(this);

    }


    @OnClick(R.id.no)
    public void no(View v) {
        send("No");
    }

    @OnClick(R.id.send)
    public void send(View v) {


        if (!answeredit.getText().toString().equalsIgnoreCase("")) {
            updateAnswer(answeredit.getText().toString());
        }
    }

    public void send(String s) {
        if (!s.equalsIgnoreCase("")) {
            updateAnswer(s);
        }
    }

    public void updateAnswer(String s) {
        listAll.add(s);
        adapter.notifyDataSetChanged();
        recycle.scrollToPosition(listAll.size() - 1);
        dbHandler.updateAnw(finalqndid.get(listAll.size() - (listAll.size() / 2)-1).getQUA(), s);
        getNextQuestion(listAll.size() - (listAll.size() / 2));

    }

}