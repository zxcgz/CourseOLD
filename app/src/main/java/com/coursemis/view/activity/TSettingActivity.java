package com.coursemis.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.coursemis.R;
import com.coursemis.model.Course;
import com.coursemis.model.Teacher;
import com.coursemis.view.fragment.AddStudentFragment;
import com.coursemis.view.fragment.BaseFragment;
import com.coursemis.view.fragment.AddCourseFragment;
import com.coursemis.view.fragment.PasswordChangeFragment;
import com.coursemis.view.fragment.SettingCourseSettingFragment;
import com.coursemis.view.fragment.SettingStudentManagerFragment;

/**
 * _oo0oo_
 * o8888888o
 * 88" . "88
 * (| -_- |)
 * 0\  =  /0
 * ___/`---'\___
 * .' \\|     |// '.
 * / \\|||  :  |||// \
 * / _||||| -:- |||||- \
 * |   | \\\  -  /// |   |
 * | \_|  ''\---/''  |_/ |
 * \  .-\__  '-'  ___/-. /
 * ___'. .'  /--.--\  `. .'___
 * ."" '<  `.___\_<|>_/___.' >' "".
 * | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 * \  \ `_.   \_ __\ /__ _/   .-` /  /
 * =====`-.____`.___ \_____/___.-`___.-'=====
 * `=---='
 * <p>
 * <p>
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Created by zhxchao on 2018/3/22.
 */

public class TSettingActivity extends AppCompatActivity
        implements Toolbar.OnMenuItemClickListener {

    public static final String TYPE = "type";
    public static final String COURSESETTING = "课程管理";
    public static final String STUDENTMANAGER = "学生管理";
    public static final String ADDCOURSE = "添加课程";
    public static final String ADDSTUDENT = "添加学生";
    public static final String CHANGEPASSWORD = "修改密码";


    public static final int ADDSUCCESS = 1;


    private Toolbar mTitle;
    private FrameLayout mContent;
    private Teacher mTeacher;
    private Course mCourse;
    private String mType;
    private BaseFragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntent();
        initView();
        initTitle();
        initData();
    }

    private void initTitle() {
        mTitle.setTitle(mType);
        mTitle.setTitleTextColor(Color.WHITE);
    }

    private void initData() {

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("teacher", mTeacher);
        bundle.putSerializable("course", mCourse);
        switch (mType) {
            case COURSESETTING:
                mFragment = new SettingCourseSettingFragment();
                mFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.content, mFragment);
                fragmentTransaction.commit();
                break;
            case STUDENTMANAGER:
                setSupportActionBar(mTitle);
                mTitle.setOnMenuItemClickListener(this);
                mFragment = new SettingStudentManagerFragment();
                mFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.content, mFragment);
                fragmentTransaction.commit();
                break;
            case ADDCOURSE:
                mFragment = new AddCourseFragment();
                mFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.content, mFragment);
                fragmentTransaction.commit();
                break;
            case ADDSTUDENT:
                mFragment = new AddStudentFragment();
                mFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.content, mFragment);
                fragmentTransaction.commit();
                break;
            case CHANGEPASSWORD:
                mFragment = new PasswordChangeFragment();
                mFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.content, mFragment);
                fragmentTransaction.commit();
                break;
        }
    }

    private void initIntent() {
        Intent intent = getIntent();
        mTeacher = (Teacher) intent.getSerializableExtra("teacher");
        mCourse = (Course) intent.getSerializableExtra("course");
        mType = intent.getStringExtra(TYPE);
    }

    private void initView() {
        setContentView(R.layout.activity_t_setting);
        mTitle = (Toolbar) findViewById(R.id.title);
        mContent = (FrameLayout) findViewById(R.id.content);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_course, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Toast.makeText(TSettingActivity.this, "点击了....", Toast.LENGTH_SHORT).show();
        //添加学生
        switch (item.getItemId()) {
            case R.id.add_course:
                Intent intent = new Intent();
                intent.putExtra("teacher", mTeacher);
                intent.putExtra("course", mCourse);
                intent.putExtra(TYPE, ADDSTUDENT);
                intent.setClass(this, TSettingActivity.class);
                startActivityForResult(intent, ADDSUCCESS);
                break;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("测试", "返回》》》》" + requestCode);
        switch (requestCode) {
            case ADDSUCCESS:
                mFragment.refresh(mCourse);
                break;
        }
    }
}
