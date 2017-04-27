package mainpackage.jivetest.provider;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;

import java.util.List;

import mainpackage.jivetest.model.SearchResults;

public class SearchSuggestionsProvider extends ContentProvider {

    List<SearchResults> searchSuggestions;

    @Override
    public boolean onCreate() {
        return false;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        searchSuggestions = SearchResults.listAll(SearchResults.class);

        MatrixCursor matrixCursor = new MatrixCursor(
                new String[]{
                        BaseColumns._ID,
                        SearchManager.SUGGEST_COLUMN_TEXT_1,
                        SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID
                }
        );
        if (searchSuggestions != null) {
            String query = uri.getLastPathSegment().toString();
            int limit = Integer.parseInt(uri.getQueryParameter(
                    SearchManager.SUGGEST_PARAMETER_LIMIT));

            int length = searchSuggestions.size();

            for (int i = 0; i < length && matrixCursor.getCount() < limit; i++) {
                String searchResult = searchSuggestions.get(i).getSearchResult();
                if (searchResult.contains(query)) {
                    matrixCursor.addRow(new Object[]{i, searchResult, i});
                }
            }
        }
        return matrixCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}

