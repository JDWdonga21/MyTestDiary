package com.kuderitest.mytestdiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRvDiary; //리사이클러뷰 (리스트 뷰)
    DiaryListAdapter mAdapter; //리사이클러뷰와 연동할 어뎁터
    ArrayList<DiaryModel> mLstDiary; //리스트에 표현할 다이러리 데이터들 (배열)

    DatabaseHelper mDatabaseHelper; //데이터 베이스 헬퍼 클래스


    @Override
    protected void onCreate(Bundle savedInstanceState) { // 엑티비티 시작할 때 최초 1회만 호출
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 데이터 베이스 객체 초기화
        mDatabaseHelper = new DatabaseHelper(this);

        mLstDiary = new ArrayList<>();
        mRvDiary = findViewById(R.id.recycDiary);
        mAdapter = new DiaryListAdapter(); // 리사이클러뷰 어뎁터 인스턴스 생성

//        DiaryModel item = new DiaryModel();
//        item.setId(0);
//        item.setTitle("제목입니다.");
//        item.setContent("내용입니다.");
//        item.setUserDate("2022/12/03 mon");
//        item.setWriteDate("2022/12/03 mon");
//        item.setWeatherType(0);
//        mLstDiary.add(item);
//
//        DiaryModel item2 = new DiaryModel();
//        item2.setId(1);
//        item2.setTitle("제목입니다.2");
//        item2.setContent("내용입니다.2");
//        item2.setUserDate("2022/12/06 Tue");
//        item2.setWriteDate("2022/12/06 Tue");
//        item2.setWeatherType(1);
//        mLstDiary.add(item2);

//        mAdapter.setSampleList(mLstDiary);
        mRvDiary.setAdapter(mAdapter);

        // 엑티비티 (화면이) 실행될 때 가장 먼저 호출되는 곳
        FloatingActionButton floatingActionButton = findViewById(R.id.writeBtn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //작성하기 버튼 클릭시 호출되는 곳
                Intent intent = new Intent(MainActivity.this, DiaryDetailActivity.class);
                startActivity(intent);

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        // 액티비티의 재개

        // get load list
        setLoadRecentList();
    }

    private void setLoadRecentList() {
        //최근 데이터 베이스 정보를 가지고와서 리사이클러뷰에 갱신해준다.

        // 이전에 배열 리스트에 저장된 데이터가 있으면 비워버림.
        if(!mLstDiary.isEmpty()){
            mLstDiary.clear();
        }

        mLstDiary = mDatabaseHelper.getDiaryListFromDB(); // 데이터 베이스로부터 저장되어있는 DB를 확인하여 가지고 옴
        mAdapter.setListInit(mLstDiary);
    }
}