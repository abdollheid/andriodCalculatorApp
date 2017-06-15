package com.example.abdullah.calculator;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private History history ;
    private LinearLayout result;
    private LayoutInflater  inflater ;
    private EditText total ,box , factor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void calculate(View view){

        int t = (new Integer(total.getText().toString()));
        history.lastThreeValue[0] = t;
        int b  = new Integer(box.getText().toString()) ;
        history.lastThreeValue[1] = b ;
        int f = (new Integer(factor.getText().toString()));
        history.lastThreeValue[2] = f ;
        Integer answer = (t-b)*f;







        result.addView(inflater.inflate(R.layout.text_form,null),0);
        history.add(answer);
        TextView txt  = (TextView)(findViewById(R.id.result_form));
        txt.setText("result: "+history.getLastValue()+" EG");
        txt = (TextView)findViewById(R.id.date_form);
        txt.setText(DateFormat.getDateInstance().format(history.getLastDate()));



    }
    @Override
    protected void onStart(){
        super.onStart();

        File file =new File(getBaseContext().getFilesDir(),"data.t"); // your app directory


        if(file.exists()){
            try {

                ObjectInputStream in =new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));

                history=((History) in.readObject());

                in.close();



            }catch (Exception ex){
                ex.printStackTrace();
            }}
        else {
           history = new History();
        }

         result = (LinearLayout) findViewById(R.id.results);
          inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0 ; i <history.getSize();++i){
            result.addView(inflater.inflate(R.layout.text_form,null),0);
            TextView txt  = (TextView)(findViewById(R.id.result_form));
            txt.setText("result: "+history.getValue(i)+" EG");
            txt = (TextView)findViewById(R.id.date_form);
            txt.setText(DateFormat.getDateInstance().format(history.getDate(i)));
        }
         total = (EditText) findViewById(R.id.total) ;
         total.setText(history.lastThreeValue[0]+"");
         box = (EditText) findViewById(R.id.box) ;
         box.setText(history.lastThreeValue[1]+"");
         factor =(EditText) findViewById(R.id.factor) ;
         factor.setText(history.lastThreeValue[2]+"");



    }

    protected void onStop(){

        super.onStop();
        File file =new File(getBaseContext().getFilesDir(),"data.t");
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            outputStream.writeObject(history);
            outputStream.close();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
//
class History implements Serializable{
    ArrayList<Integer> values ;
    ArrayList<Date> dates ;
    public Integer lastThreeValue [] ;

    public  History(){
        values = new ArrayList<>();
        dates = new ArrayList<>();
        lastThreeValue = new Integer[3] ;
    }
    public void add(int value){
        Date date = new Date() ;
        values.add(value);
        dates.add(date);
    }
    public Integer getLastValue(){
        return values.get(values.size()-1);
    }
    public Integer getValue(int i){
        return values.get(i);
    }
    public Date getLastDate(){
        return  dates.get(dates.size()-1);
    }
    public Date getDate(int i ){
        return dates.get(i);
    }
    public int getSize(){
        return values.size() ;
    }


}