package com.example.android.justjava;

/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/



         import android.content.Intent;
         import android.net.Uri;
         import android.os.Bundle;
         import android.support.v7.app.AppCompatActivity;
         import android.text.Editable;
         import android.util.Log;
         import android.view.View;
         import android.widget.CheckBox;
         import android.widget.EditText;
         import android.widget.TextView;
         import android.widget.Toast;

         import java.text.NumberFormat;

         import static com.example.android.justjava.R.layout.activity_main;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        boolean hasChocolate = chocolateCheckBox.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);
        Intent emailOrder = new Intent(Intent.ACTION_SENDTO);
        emailOrder.setData(Uri.parse("mailto:")); // only email apps should handle this
        emailOrder.putExtra(Intent.EXTRA_SUBJECT, "JustJava Order for " + name);
        emailOrder.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (emailOrder.resolveActivity(getPackageManager()) != null) {
            startActivity(emailOrder);
        }
    }
    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100){
            Toast.makeText(this, "You can't buy more than 100 coffees.", Toast.LENGTH_SHORT).show();
            return;
        }
            quantity = quantity + 1;
            displayQuantity(quantity);
    }
    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if(quantity == 1) {
            Toast.makeText(this, "You can't buy no coffees", Toast.LENGTH_SHORT).show();
            return;
        }
            quantity = quantity - 1;
            displayQuantity(quantity);

    }
    /**
     * Create summary of the order.
     *
     * @param hasWhippedCream is whether or not the user wants whipped cream topping
     * @param hasChocolate is whether or not the user wants chocolate topping
     * @param price of the order
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean hasWhippedCream, boolean hasChocolate){
        String priceMessage = "Name: " + name;
        priceMessage = priceMessage + "\nAdd whipped cream? " + hasWhippedCream;
        priceMessage = priceMessage + "\nAdd chocolate? " + hasChocolate;
        priceMessage = priceMessage + "\nQuantity: " + quantity + "\n" + "$" + price;
        priceMessage = priceMessage + "\nThank you!";
        return priceMessage;
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays the given order summary on the screen.
     */


    /**
     * Calculates the price of the order.
     *
     *
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int pricePerCup = 5;
        int priceWhippedCream = 1;
        int priceChocolate = 2;
        int basePrice = 5;
        if (hasWhippedCream){
            basePrice = basePrice + priceWhippedCream;
        }
        if (hasChocolate){
            basePrice = basePrice + priceChocolate;
        }
        int price = quantity * basePrice;
        return price;
    }

}
