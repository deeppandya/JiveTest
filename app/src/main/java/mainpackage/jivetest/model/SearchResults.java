package mainpackage.jivetest.model;

import com.orm.SugarRecord;


public class SearchResults extends SugarRecord {

    private String searchResult;

    public SearchResults() {
    }

    public SearchResults(String searchResult) {
        this.searchResult = searchResult;
    }

    public String getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(String searchResult) {
        this.searchResult = searchResult;
    }
}
