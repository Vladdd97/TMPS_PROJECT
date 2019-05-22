package com.example.vlad.organiserapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class ShowAllEventsActivity extends AppCompatActivity {

    public int textViewIncreaseIndex = 0;
    public int modifyButtonIncreaseIndex = 1000;
    public int deleteButtonIncreaseIndex = 2000;
    public View.OnClickListener modifyButtonOnClickListener;
    public View.OnClickListener deleteButtonOnClickListener;
    public ConstraintLayout showAllEventsConstraintLayout;
    ArrayAdapter<String> adapter;
    private ListView eventsList;
    PopupWindow popupWindow;
    Button deleteButton;
    Button modifyButton;
    TextView popUpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_events);

        //get layout of activity_show_all_events
        showAllEventsConstraintLayout = (ConstraintLayout) findViewById(R.id.ShowAllEventsConstraintLayout);
        // get ListView of events
        eventsList = (ListView) findViewById(R.id.eventsList);

        // create OnClickListener for modify button
        modifyButtonOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId() - modifyButtonIncreaseIndex;

                Intent modifyEventIntent = new Intent(ShowAllEventsActivity.this,ModifyEventActivity.class);
                modifyEventIntent.putExtra("eventId",id);
                startActivityForResult(modifyEventIntent,RequestCodes.MODIFY_ACTIVITY_RESULT);

                Log.d("ShowAllEventsLogger", "id :" + id);
            }
        };

        // create OnClickListener for delete button
        deleteButtonOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId() - deleteButtonIncreaseIndex;
                CustomEventXmlParser.deleteEvent(id);
                //parentLinearLayout.removeAllViews();
                showAllEvents();
                Log.d("ShowAllEventsLogger", "id :" + id);
            }
        };


        showAllEvents();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == RequestCodes.MODIFY_ACTIVITY_RESULT){
            //parentLinearLayout.removeAllViews();
            showAllEvents();
        }

    }

    public void showAllEvents() {
        // close pop up window
        if ( popupWindow != null)
            popupWindow.dismiss();
        // get array of objects
        ArrayList<CustomEvent> customEventsArray = CustomEventXmlParser.getcustomEvents();
        // get array of titles
        ArrayList<String> customEventTitles = new ArrayList<>();

        // set titles
        for(int i = 0 ; i < customEventsArray.size();i++)
            customEventTitles.add(customEventsArray.get(i).getId()+". " + "Title : " + customEventsArray.get(i).getTitle() +
            "\n        Date : " + customEventsArray.get(i).getDate().getDate() + "/" + customEventsArray.get(i).getDate().getMonth() + "/" + customEventsArray.get(i).getDate().getYear() +
            "\n        Time : " + customEventsArray.get(i).getDate().getHours() + ":" + customEventsArray.get(i).getDate().getMinutes());

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, customEventTitles);

        eventsList.setAdapter(adapter);

        // set onClick event for every item of list
        eventsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                              @Override
                                              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                                  //instantiate the popup.xml layout file
                                                  LayoutInflater layoutInflater = (LayoutInflater) ShowAllEventsActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                  View customView = layoutInflater.inflate(R.layout.popup, null);
                                                  // find buttons from pop up window
                                                  modifyButton = (Button) customView.findViewById(R.id.modifyButton);
                                                  deleteButton = (Button) customView.findViewById(R.id.deleteButton);

                                                  // get eventId
                                                  String text = ((TextView)view).getText().toString();
                                                  String [] eventInfoString = text.toString().split("\\.");
                                                  int eventId = Integer.parseInt(eventInfoString[0]);
                                                  // set buttons' id ... needs to perform onClickEvents
                                                  modifyButton.setId(modifyButtonIncreaseIndex + eventId);
                                                  deleteButton.setId(deleteButtonIncreaseIndex + eventId);
                                                  // add OnClickListener to modify button
                                                  modifyButton.setOnClickListener(modifyButtonOnClickListener);
                                                  // add OnClickListener to delete button
                                                  deleteButton.setOnClickListener(deleteButtonOnClickListener);


                                                  // find TextView from pop up window
                                                  popUpTextView = (TextView) customView.findViewById(R.id.popUpTextView);
                                                  popUpTextView.setText(eventInfoString[1]);

                                                  //instantiate popup window
                                                  popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                                                  //display the popup window
                                                  popupWindow.showAtLocation(showAllEventsConstraintLayout, Gravity.CENTER, 0, 0);

                                                  Log.d("fromList", "position : " + position + " | id : " + id +
                                                          " | eventId : " + eventId);
                                              }
                                          } );

/*
            // get parent Layout
            parentLinearLayout =

            findViewById(R.id.eventsLinearLayout);

            ArrayList<CustomEvent> eventList;
            eventList =CustomEventXmlParser.getcustomEvents();
        for(
            int i = 0; i<eventList.size();i++)

            {

                // create child LinearLayout for TextView
                LinearLayout childTextViewLinearLayout = new LinearLayout(ShowAllEventsActivity.this);

                // create new TextView
                TextView newTextView = new TextView(ShowAllEventsActivity.this);
                newTextView.setId(textViewIncreaseIndex + eventList.get(i).getId());
                Date eventDate = eventList.get(i).getDate();
                newTextView.setText("\n" + "" +
                        "ID : " + eventList.get(i).getId() + "\n" +
                        "Title : " + eventList.get(i).getTitle() + "\n" +
                        "Description : " + eventList.get(i).getDescription() + "\n" +
                        "isAlarmSet : " + eventList.get(i).getIsAlarmSet() + "\n" +
                        "Date : " + eventDate.getDate() + "/" + eventDate.getMonth() + eventDate.getYear() + "\n" +
                        "Time: " + eventDate.getHours() + ":" + eventDate.getMinutes());

                // create child LinearLayout for Buttons
                LinearLayout childButtonswLinearLayout = new LinearLayout(ShowAllEventsActivity.this);

                // create modify button
                Button modifyButton = new Button(ShowAllEventsActivity.this);
                modifyButton.setId(modifyButtonIncreaseIndex + eventList.get(i).getId());
                modifyButton.setText("Modify");
                // style of button
                modifyButton.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                modifyButton.setTextColor(getResources().getColor(android.R.color.white));
                // add OnClickListener to modify button
                modifyButton.setOnClickListener(modifyButtonOnClickListener);

                // create delete button
                Button deleteButton = new Button(ShowAllEventsActivity.this);
                deleteButton.setId(deleteButtonIncreaseIndex + eventList.get(i).getId());
                deleteButton.setText("Delete");
                // style of button
                deleteButton.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                deleteButton.setTextColor(getResources().getColor(android.R.color.white));
                // add OnClickListener to delete button
                deleteButton.setOnClickListener(deleteButtonOnClickListener);

                // add TextView to child LinearLayout for TextView
                childTextViewLinearLayout.addView(newTextView);

                // add Buttons to child LinearLayout for Buttons
                childButtonswLinearLayout.addView(modifyButton);
                childButtonswLinearLayout.addView(deleteButton);

                // add childLinearLayouts to parent LinearLayout
                parentLinearLayout.addView(childTextViewLinearLayout);
                parentLinearLayout.addView(childButtonswLinearLayout);

            }
            */

        }

    // search bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu,menu);
        MenuItem item = menu.findItem(R.id.search_event);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void onClick_backButton(View v){
        finish();
    }

    }
