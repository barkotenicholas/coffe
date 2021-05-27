package co.coffeetype.android.coffemenu;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.coderade.android.coffeemenusample.R;

import co.coffeetype.android.coffemenu.model.DataItem;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Locale;

@SuppressWarnings("FieldCanBeLocal")
public class DetailActivity extends AppCompatActivity {

    private TextView tvName, tvDescription, tvPrice;
    private ImageView itemImage;
    private BillingProcessor billingProcessor;
    private Button button;

    public DetailActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        DataItem item = getIntent().getExtras().getParcelable(DataItemAdapter.ITEM_KEY);
        if (item == null) {
            throw new AssertionError("Null data item received!");
        }

        tvName = (TextView) findViewById(R.id.tvItemName);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        itemImage = (ImageView) findViewById(R.id.itemImage);
        button = findViewById(R.id.but);
        billingProcessor = new BillingProcessor(this,"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAooyj9pslXVflb7mDkbABCRX5rcU9ur9fCablsmIR6E7i/M5E3QcrUtZF8ROtgTi1A26Srnd9NxGL5bkRTtLhIv+kvDOFONkdj2cOdqwHA7b8rSwTlzTyRxHWzgfHOmRlGaDbpg+/Qa4PjKCfLsvdO3XO+ZUuwok4z17KEjSk57ZCBwIEUaV94+x77QHvib5Q4/4kPSOnChfiNGf0a1iP3lTA26FnyEYicKtgKBzIwM+BTWUsqQnlDQVK3G24g547KhoX9bnMgypYTiD06AJKDzy69Tn53sWFyZzILYHDsjP3giVVotIu0gwyoobNbB3L+NulP5mwMZdmYjceUS7BiQIDAQAB" , null, new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(String productId, TransactionDetails details) {

            }

            @Override
            public void onPurchaseHistoryRestored() {

            }

            @Override
            public void onBillingError(int errorCode, Throwable error) {

            }

            @Override
            public void onBillingInitialized() {

            }
        });
        tvName.setText(item.getItemName());
        tvDescription.setText(item.getDescription());

        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
        tvPrice.setText(nf.format(item.getPrice()));


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                billingProcessor.subscribe(DetailActivity.this,"coffee");

            }
        });
        InputStream inputStream = null;
        try {
            String imageFile = item.getImage();
            inputStream = getAssets().open(imageFile);
            Drawable d = Drawable.createFromStream(inputStream, null);
            itemImage.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}