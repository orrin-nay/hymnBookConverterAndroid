package com.example.naylo.hymnbookconverter;

import java.util.Comparator;

/**
 * Created by Naylo on 12/17/2016.
 */

public class SearchItemComp implements Comparator<SearchItem> {


    @Override
    public int compare(SearchItem o1, SearchItem o2) {
        return o2.getQuantity() - o1.getQuantity();
    }
}
