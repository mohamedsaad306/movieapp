package com.webcraft.android.mov_app;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.Inflater;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new movieFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {




            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public class movieFragment extends Fragment {

        public movieFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            //the grid data
            GridView movies_grid= (GridView)rootView.findViewById(R.id.gridView_movies);
            String [] str={"sss","sss"};
            imageadabter myadabter= new imageadabter(getActivity(),str);
            movies_grid.setAdapter(myadabter);

            getmovies task= new getmovies();
            task.execute();
            return rootView;
        }
    }

   public  class imageadabter extends BaseAdapter{

       private Context mContext;
       String []imgsPath;

       Inflater inflater;

        public imageadabter (Context c,String[] imgs){
            mContext =c;
            imgsPath=imgs;
        }

        @Override
        public int getCount() {
            return imgsPath.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ImageView imageView ;

            LayoutInflater inflater= LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.imagelayout,null);
            imageView= (ImageView) convertView.findViewById(R.id.imageViewtoShow);


          //  imageView.setImageResource(imgsPath[position]);
            return convertView;
        }

    }
}

class getmovies extends AsyncTask<Void,Void,Void>{
    private final String LOG_TAG = getmovies.class.getSimpleName();

    @Override
    protected Void doInBackground(Void... params) {

//        http://api.themoviedb.org/3/movie/popular?/discover/movie?sort_by=popularity.desc&api_key=9221879dc97932acb5f6995acaca8d7f
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        // Will contain the raw JSON response as a string.
        String moviesJsonStr = null;
        String sorting="popularity.desc";
        String app_id="9221879dc97932acb5f6995acaca8d7f";
        try {

            final String base_url = "http://api.themoviedb.org/3/movie/popular?/discover/movie?/discover/movie?sort_by=popularity.desc";


            final String sort_PARAM = "sort_by";
            final String appid_PARAM ="api_key";

            Uri readyURI=Uri.parse(base_url).buildUpon()
                    .appendQueryParameter(sort_PARAM, sorting)
                    .appendQueryParameter(appid_PARAM,app_id).build();


            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            //URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7&appid=3fa7a79dcc0fdef6cc62f712f4aff7b7");
            Log.v(LOG_TAG,"movJsonString: "+readyURI.toString());
            URL url= new URL(readyURI.toString());
         //   URL url=new URL("api.themoviedb.org/3/movie/popular?/discover/movie?sort_by=popularity.desc&api_key=9221879dc97932acb5f6995acaca8d7f");
            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            moviesJsonStr= buffer.toString();
            // here to start the json parsing



            Log.v(LOG_TAG,"movJsonString: "+moviesJsonStr);
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
        return null;
    }

}

