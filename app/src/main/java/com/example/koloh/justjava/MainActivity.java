package com.example.koloh.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 0;
    int price = quantity * 5;
    String priceMessage = "Total: €" + price + "\nThank you!";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Helpful for screen rotation, ensures that data is saved when screen rotates horizontally and vertically
     */
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt("number of coffees", quantity);
        savedInstanceState.putInt("price", price);
        savedInstanceState.putString("message", priceMessage);
    }

    /**
     * Helpful for screen rotation, ensures that data is saved when screen rotates horizontally and vertically
     */
    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState) {
        super.onRestoreInstanceState(saveInstanceState);

        if (saveInstanceState != null) {
            quantity = saveInstanceState.getInt("number of coffees");
        }
        quantity = quantity + 1;
        quantity = quantity - 1;
        displayQuantity(quantity);

        EditText editName = (EditText) findViewById(R.id.edit_name);
        String textName = editName.getText().toString();

        CheckBox whippedCreamChecked = (CheckBox) findViewById(R.id.checkbox_whipped_cream);
        Boolean hasWhippedCream = whippedCreamChecked.isChecked();

        CheckBox chocolateChecked = (CheckBox) findViewById(R.id.checkbox_chocolate);
        Boolean hasChocolate = chocolateChecked.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String priceMessage = createOrderSummary(textName, price, hasWhippedCream, hasChocolate);
        displayMessage(priceMessage);


    }


    /**
     * Create summary of the order.
     *
     * @return text summary
     * @ editNae
     * @ whippedCreamChecked is whether or not the user wants whipped cream topping
     * @ hasChocolate is whether or not the user wants chocolate topping
     * @ price of the order
     */
    public void submitOrder(View view) {
        EditText editName = (EditText) findViewById(R.id.edit_name);
        String textName = editName.getText().toString();

        CheckBox whippedCreamChecked = (CheckBox) findViewById(R.id.checkbox_whipped_cream);
        Boolean hasWhippedCream = whippedCreamChecked.isChecked();

        CheckBox chocolateChecked = (CheckBox) findViewById(R.id.checkbox_chocolate);
        Boolean hasChocolate = chocolateChecked.isChecked();


        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(textName, price, hasWhippedCream, hasChocolate);

        String subject = "Here is Your Order Summary ";
        String body = priceMessage;
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject + textName);
        intent.putExtra(Intent.EXTRA_TEXT, body);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        displayMessage(priceMessage);
    }


    /**
     * Creates the order summary.
     *
     * @return hasWhippedCream.
     */

    private String createOrderSummary(String textName, int price, boolean hasWhippedCream, boolean hasChocolate) {
        String priceMessage = "Name: " + textName;
        priceMessage += "\nAdd Whipped Cream? " + hasWhippedCream;
        priceMessage += "\nAdd Chocolate? " + hasChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: €" + price + "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * Calculates the price of the order.
     *
     * @return total price.
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        // Price of 1 cup of Coffee
        int basePrice = 5;

        if (hasWhippedCream) {
            // Add $1 for whipped Cream
            basePrice = basePrice + 1;
        }
        if (hasChocolate) {
            //Add $2 for chocolate Cream
            basePrice = basePrice + 2;
        }
        return quantity * basePrice;
    }


    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        // For maximum number of coffee
        if (quantity == 100) {
            // Show an error message as a toast
            Toast.makeText(this, "Sorry, you cannot order more than 100 cups of coffee at a time", Toast.LENGTH_SHORT).show();
            //Exit this method early because there is nothing else to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        // For minimum number of coffee
        if (quantity == 1) {
            // Show an error message as a toast
            Toast.makeText(this, "Sorry, you cannot order less than 1 cup of coffee", Toast.LENGTH_SHORT).show();
            //Exit this method early because there is nothing else to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}