package mainpackage.jivetest.activity;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import mainpackage.jivetest.R;
import mainpackage.jivetest.adapter.FlickerPhotosListAdapter;
import mainpackage.jivetest.iviews.IMainView;
import mainpackage.jivetest.model.FlickerPhotos;
import mainpackage.jivetest.model.SearchResults;
import mainpackage.jivetest.presenter.MainViewPresenter;
import mainpackage.jivetest.support.Constants;
import mainpackage.jivetest.support.Scopes;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;

public class MainActivity extends AppCompatActivity implements IMainView,SearchView.OnSuggestionListener, SearchView.OnQueryTextListener {

    private RecyclerView flickerPhotoslistRecyclerView;
    private FlickerPhotosListAdapter flickerPhotosListAdapter;
    private MainViewPresenter mainViewPresenter;

    private ProgressDialog mProgressDialog;

    private SearchView searchView;
    private TextView txtEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewPresenter = (MainViewPresenter) getSystemService(Scopes.MAINVIEW);
        mainViewPresenter.takeView(this);

        setViews();

        flickerPhotosListAdapter = new FlickerPhotosListAdapter(this,this);

        //mainViewPresenter.getRecentFlickerPhotos();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);

        setUpSearchMenu(menu);

        return true;
    }

    private void setUpSearchMenu(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        final SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnSuggestionListener(this);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint(getResources().getString(R.string.search_photos));
    }

    private void setViews() {
        flickerPhotoslistRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        txtEmpty=(TextView)findViewById(R.id.txtEmpty);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this);
        flickerPhotoslistRecyclerView.setLayoutManager(mLayoutManager);
        flickerPhotoslistRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainViewPresenter.dropView(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    @Override
    public boolean onSuggestionSelect(int position) {
        return true;
    }

    @Override
    public boolean onSuggestionClick(int position) {

        Cursor searchCursor = searchView.getSuggestionsAdapter().getCursor();
        if(searchCursor.moveToPosition(position)) {
            String selectedItem = searchCursor.getString(1);
            searchView.setQuery(selectedItem,true);
        }

        return true;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setFlickerPhotoListAdapter(List<FlickerPhotos> flickerPhotosList) {
        if(flickerPhotosListAdapter !=null){
            flickerPhotosListAdapter.setFlickerPhotosList(flickerPhotosList);
            flickerPhotoslistRecyclerView.setAdapter(flickerPhotosListAdapter);
        }
    }

    @Override
    public void showProgressDialog(String progressDialogTitle) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setIndeterminate(true);
            SpannableString ss2=  new SpannableString(progressDialogTitle);
            ss2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 0, ss2.length(), 0);
            mProgressDialog.setMessage(ss2);
        }

        mProgressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void openPhotoFullView(FlickerPhotos flickerPhotos) {
        Intent intent=new Intent(MainActivity.this,FlickerPhotoViewActivity.class);
        intent.putExtra(Constants.PHOTO, flickerPhotos);
        startActivity(intent);
    }

    @Override
    public void updateEmptySearch(int visibility) {
        txtEmpty.setVisibility(View.GONE);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        long count = SearchResults.count(SearchResults.class, "search_result = ?", new String[]{query});
        if(count<=0){
            SearchResults searchResults=new SearchResults(query);
            searchResults.save();
        }

        mainViewPresenter.getFlickerPhotos(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public Object getSystemService(@NonNull String name) {

        MortarScope activityScope = MortarScope.findChild(getApplicationContext(), Scopes.MAINVIEW);

        if (activityScope == null) {
            activityScope = MortarScope.buildChild(getApplicationContext()) //
                    .withService(BundleServiceRunner.SERVICE_NAME, new BundleServiceRunner())
                    .withService(Scopes.MAINVIEW, new MainViewPresenter())
                    .build(Scopes.MAINVIEW);
        }

        return activityScope.hasService(name) ? activityScope.getService(name)
                : super.getSystemService(name);
    }
}
