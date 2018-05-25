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
import com.test.wheelstreettest.adapter.QuationsAdapter;
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
    List<String> quations;
    List<String> answers;
    QuationsAdapter adapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }
    protected void init(){
        dbHandler = new DBHandler(this);
        userDetails = dbHandler.getProfile();
        id = userDetails.getId();
        name = userDetails.getName();
        age = userDetails.getDob();
        gender = userDetails.getGender();
        email = userDetails.getEmail();
        mobile = userDetails.getMobile();

        answers = new ArrayList<>();
        answeredQuations = new AnsweredQuations();
        answeredQuationsList = new ArrayList<>();
        recycle.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        // scroll=recycle;
        constents = new Constents();
        presenter = new Presenter(this);
        constents.showProgressdialog(this);


        presenter.attemptToGetQuations();
    }

    @Override
    public void onSuccess(QuationsModel model) {
        constents.dismissProgress();
        quations = new ArrayList<>();
        data = model.getData();
        quations.add("-" + model.getData().get(i).getQuestion());
        adapter = new QuationsAdapter(this, model, quations);
        answeredit.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        setEditTextMaxLength(2);
        recycle.setAdapter(adapter);
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

        sending("Yes");
    }

    @OnClick(R.id.submit)
    public void submit(View v) {
        AnswerModel model = new AnswerModel();

        for (int i = 0; i < answers.size(); i++) {
            AnsweredQuations a = new AnsweredQuations();
            a.setAnswer(answers.get(i));
            a.setId(i + 1);
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
        sending("No");
    }

    @OnClick(R.id.send)
    public void send(View v) {

        sending("");
    }


    public void sending(String name) {


        if (!answeredit.getText().toString().equals("") || !name.equals("")) {

            if (name.equals("")) {
                adapter.listall(answeredit.getText().toString());

                answers.add(answeredit.getText().toString());
                answeredit.setText("");
            } else {
                if (name.equalsIgnoreCase("yes")) {

                    answers.add("true");
                } else {

                    answers.add("false");
                }

                adapter.listall(name);
            }
            recycle.scrollToPosition(QuationsAdapter.listall.size() - 1);
            send.setEnabled(false);


            if (i < 4) {


                i++;

                if (i == 1) {

                    answeredit.setInputType(InputType.TYPE_CLASS_TEXT);
                    setEditTextMaxLength(100);
                } else if (i == 2) {

                    answeredit.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                    setEditTextMaxLength(10);

                } else if (i == 3) {

                    typing.setVisibility(View.GONE);
                    yesno.setVisibility(View.VISIBLE);

                } else if (i == 4) {

                    typing.setVisibility(View.VISIBLE);
                    yesno.setVisibility(View.GONE);
                    answeredit.requestFocus();

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(answeredit, InputMethodManager.SHOW_IMPLICIT);


                    answeredit.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                    setEditTextMaxLength(5);
                }


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.listall("-" + data.get(i).getQuestion());
                        recycle.scrollToPosition(QuationsAdapter.listall.size() - 1);
                        send.setEnabled(true);
                    }
                }, 0);

            } else {
                completed.setVisibility(View.VISIBLE);
                typing.setVisibility(View.GONE);
                yesno.setVisibility(View.GONE);
            }


        }


        else {
            Toast.makeText(this, "Please Enter Valid Answer", Toast.LENGTH_SHORT).show();
        }
    }

}
