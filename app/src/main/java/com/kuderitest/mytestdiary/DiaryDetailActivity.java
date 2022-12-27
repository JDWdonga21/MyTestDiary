package com.kuderitest.mytestdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.radiobutton.MaterialRadioButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DiaryDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mTvDate; // 일시 설정 텍스트
    private EditText mEtTitle, mEtContent; //일기 제목, 일기 내용
    private RadioGroup mRgWeather;

    private String mDetailMode = ""; //intent로 받아냉 게시글 모드
    private String mBeforeDate = ""; //intent로 받아낸 사용자 입력 날짜
    private String mSelectedUserDate = ""; // 선택된 일시 값
    private int mSelectedWeatherType = -1; //선택된 날씨 값

    private DatabaseHelper mDatabaseHelper; // Database Util 객체


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diart_detail);

        // 데이터베이스 객체 생성
        mDatabaseHelper = new DatabaseHelper(this);

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
        mSelectedUserDate = new SimpleDateFormat("yyyy/MM/dd E요일", Locale.KOREAN).format(new Date());
        // 설명 : SimpleDateFormat 간단한 날짜 포멧 설정, format(new Date())_현 디바이스 기준의 시간, 날짜 가져옴
        mTvDate.setText(mSelectedUserDate);

        // 이전 엑티비티로부터 값을 전달 받기
        Intent intent = getIntent();
        if(intent.getExtras() != null){
            //intent putExtra 했던 데이터가 존재한다면 내부를 수행, (캐스팅 작업)
            DiaryModel diaryModel = (DiaryModel) intent.getSerializableExtra("diaryModel");
            mDetailMode = intent.getStringExtra("mode");
            mBeforeDate = diaryModel.getWriteDate(); // 계시글 database update 쿼리문 처리를 위해서 여기서 받아둠

            // 넘겨받은 값을 활용해서 각 필드들에 설정해주기
            mEtTitle.setText(diaryModel.getTitle());
            mEtContent.setText(diaryModel.getContent());
            int weatherType = diaryModel.getWeatherType();
            ((MaterialRadioButton)mRgWeather.getChildAt(weatherType)).setChecked(true);

            mSelectedUserDate = diaryModel.getUserDate();
            mTvDate.setText(diaryModel.getUserDate());

            if(mDetailMode.equals("modify")){
                //수정모드
                TextView tv_header_title = findViewById(R.id.tv_header_title);
                tv_header_title.setText("일기 수정");
            }else if(mDetailMode.equals("detail")) {
                //상세보기
                TextView tv_header_title = findViewById(R.id.tv_header_title);
                tv_header_title.setText("일기 상세보기");

                //읽기 전용 화면으로 표시
                mEtTitle.setEnabled(false);
                mEtContent.setEnabled(false);
                mTvDate.setEnabled(false);
                for(int i = 0; i < mRgWeather.getChildCount(); i++){
                    //라디오 그룹 비활성화
                    mRgWeather.getChildAt(i).setEnabled(false);
                }
                iv_check.setVisibility(View.INVISIBLE);
           }
        }

    }

    @Override
    public void onClick(View view) {
        // setOnClickListener가 붙어있는 뷰들은 클릭이 발생하면 모두 이곳을 수행
        switch (view.getId()){
            case R.id.iv_back:
                //뒤로가기
                finish();
                break;
            case R.id.iv_check:
                //작성 완료
                // 라디오 버튼 선택 값 가져오기
                mSelectedWeatherType = mRgWeather.indexOfChild(findViewById(mRgWeather.getCheckedRadioButtonId()));
                // 입력필드에 값 모두 입력되었는지 체크
                if(mEtTitle.getText().length() == 0){
                    //에러
                    Toast.makeText(this, "제목이 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();
                    return; //아래 로직을 실행하지 않고 돌아감
                }
                if(mEtContent.getText().length() <= 5){
                    Toast.makeText(this, "입력된 내용이 너무 적습니다.", Toast.LENGTH_SHORT).show();
                    return; //아래 로직을 실행하지 않고 돌아감
                }
                if(mSelectedWeatherType == -1){
                    //에러
                    Toast.makeText(this, "날씨를 선택하지 않았습니다.", Toast.LENGTH_SHORT).show();
                    return; //아래 로직을 실행하지 않고 돌아감
                }
                // 데이터 저장
                String title = mEtTitle.getText().toString();       //제목
                String content = mEtContent.getText().toString();   //내용
                String userDate = mSelectedUserDate;                // 사용자가 선택한 일시
                if(userDate.equals("")){
                    //별도 날짜 설정을 하지않은채로 작성완료를 누를 경우
                    userDate = mTvDate.getText().toString();
                }
                String writeDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.KOREAN).format(new Date());

                //데이터 베이스에 저장 또는 업데이트 로직
                if(mDetailMode.equals("modify")){
                    // 계시글 수정 모드
                    mDatabaseHelper.setUpdateDiaryList(title, content, mSelectedWeatherType, userDate, writeDate, mBeforeDate);
                    Toast.makeText(this, "다이어리 수정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                }else {
                    // 신규 작성 모드
                    mDatabaseHelper.setInsertDiaryList(title, content, mSelectedWeatherType, userDate, writeDate);
                    Toast.makeText(this, "다이어리 등록이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                }

                finish();
                break;

            case R.id.tv_date:
                // 일시 작성 텍스트

                // 달력을 띄워서 사용자에게 일시를 입력 받는다.
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        // 달력에 선택 된 (년, 월, 일)을 가지고 와서 다시 캘린더 함수에 넣어줘서 사용자가 선택한 요일을 알아낸다.
                        Calendar innerCal = Calendar.getInstance();
                        innerCal.set(Calendar.YEAR, year);
                        innerCal.set(Calendar.MONTH, month);
                        innerCal.set(Calendar.DATE, day);

                        mSelectedUserDate = new SimpleDateFormat("yyyy/MM/dd E요일", Locale.KOREAN).format(innerCal.getTime());
                        mTvDate.setText(mSelectedUserDate);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                dialog.show(); //다이얼로그 (팝업) 활성화
                break;
        }
    }
}