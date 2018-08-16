package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;


import java.util.List;
//starter code by Udacity
public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getName();
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private  TextView getTvDescription, getTvOrigin, getTvAsKnownAs , getTvIngredient;
    private ImageView ingredientsIv;

    Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        getTvDescription = findViewById(R.id.description_tv);
        getTvOrigin = findViewById(R.id.origin_tv);
        getTvAsKnownAs = findViewById(R.id.also_known_tv);
        getTvIngredient = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        assert intent != null;
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        try{

        sandwich = JsonUtils.parseSandwichJson(json);
            populateUI();
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .placeholder(R.mipmap.ic_launcher) //launch the icon before you get data and place the icon if we have no image.
                    .into(ingredientsIv);

            setTitle(sandwich.getMainName());
        }catch (Exception e){
            Log.e(TAG,"Error",e);

        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * COMPLETED (8) DetailActivity shows all Sandwich details correctly
     * COMPLETED (11) populate the UI by setting text to the correct textview using Sandwich class
     * COMPLETED (5) declare all the textViews and set variable, add the Imageview and assign them to their views
     *
     */
    private void populateUI() {
        getTvDescription.setText(sandwich.getDescription().equals("") ? "Data Missing" : sandwich.getDescription());
        getTvOrigin.setText(sandwich.getPlaceOfOrigin().equals("") ? "Data Missing" : sandwich.getPlaceOfOrigin());
        getList(getTvAsKnownAs,sandwich.getAlsoKnownAs());
        getList(getTvIngredient,sandwich.getIngredients());
    }

    /**
     * my getList takes in two parameters; that is the textview and the list of type String
     * @param textView
     * @param stringList
     * @precondition
     * stringList != null;
     * @postcondition
     * append textView to stringList
     */
    public void getList(TextView textView, List<String> stringList){
        // n <- stringList.size
        int n = stringList.size();
        if(n == 0)
            textView.append("Data Missing"); // if we have no data display the string
        for(int i = 0; i < n; i++){
            if(i == n - 1){
                textView.append(stringList.get(i));
            }else {
                textView.append(stringList.get(i) + "\n");
            }

        }

    }
}
