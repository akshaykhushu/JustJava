package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    private String message,name;
    private boolean hasWhippedCream = false;
    private boolean hasChocolate = false;
    private int numberOfCoffees = 0;
    private String price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onSaveInstanceState(Bundle sIS) {
        Log.v("NOTICE", "saveInstanceState");
        sIS.putString("price", ((TextView) findViewById(R.id.price_text_view)).getText().toString());
        sIS.putString("msg", message);
        sIS.putBoolean("hasWhippedCream", hasWhippedCream);
        sIS.putBoolean("hasChocolate", hasChocolate);
        sIS.putInt("quant", numberOfCoffees);
        sIS.putString("name", (((EditText) findViewById(R.id.editTextName))).getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle sIS) {
        if (sIS != null){
            Log.v("NOTICE", "restoreInstanceState");
            TextView q = (TextView) findViewById(R.id.quantity_text_view);
            numberOfCoffees = sIS.getInt("quant");
            q.setText(String.valueOf(numberOfCoffees));
            TextView tp = (TextView) findViewById(R.id.price_text_view);
            price = sIS.getString("price");
            tp.setText(price);
            CheckBox hwc = (CheckBox) findViewById(R.id.whippedCreamCheckBox);
            hasWhippedCream = sIS.getBoolean("hasWhippedCream");
            hwc.setChecked(hasWhippedCream);
            CheckBox hc = (CheckBox) findViewById(R.id.choclateCheckBox);
            hasChocolate = sIS.getBoolean("hasChocolate");
            hc.setChecked(hasChocolate);
            message = sIS.getString("msg");
            name = sIS.getString("name");
            (((EditText) findViewById(R.id.editTextName))).setText(name);

        }
    }


    public void submitOrder(View view) {
        displayPrice(numberOfCoffees * 5);
        String[] address = new String[1];
        address[0] = "akshaykhushu.ak@gmail.com";
        String subject = "JustJava Order Confirmation";
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, address);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
    }

    public void increment(View view){
        numberOfCoffees += 1;
        displayPrice(numberOfCoffees * 5);
        display(numberOfCoffees);
    }

    public void decrement(View view){
        if (numberOfCoffees > 0)
            numberOfCoffees -= 1;
        else
        {
            Context context = getApplicationContext();
            String error = "You can not have less than 0 coffee";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context,error,duration);
            toast.show();
        }
        displayPrice(numberOfCoffees * 5);
        display(numberOfCoffees);
    }

    public void whippedCream(View view){
        CheckBox whipCream = (CheckBox) findViewById(R.id.whippedCreamCheckBox);
        CheckBox choclate = (CheckBox) findViewById(R.id.choclateCheckBox);
        hasChocolate = choclate.isChecked();
        hasWhippedCream = whipCream.isChecked();
    }

    private void displayPrice(int number) {
        TextView priceText = (TextView) findViewById(R.id.price_text_view);
        priceText.setText(NumberFormat.getCurrencyInstance().format(number));
        EditText nameText = (EditText) findViewById(R.id.editTextName);
        String name = nameText.getText().toString();
        message = "Name: " + name;
        if (hasWhippedCream == true)
        {
            message = message + "\nHas Whipped Cream";
        }
        if (hasChocolate == true)
        {
            message = message + "\nHas Chocolate";
        }
        message = message + "\nQuantity :" + numberOfCoffees;
        message = message + "\nTotal :" + number + "\nThank You";
    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}