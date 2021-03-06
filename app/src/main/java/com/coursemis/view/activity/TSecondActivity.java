package com.coursemis.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
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
import com.coursemis.util.HttpUtil;
import com.coursemis.view.fragment.AddCourseFragment;
import com.coursemis.view.fragment.AddStudentFragment;
import com.coursemis.view.fragment.BaseFragment;
import com.coursemis.view.fragment.FeedBackHistogramFragment;
import com.coursemis.view.fragment.FeedBackLineChartFragment;
import com.coursemis.view.fragment.FeedBackSectorChartFragment;
import com.coursemis.view.fragment.FileAddHomeworkFragment;
import com.coursemis.view.fragment.FileHomeworkFragment;
import com.coursemis.view.fragment.FileResourceFragment;
import com.coursemis.view.fragment.PasswordChangeFragment;
import com.coursemis.view.fragment.SettingCourseSettingFragment;
import com.coursemis.view.fragment.SettingStudentManagerFragment;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

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
 *不知道怎么命名
 * 在教师主页面点击跳转都跳转到这个Activity
 * Created by zhxchao on 2018/3/24.
 */

public class TSecondActivity extends AppCompatActivity
implements Toolbar.OnMenuItemClickListener{

    //Intent传参时的标志

    //柱状图
    public final static String Histogram = "柱状图" ;
    //折线图
    public final static String LineChart = "折线图" ;
    //扇形图
    public final static String SectorChart = "扇形图" ;
    //学生反馈
    public final static String StudentFeedBack = "学生反馈" ;

    public static final String TYPE = "type";
    public static final String COURSE_SETTING = "课程管理";
    public static final String STUDENT_MANAGER = "学生管理";
    public static final String ADD_COURSE = "添加课程";
    public static final String ADD_STUDENT = "添加学生";
    public static final String CHANGE_PASSWORD = "修改密码";

    public static final String HOMEWORK = "作业";
    public static final String RESOURCE = "资源管理";

    public static final String ADD_HOMEWORK = "添加随堂测验" ;


    public static final int ADD_SUCCESS = 1;


    private Toolbar mTitle;
    private FrameLayout mContent;
    private Teacher mTeacher;
    private Course mCourse;
    private String mType;
    private BaseFragment mFragment;
    private AlertDialog mAlertDialog;

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
            case COURSE_SETTING:
                //课程设置
                mFragment = new SettingCourseSettingFragment();
                mFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.content, mFragment);
                fragmentTransaction.commit();
                break;
            case STUDENT_MANAGER:
                //学生管理
                setSupportActionBar(mTitle);
                mTitle.setOnMenuItemClickListener(this);
                mFragment = new SettingStudentManagerFragment();
                mFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.content, mFragment);
                fragmentTransaction.commit();
                break;
            case ADD_COURSE:
                //添加课程
                mFragment = new AddCourseFragment();
                mFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.content, mFragment);
                fragmentTransaction.commit();
                break;
            case ADD_STUDENT:
                //添加学生
                mFragment = new AddStudentFragment();
                mFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.content, mFragment);
                fragmentTransaction.commit();
                break;
            case CHANGE_PASSWORD:
                //修改密码
                mFragment = new PasswordChangeFragment();
                mFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.content, mFragment);
                fragmentTransaction.commit();
                break;
            case Histogram :
                //直方图
                mFragment = new FeedBackHistogramFragment() ;
                mFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.content,mFragment) ;
                fragmentTransaction.commit() ;
                break;
            case LineChart :
                //折线图
                mFragment = new FeedBackLineChartFragment();
                mFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.content,mFragment) ;
                fragmentTransaction.commit() ;
                break;
            case SectorChart :
                //饼图
                mFragment = new FeedBackSectorChartFragment();
                mFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.content,mFragment) ;
                fragmentTransaction.commit() ;
                break;
            case StudentFeedBack :
                //反馈建议
                break;
            case HOMEWORK:
                //随堂测验
                setSupportActionBar(mTitle);
                mTitle.setOnMenuItemClickListener(this);
                mFragment = new FileHomeworkFragment();
                mFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.content, mFragment);
                fragmentTransaction.commit();
                break;
            case RESOURCE:
                //资源管理
                setSupportActionBar(mTitle);
                mTitle.setOnMenuItemClickListener(this);
                mFragment = new FileResourceFragment();
                mFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.content, mFragment);
                fragmentTransaction.commit();
                break;
            case ADD_HOMEWORK:
                //添加随堂测验
                mFragment = new FileAddHomeworkFragment();
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
        setContentView(R.layout.activity_t_second);
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
        if (item.getItemId() == R.id.add_course){
            if (mFragment instanceof SettingStudentManagerFragment){
                Intent intent = new Intent();
                intent.putExtra("teacher", mTeacher);
                intent.putExtra("course", mCourse);
                intent.putExtra(TYPE, ADD_STUDENT);
                intent.setClass(this, TSecondActivity.class);
                startActivityForResult(intent, ADD_SUCCESS);
            }else if (mFragment instanceof FileHomeworkFragment){
                //弹出对话框
                //发送消息
                mAlertDialog = new AlertDialog.Builder(this)
                        .setMessage("是否发送本次课的随堂测验信息")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //发送消息
                                sendTestMess() ;
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mAlertDialog.dismiss();
                            }
                        }).create();
                mAlertDialog.show();
            }
        }
        return false;
    }

    private void sendTestMess() {
        AsyncHttpClient client = new AsyncHttpClient() ;
        RequestParams params = new RequestParams() ;
        params.put("tid",mTeacher.getTId()+"");
        params.put("cid",mCourse.getCId()+"");
        client.post(HttpUtil.server_send_test_message,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                Toast.makeText(TSecondActivity.this,"发送成功",Toast.LENGTH_SHORT).show();
            }
        }) ;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ADD_SUCCESS:
                mFragment.refresh(mCourse);
                break;
        }
    }
}
