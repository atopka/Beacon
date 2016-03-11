package project.topka.beacon11;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ManageBeacon extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    //Adapter to display the list's data
    SimpleCursorAdapter mAdapter;

    //Load from server

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_beacon);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
       return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        //do something
    }
}
