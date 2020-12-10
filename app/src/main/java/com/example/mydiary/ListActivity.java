package com.example.mydiary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    private static final String TAG = "ListActivity";
    private ListView diaryList;
    private List<Diary> diaryListItem;
    private DiaryAdapter diaryAdapter;
    private PopupMenu popupMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);
        diaryList = findViewById(R.id.diary_list);
        diaryListItem = MainActivity.getMySQLite().selectAll();
        diaryAdapter = new DiaryAdapter(this, diaryListItem);
        diaryList.setAdapter(diaryAdapter);


        diaryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popupMenu = new PopupMenu(ListActivity.this, view,  Gravity.TOP);
                popupMenu.getMenuInflater().inflate(R.menu.pop_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.pop_delete:
                                Diary diary = diaryListItem.get(position);
                                diaryListItem.remove(diary);
                                diaryAdapter.notifyDataSetChanged();
                                MainActivity.getMySQLite().delete(diary);
                                break;
                            case R.id.pop_modify:
                                Log.i(TAG, "修改還沒寫");
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
                Log.i(TAG, diaryListItem.get(position).toString());
                //Toast.makeText(ListActivity.this, diaryListItem.get(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });

        diaryList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Diary diary = diaryListItem.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
                builder.setTitle(R.string.message);
                builder.setMessage(getResources().getString(R.string.delete_message) + " \n" + diary.getTitle());
                builder.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        diaryListItem.remove(diary);
                        diaryAdapter.notifyDataSetChanged();
                        MainActivity.getMySQLite().delete(diary);
                    }
                });
                builder.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });

                builder.create().show();

                return true;
            }
        });


    }


    class DiaryAdapter extends BaseAdapter {

        Context context;
        List<Diary> diaryList;
        private LayoutInflater layoutInflater;

        public DiaryAdapter(Context context, List<Diary> diaryList) {
            this.context = context;
            this.diaryList = diaryList;

            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return diaryList.size();
            //return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.listitem_diary, null);

                holder = new ViewHolder();
                holder.titleText = (TextView) convertView.findViewById(R.id.title_text);
                holder.bodyText = (TextView) convertView.findViewById(R.id.body_text);
                holder.dateText = (TextView) convertView.findViewById(R.id.date_text);
                holder.weatherImg = (ImageView) convertView.findViewById(R.id.weather_img);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Diary diary = diaryList.get(position);
            holder.titleText.setText(diary.getTitle());
            holder.dateText.setText(diary.getDate());
            holder.bodyText.setText(diary.getBody());

            int drawId = R.drawable.sun;
            if (diary.getWeather().equals("雨天")) {
                drawId = R.drawable.rain;
            } else if (diary.getWeather().equals("陰天")) {
                drawId = R.drawable.cloudy;
            }
            holder.weatherImg.setImageResource(drawId);

            return convertView;
        }
    }

    //存放資料內容
    class ViewHolder {
        TextView titleText;
        TextView bodyText;
        TextView dateText;
        ImageView weatherImg;
    }

}