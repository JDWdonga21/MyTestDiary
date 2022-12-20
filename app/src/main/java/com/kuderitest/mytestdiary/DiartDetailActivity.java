package com.kuderitest.mytestdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DiartDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mTvDate; // 일시 설정 텍스트
    private EditText mEtTitle, mEtContent; //일기 제목, 일기 내용
    private RadioGroup mRgWeather;

    private String mDetailMode = ""; //intent로 받아냉 게시글 모드
    private String mBeforeDate = ""; //intent로 받아낸 사용자 입력 날짜
    private String mSelectedUserDate = ""; // 선택된 일시 값
    private int mSelectedWeatherType = -1; //선택된 날씨 값


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diart_detail);

        mTvDate = findViewById(R.id.tv_date);  //일시 설정 텍스트
        mEtTitle = findViewById(R.id.et_title); //제목 입력필드
        mEtContent = findViewById(R.id.et_content); //내용 입력필드
        mRgWeather = findViewById(R.id.rg_weather); // 날씨 선택 라디오 그룹

        ImageView iv_back = findViewById(R.id.iv_back); //뒤로가기 버튼
        ImageView iv_check = findViewById(R.id.iv_check); //작성완료 버튼

        mTvDate.setOnClickListener(this); //클릭 기능 부여
        iv_back.setOnClickListener(this); //클릭 기능 부여
        iv_check.setOnClickListener(this); //클릭 기능 부여

        // 기본으로 설정된 날짜의 값을 지정
        String defaultDate = new SimpleDateFormat("yyyy/MM/dd E요일", Locale.KOREAN).format(new Date());
        // 설명 : 이후 강의에서 이어서
    }

    @Override
    public void onClick(View view) {
        // setOnClickListener가 붙어있는 뷰들은 클릭이 발생하면 모두 이곳을 수행
        switch (view.getId()){
            case R.id.iv_back:
                //뒤로가기
                break;
            case R.id.iv_check:
                //작성 완료
                break;

            case R.id.tv_date:
                // 일시 작성 텍스트
                break;
        }
    }
}