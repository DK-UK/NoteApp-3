package com.example.noteapp.Activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.Adapters.CheckListAdapter;
import com.example.noteapp.Adapters.ListItemsAdapter;
import com.example.noteapp.Database.CheckListDatabase;
import com.example.noteapp.Database.ListItemDatabase;
import com.example.noteapp.Entities.CheckList;
import com.example.noteapp.Entities.CheckListItem;
import com.example.noteapp.R;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.List;

import static com.example.noteapp.Fragments.CheckListFragment.REQUEST_CODE_UPDATE_CHECKLIST;

public class CreateListActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_UPDATE_LIST = 1;
    private static final int REQUEST_CODE_SHOW_LIST = 2 ;
    EditText inputListTitle;
    TextView listDateTime, itemDate, itemTime;
    ImageView imageAddListItem;

    public static final int REQUEST_CODE_ADD_CHECKLIST_ITEM = 1;
    public static final int REQUEST_CODE_UPDATE_CHECKLIST_ITEM = 2;
    public static final int REQUEST_CODE_SHOW_CHECKLIST_ITEM = 3;

    private RecyclerView listRecyclerView;
    private List<CheckListItem> checkListItem;
    private ListItemsAdapter checkListItemAdapter;

    private int listItemClickedPosition = -1;

    private AlertDialog dialogAddItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        itemDate = findViewById(R.id.itemDate);
        itemTime = findViewById(R.id.itemTime);

        ImageView imageAddListItem = findViewById(R.id.imageAddListItem);
        imageAddListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                       showAddItemDialog();
            }
        });

    }

    private void showAddItemDialog() {
        if (dialogAddItem == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(CreateListActivity.this);
            final View view = LayoutInflater.from(this).inflate(
                    R.layout.layout_add_list_item,
                    (ViewGroup) findViewById(R.id.layoutAddItemContainer)
            );
            builder.setView(view);

            dialogAddItem = builder.create();
            if (dialogAddItem.getWindow() != null) {
                dialogAddItem.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            final EditText inputListItem = view.findViewById(R.id.inputListItem);
            inputListItem.requestFocus();

            view.findViewById(R.id.imageAddTime).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PersianCalendar listitemcalendar = new PersianCalendar();
                    TimePickerDialog tpd = TimePickerDialog.newInstance(
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                                    String hourString   = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
                                    String minuteString = minute < 10 ? "0" + minute : "" + minute;
                                    String time  = "You picked the following time: " + hourString + ":" + minuteString;
                                    Toast.makeText(CreateListActivity.this, time, Toast.LENGTH_LONG).show();
                                    itemTime.setText(time);
                                }
                            },
                            listitemcalendar.get(PersianCalendar.HOUR_OF_DAY),
                            listitemcalendar.get(PersianCalendar.MINUTE),
                            true
                    );

                    tpd.setThemeDark(true);
                    tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            Toast.makeText(CreateListActivity.this, "TimePickerDialog Canceled", Toast.LENGTH_LONG).show();
                        }
                    });
                    tpd.show(getFragmentManager(), "FuLLKade");

                }
            });

            view.findViewById(R.id.imageAddDate).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PersianCalendar now = new PersianCalendar();
                    DatePickerDialog dpd = DatePickerDialog.newInstance(
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                    Toast.makeText(CreateListActivity.this, year + "/" + monthOfYear + "/" + dayOfMonth, Toast.LENGTH_LONG).show();
                                    itemDate.setText(year + "/" + monthOfYear + "/" + dayOfMonth);
                                }
                            },
                            now.getPersianYear(),
                            now.getPersianMonth(),
                            now.getPersianDay()
                    );
                    dpd.setThemeDark(true);
                    dpd.show(getFragmentManager(), "FuLLKade");
                }
            });

            view.findViewById(R.id.textAdd).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (inputListItem.getText().toString().trim().isEmpty()) {
                        Toast.makeText(CreateListActivity.this, "Enter your work", Toast.LENGTH_SHORT).show();
                    } else if (itemDate.getText().toString().trim().isEmpty()){
                        Toast.makeText(CreateListActivity.this, "your list item should have date", Toast.LENGTH_SHORT).show();
                        //layoutWebURL.setVisibility(View.VISIBLE);
                        //dialogAddURL.dismiss();
                    }
                }
            });
            view.findViewById(R.id.textCancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogAddItem.dismiss();
                }
            });
        }
        dialogAddItem.show();
    }


    @Override
    public void onListItemClicked(CheckListItem checkListItem, int position) {
        listItemClickedPosition = position;
        Intent intent = new Intent(this, CreateListActivity.class);
        intent.putExtra("isViewOrUpdate", true);
        intent.putExtra("listitem", checkListItem);
        startActivityForResult(intent, REQUEST_CODE_UPDATE_LIST);
    }

    private void getCheckList(final int requestCode, final Boolean isListDeleted) {

        @SuppressLint("StaticFieldLeak")
        class getCheckListTask extends AsyncTask<String, String, List<CheckList>> {

            @Override
            protected List<CheckListItem> doInBackground(String... params) {
                return ListItemDatabase
                        .getDatabase(CreateListActivity.this)
                        .ListItemDao().getAllLists();
            }

            @Override
            protected void onPostExecute(List<CheckListItem> checkListItem) {
                super.onPostExecute(checkListItem);
                if (requestCode == REQUEST_CODE_SHOW_LIST) {
                    checkListItem.addAll(checkListItem);
                    checkListItemAdapter.notifyDataSetChanged();
                } else if (requestCode == REQUEST_CODE_ADD_CHECKLIST_ITEM) {
                    checkListItem.add(0, checkListItem.get(0));
                    listRecyclerView.smoothScrollToPosition(0);
                } else if (requestCode == REQUEST_CODE_UPDATE_CHECKLIST) {
                    checkListItem.remove(listClickedPosition);
                    if (isListDeleted) {
                        checkListAdapter.notifyItemRemoved(listClickedPosition);
                    } else {
                        checkList.add(listClickedPosition, checkList.get(listClickedPosition));
                        checkListAdapter.notifyItemChanged(listClickedPosition);
                    }
                }
            }
        }
        new getCheckListTask().execute();
    }
}
