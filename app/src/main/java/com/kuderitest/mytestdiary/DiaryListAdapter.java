package com.kuderitest.mytestdiary;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// public class DiaryListAdapter extends RecyclerView.Adapter<DiaryListAdapter.ViewHolder>까지 치고
// alt + Enter를 활용하여 작성
public class DiaryListAdapter extends RecyclerView.Adapter<DiaryListAdapter.ViewHolder> {
    ArrayList<DiaryModel> mListDiary; //다이어리 데이터들을 들고 있는 자료형
    // 컨텍스트 : 엑티비티나 화면 단위에서 모든 데이터를 가진 것, DiaryListAdapter가 화면이 아니라서
    // 컨텍스트가 없어서 화면 정보를 받기 위해서 정의해둠
    Context mContext;
    @NonNull
    @Override
    public DiaryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 아이템 뷰가 최초로 생성이 될 때 호출되는 곳
        mContext = parent.getContext();
        View holder = LayoutInflater.from(mContext).inflate(R.layout.list_item_diary, parent, false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryListAdapter.ViewHolder holder, int position) {
        // 생성된 아이템 뷰가 실제 연동이 되어지는 곳

        // 날씨의 경우의 수
        int weatherType = mListDiary.get(position).getWeatherType();
        switch (weatherType){
            case 0:
                //맑음
                holder.iv_weather.setImageResource(R.drawable.img_sun);
                break;
            case 1:
                //흐림
                holder.iv_weather.setImageResource(R.drawable.img_cloud);
                break;
            case 2:
                //비
                holder.iv_weather.setImageResource(R.drawable.img_rainy);
                break;
            case 3:
                //눈
                holder.iv_weather.setImageResource(R.drawable.img_snowy);
                break;
        }
        //제목, 날짜, 일시
        String title = mListDiary.get(position).getTitle();
        String userDate = mListDiary.get(position).getUserDate();

        holder.tv_title.setText(title);
        holder.tv_user_date.setText(userDate);
    }

    @Override
    public int getItemCount() {
        // 아이템 뷰의 총 갯수를 관리하는 곳
        return mListDiary.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_weather;
        TextView tv_title, tv_user_date;

        // 하나의 아이템 뷰
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_weather = itemView.findViewById(R.id.tv_weather); //날씨 이미지
            tv_title = itemView.findViewById(R.id.tv_title); // 다이어리 제목
            tv_user_date = itemView.findViewById(R.id.tv_user_date); //사용자 지정 날짜

            // 일반클릭 (상세보기)
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //현재 클릭된 위치 (배열)
                    int currentPosition = getAdapterPosition();
                    //현재 클릭된 리스트 아이템 정보를 가지는 변수
                    DiaryModel diaryModel = mListDiary.get(currentPosition);
                    //화면 이동 및 다이어리 데이터 다음 엑티비티로 전달
                    Intent diaryDetailIntent = new Intent(mContext, DiartDetailActivity.class);
                    diaryDetailIntent.putExtra("diaryfModel", diaryModel);
                    diaryDetailIntent.putExtra("mode", "detail"); //상세보기 모드
                    mContext.startActivity(diaryDetailIntent);
                }
            });
            // 롱클릭 (선택 옵션 : 수정 / 삭제)
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    //현재 클릭된 위치 (배열)
                    int currentPosition = getAdapterPosition();
                    //현재 클릭된 리스트 아이템 정보를 가지는 변수
                    DiaryModel diaryModel = mListDiary.get(currentPosition);
                    // 버튼 선택지 배열
                    String[] strChiceArray = {"수정하기", "복사하기", "삭제하기"};
                    // 팝업화면 표시
                    new AlertDialog.Builder(mContext)
                            .setTitle("원하시는 동작을 선택하세요.")
                            .setItems(strChiceArray, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int position) {
                                    if(position == 0){
                                        Intent diaryDetailIntent = new Intent(mContext, DiartDetailActivity.class);
                                        diaryDetailIntent.putExtra("diaryfModel", diaryModel);
                                        diaryDetailIntent.putExtra("mode", "modify"); //수정하기 모드
                                        mContext.startActivity(diaryDetailIntent);
                                    }else if(position == 1){

                                        mListDiary.add(diaryModel);
                                        notifyItemChanged(currentPosition);
                                    }
                                    else {
                                        mListDiary.remove(currentPosition);
                                        notifyItemRemoved(currentPosition);
                                    }
                                }
                            }).show();
                    return false;
                }
            });
        }
    }
    public void setSampleList(ArrayList<DiaryModel> lstDiary){
        mListDiary = lstDiary;
    }
}
