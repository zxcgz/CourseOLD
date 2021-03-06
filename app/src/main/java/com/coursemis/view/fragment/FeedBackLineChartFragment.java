package com.coursemis.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.coursemis.R;
import com.coursemis.model.Chart;
import com.coursemis.model.Course;
import com.coursemis.model.Evaluation;
import com.coursemis.util.HttpUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
 * 折线图展示
 * Created by zhxchao on 2018/3/17.
 */

public class FeedBackLineChartFragment extends BaseFragment {

    private LineChart mLineChart;

    @Override
    public void refresh(Course course) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_line_chart, null);
        initView();
        initData();
        return mView;
    }

    private void initData() {
        // 是否在折线图上添加边框
        mLineChart.setDrawGridBackground(false);
        mLineChart.setDrawBorders(false);
        // 设置右下角描述
        mLineChart.setDescription("");
        //设置透明度
        mLineChart.setAlpha(0.8f);
        //设置网格底下的那条线的颜色
        mLineChart.setBorderColor(Color.rgb(213, 216, 214));
        //设置高亮显示
        mLineChart.setHighlightEnabled(true);
        //设置是否可以触摸，如为false，则不能拖动，缩放等
        mLineChart.setTouchEnabled(true);
        //设置是否可以拖拽
        mLineChart.setDragEnabled(false);
        //设置是否可以缩放
        mLineChart.setScaleEnabled(false);
        //设置是否能扩大扩小
        mLineChart.setPinchZoom(false);
        mLineChart.animateX(4000);
        mLineChart.animateY(3000);   //从Y轴进入的动画
        mLineChart.animateXY(3000, 3000);    //从XY轴一起进入的动画
        //设置最小的缩放
        mLineChart.setScaleMinima(0.5f, 1f);
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("cid", mCourse.getCId() + "");
        params.put("tid", mTeacher.getTId() + "");// /
        client.post(HttpUtil.server_get_callback, params,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONArray response) {

                        super.onSuccess(statusCode, response);
                    }

                    @Override
                    public void onSuccess(int arg0, JSONObject arg1) {
                        Log.e("测试", "接收到反馈信息");
                        String result = arg1.optString("result");
                        Gson gson = new Gson() ;
                        List<Evaluation> list = new ArrayList<>() ;
                        List<Evaluation> evaluations = gson.fromJson(result, list.getClass());
                        for (int i = 0;i<evaluations.size() ;i++){
                            Evaluation evaluation = gson.fromJson(evaluations.toArray()[i].toString(), Evaluation.class);
                            list.add(evaluation) ;
                        }
                        Log.e("解析",list.get(0).getEGrades());
                        if (evaluations.size()==0){
                            //没有数据
                            Toast.makeText(getActivity(),"暂时还没有数据",Toast.LENGTH_SHORT).show();
                        }else {
                            Log.e("测试",evaluations.toArray()[0].toString());
                        }
                        super.onSuccess(arg0, arg1);
                        double[] years;
                        double[] points;
                        years = new double[arg1.optJSONArray(
                                "result").length()];
                        points = new double[arg1.optJSONArray(
                                "result").length()];
                        for (int i = 0; i < arg1.optJSONArray(
                                "result").length(); i++) {
                            JSONObject object = arg1.optJSONArray(
                                    "result").optJSONObject(i);

                            years[i] = object.optDouble("year");
                            points[i] = object.optDouble("point");
                        }
                        Chart chart = new Chart();
                        chart.setYears(years);
                        chart.setPoint(points);// 加载数据
                        LineData data = getLineData(chart);
                        mLineChart.setData(data);
                        Legend l = mLineChart.getLegend();
                        l.setForm(Legend.LegendForm.LINE);  //设置图最下面显示的类型
                        l.setTextSize(15);
                        l.setTextColor(Color.rgb(104, 241, 175));
                        l.setFormSize(30f);
                        // 刷新图表
                        mLineChart.invalidate();

                    }

                    @Override
                    public void onFailure(Throwable e, JSONObject errorResponse) {

                        super.onFailure(e, errorResponse);
                    }
                });
    }

    private LineData getLineData(Chart chart) {
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < chart.getYears().length; i++) {
            xVals.add(chart.getYears()[i] + "");
        }

        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for (int i = 0; i < chart.getPoint().length; i++) {
            yVals.add(new Entry((float) chart.getPoint()[i], i));
        }

        LineDataSet set1 = new LineDataSet(yVals, "时间");
        set1.setDrawCubic(true);  //设置曲线为圆滑的线
        set1.setCubicIntensity(0.2f);
        set1.setDrawFilled(false);  //设置包括的范围区域填充颜色
        set1.setDrawCircles(true);  //设置有圆点
        set1.setLineWidth(2f);    //设置线的宽度
        set1.setCircleSize(5f);   //设置小圆的大小
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setColor(Color.rgb(104, 241, 175));    //设置曲线的颜色

        return new LineData(xVals, set1);
    }

    private void initView() {
        mLineChart = (LineChart) mView.findViewById(R.id.lineChart);
    }
}
