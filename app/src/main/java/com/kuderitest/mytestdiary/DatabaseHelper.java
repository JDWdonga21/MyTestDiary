package com.kuderitest.mytestdiary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * 데이터베이스 관리 클래스 (유틸 클래스)
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    // SQLite = 안드로이드에서 지원하는 앱 내부 데이터베이스 시스템
    private static final String DB_NAME = "MyDiart.db";
    private static final int DB_VERSION = 1;

    // 생성자(constructor)
    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Database Create
        // SQL 이란 Database 접근하기 위해서 명령을 내리는 쿼리문
        // 테이블 생성 1회성
        db.execSQL("CREATE TABLE IF NOT EXISTS DiaryInfo(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL," +
                "content TEXT NOT NULL," +
                "weatherType INTEGER NOT NULL," +
                "userDate TEXT NOT NULL," +
                "writeDate TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { onCreate(db); }

    /**
     * 다이어리 작성 데이터를 DB에 저장한다.(INSERT) - create
     */
    public void setInsertDiaryList(String _title, String _content, int _weatherType, String _userDate, String _writeDate) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO DiaryInfo (title, content, weatherType, userDate, writeDate) " +
                "VALUES ('" + _title + "','" + _content + "','" + _weatherType + "','" + _userDate + "','" + _writeDate + "')");

    }
    /**
     * 기존 작성 데이터를 수정한다. (UPDATE)
     */
    public void setUpdateDiaryList(String _title, String _content, int _weatherType, String _userDate, String _writeDate, String _beforeDate) {
        // _beforeDate : KEY 값
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE DiaryInfo SET title = '"+ _title +"', content = '"+ _content +"', weatherType = '"+ _weatherType +"', userDate = '"+ _userDate +"', writeDate '"+ _writeDate +"' WHERE writeDate = '"+ _beforeDate + "' ");
    }
    /**
     * 기존 작성 데이터를 삭제한다. (DELETE)
     */
    public void setDeleteDiaryList(String _writeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM DiaryInfo WHERE writeDate = '"+ _writeDate +"'");
    }

}
