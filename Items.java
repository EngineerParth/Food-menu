package com.example.parth.foodmenu;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class Items extends ListActivity {

    int total = 0;
    String[] selectedItems=new String[this.MAXITEMS];
    int count=0;
    public static String TOTAL = "TOTAL";
    public static String SELECTED="SELECTED";
    public static int MAXITEMS=20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        setListAdapter(new MyAdapter(this,android.R.layout.simple_list_item_2, R.id.txtPrice, getResources().getStringArray(R.array.items)));

    }

    private class MyAdapter extends ArrayAdapter<String>{

        public MyAdapter(Context context, int resource, int textViewResourceId, String[] objects) {
            super(context, resource, textViewResourceId, objects);
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
            View row=inflater.inflate(R.layout.item_list,parent,false);

            //because it is being accessed from inner event handler anonymous class, it needs to be declared final
            final String[] items = getResources().getStringArray(R.array.items);
            final String[] prices = getResources().getStringArray(R.array.prices);

            //Toast.makeText(super.getContext(),prices[0],Toast.LENGTH_LONG).show();
            ((CheckBox)row.findViewById(R.id.chkItem)).setText(items[position]);
            ((CheckBox)row.findViewById(R.id.chkItem)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    CheckBox cb=(CheckBox)compoundButton;
                    if(cb.isChecked()){
                        total+=Integer.parseInt(prices[position]);
                        selectedItems[count++]=items[position];
                    }
                    else
                    {
                        total-=Integer.parseInt(prices[position]);
                        int removableItemIndex=-1;
                        do{
                            removableItemIndex++;
                        }while(!selectedItems[removableItemIndex].equals(cb.getText()));
                        selectedItems[removableItemIndex]=null;

                        //Remove algorithm needs to be worked out and put over here
                    }
                    //For testing purpose
                    //Toast.makeText(parent.getContext(),""+total,Toast.LENGTH_SHORT).show();
                }
            });
            TextView priceView = (TextView)row.findViewById(R.id.txtPrice);
            String index=items[position];
//            if(index.equals("Sandwich")){
//                priceView.setText("25");
//            }
//            else if(index.equals("Dhosa")){
//                priceView.setText("50");
//            }
//            else if(index.equals("Pizza")){
//                priceView.setText("65");
//            }
//            else if(index.equals("Fruit Salad")){
//                priceView.setText("30");
//            }

            //working after some 5-10 minutes of debugging, apparently, the bug was in layout file.
            ((TextView)row.findViewById(R.id.txtPrice)).setText(prices[position]);
            return row;

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar wilfffl
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Event handler for Next button
    public void nextButtonClicked(View view){
        if(total==0){
            Toast.makeText(this,"Please select some food items",Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent;
        intent = new Intent(this,DisplayBill.class);
        intent.putExtra(TOTAL,""+total);
        intent.putExtra(SELECTED,selectedItems);
        startActivity(intent);
    }
}
