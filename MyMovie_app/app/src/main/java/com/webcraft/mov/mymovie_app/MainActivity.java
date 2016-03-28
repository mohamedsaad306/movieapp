package com.webcraft.mov.mymovie_app;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.webcraft.mov.mymovie_app.R.layout.gridimagview;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MoviesFragment())
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
    public class MoviesFragment extends Fragment {
        public MoviesFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            GridView fragGridview= (GridView) rootView.findViewById(R.id.gridView_movies);
            getMoviesTask task=new getMoviesTask();
            task.execute();

//            movieImageAdabter theAdabter= new movieImageAdabter(getActivity());
//            fragGridview.setAdapter(theAdabter);
            return rootView;
        }
    public class getMoviesTask extends AsyncTask<Void,Void,Void>{


        private final String LOG_TAG = getMoviesTask.class.getSimpleName();


        @Override
        protected Void doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.

            String forecastJsonStr = null;
            String sorting="popularity.desc";
            String app_id="3fa7a79dcc0fdef6cc62f712f4aff7b7";

            try {
// api.themoviedb.org/3/movie/popular?/discover/movie?/discover/movie?sort_by=popularity.desc&api_key=9221879dc97932acb5f6995acaca8d7f
                final String base_url = "http://api.themoviedb.org/3/movie/popular?/discover/movie?/discover/movie?";

                final String sorting_PARAM = "sort_by";
                final String appid_PARAM ="api_key";

                Uri readyURI=Uri.parse(base_url).buildUpon()
                        .appendQueryParameter(sorting_PARAM,sorting)
                        .appendQueryParameter(appid_PARAM,app_id).build();

                URL url= new URL(readyURI.toString());

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
                forecastJsonStr = buffer.toString();
                // here to start the json parsing



                Log.v(LOG_TAG, "moviesJsonString: " + forecastJsonStr);
            } catch (IOException e) {
                Log.e("moviesFrag", "Error ", e);
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
    }

    public class movieImageAdabter extends BaseAdapter{

        private Context adabterContext;
        public movieImageAdabter(Context c){
            adabterContext=c;
        }

        @Override
        public int getCount() {
            return 0;
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
            ImageView theImageView;
            LayoutInflater inflater= LayoutInflater.from(adabterContext);

            convertView=inflater.inflate(R.layout.gridimagview,null);
            theImageView= (ImageView) convertView.findViewById(R.id.imagetoview);

            return null;
        }
    }
}
