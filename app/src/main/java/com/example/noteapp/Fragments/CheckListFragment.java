package com.example.noteapp.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.noteapp.Activities.CreateListActivity;
import com.example.noteapp.Activities.CreateNoteActivity;
import com.example.noteapp.Adapters.CheckListAdapter;
import com.example.noteapp.Database.CheckListDatabase;
import com.example.noteapp.Entities.CheckList;
import com.example.noteapp.Listeners.CheckListListener;
import com.example.noteapp.R;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.noteapp.Fragments.NotesFragment.REQUEST_CODE_ADD_NOTE;
import static com.example.noteapp.Fragments.NotesFragment.REQUEST_CODE_SHOW_NOTES;
import static com.example.noteapp.Fragments.NotesFragment.REQUEST_CODE_UPDATE_NOTE;


public class CheckListFragment extends Fragment implements CheckListListener {

    public static final int REQUEST_CODE_ADD_CHECKLIST = 1;
    public static final int REQUEST_CODE_UPDATE_CHECKLIST = 2;
    public static final int REQUEST_CODE_SHOW_CHECKLIST = 3;

    private RecyclerView checkListRecyclerView;
    private List<CheckList> checkList;
    private CheckListAdapter checkListAdapter;

    private int listClickedPosition = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_checklist, container, false);

        checkListRecyclerView = view.findViewById(R.id.checkListRecyclerView);
        checkListRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        checkList = new ArrayList<>();
        checkListAdapter = new CheckListAdapter(checkList, this);
        checkListRecyclerView.setAdapter(checkListAdapter);

        ImageView imageAddListMain = view.findViewById(R.id.imageAddListMain);
        imageAddListMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        new Intent(getContext(), CreateListActivity.class),
                        REQUEST_CODE_ADD_CHECKLIST);
            }
        });

        getCheckList(REQUEST_CODE_SHOW_CHECKLIST, false);

        /*
        EditText inputSearch = view.findViewById(R.id.inputSearch);
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkListAdapter.cancelTimer();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (checkList.size() != 0) {
                    checkListAdapter.searchLists(s.toString());
                }
            }
        });


         */
        view.findViewById(R.id.imageAddListMain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        new Intent(getContext(), CreateListActivity.class),
                        REQUEST_CODE_ADD_NOTE);
            }
        });

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_UPDATE_CHECKLIST && resultCode == RESULT_OK) {
            getCheckList(REQUEST_CODE_ADD_CHECKLIST, false);
        } else if (requestCode == REQUEST_CODE_UPDATE_CHECKLIST && resultCode == RESULT_OK) {
            if (data != null) {
                getCheckList(REQUEST_CODE_ADD_CHECKLIST, data.getBooleanExtra("isListDeleted", false));
            }
        }

    }

    @Override
    public void onCheckListClicked(CheckList checkList, int position) {
        listClickedPosition = position;
        Intent intent = new Intent(getContext(), CreateNoteActivity.class);
        intent.putExtra("isViewOrUpdate", true);
        intent.putExtra("checklist", checkList);
        startActivityForResult(intent, REQUEST_CODE_UPDATE_CHECKLIST);
    }

    private void getCheckList(final int requestCode, final Boolean isListDeleted) {

        @SuppressLint("StaticFieldLeak")
        class getCheckListTask extends AsyncTask<String, String, List<CheckList>> {

            @Override
            protected List<CheckList> doInBackground(String... params) {
                return CheckListDatabase
                        .getDatabase(getContext())
                        .checkListDao().getAllLists();
            }

            @Override
            protected void onPostExecute(List<CheckList> notes) {
                super.onPostExecute(notes);
                if (requestCode == REQUEST_CODE_SHOW_CHECKLIST) {
                    checkList.addAll(checkList);
                    checkListAdapter.notifyDataSetChanged();
                } else if (requestCode == REQUEST_CODE_ADD_CHECKLIST) {
                    checkList.add(0, checkList.get(0));
                    checkListRecyclerView.smoothScrollToPosition(0);
                } else if (requestCode == REQUEST_CODE_UPDATE_CHECKLIST) {
                    checkList.remove(listClickedPosition);
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



