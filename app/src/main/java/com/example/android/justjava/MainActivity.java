package com.example.android.justjava;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar;
        actionBar = getSupportActionBar();

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF9800"));
        assert actionBar != null;
        actionBar.setBackgroundDrawable(colorDrawable);
    }

    public void orderSubmitted(View view) {
        EditText text = findViewById(R.id.name_field);
        String name = text.getText().toString();

        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_check_box);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCreamCheckBox = findViewById(R.id.chocolate_check_box);
        boolean hasChocolateCream = chocolateCreamCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolateCream);

        String message = createOrderSummary(price, hasWhippedCream, hasChocolateCream, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT,message);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }

    int calculatePrice(boolean hasWhipped, boolean hasChocolate){
        int basePrice = 5;

        if(hasChocolate){
            basePrice += 2;
        }
        if(hasWhipped){
            basePrice += 1;
        }
        return quantity * basePrice;
    }

    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolateCream, String name) {
        String message = getString(R.string.order_summary_name, name);
        message += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        message += "\n" + getString(R.string.order_summary_chocolate_cream, addChocolateCream);
        message += "\n" + getString(R.string.order_summary_quantity, quantity);
        message += "\n" + getString(R.string.order_summary_price, price);
        message += "\n" + getString(R.string.thank_you);
        return message;
    }

    private void display(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    public void increment(View view) {
        if(quantity == 100){
            Toast.makeText(this,getString(R.string.toastFor100),Toast.LENGTH_SHORT).show();
            return;
        }
        quantity += 1;
        display(quantity);
    }

    public void decrement(View view) {
        if(quantity == 1){
            Toast.makeText(this,getString(R.string.toastFor1),Toast.LENGTH_SHORT).show();
            return;
        }
        quantity -= 1;
        display(quantity);
    }
}