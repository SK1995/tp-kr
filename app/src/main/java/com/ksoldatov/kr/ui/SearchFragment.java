package com.ksoldatov.kr.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import com.ksoldatov.kr.R;
import com.ksoldatov.kr.adapters.CounterPartyAdapter;
import com.ksoldatov.kr.model.suggestions.Suggestion;
import com.ksoldatov.kr.utils.SuggestionsTextWatcher;

import java.util.ArrayList;

public class SearchFragment extends android.support.v4.app.Fragment {

    private AutoCompleteTextView autoCompleteTextView;
    private CounterPartyAdapter adapter;
    private ArrayList<Suggestion> suggests = new ArrayList<>();

    public static SearchFragment newInstance() {

        Bundle args = new Bundle();

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        autoCompleteTextView = getView().findViewById(R.id.user_input_autocomplete);
        adapter = new CounterPartyAdapter(getView().getContext(), android.R.layout.select_dialog_item, suggests);

        autoCompleteTextView.setAdapter(adapter);
        SuggestionsTextWatcher textWatcher = new SuggestionsTextWatcher(adapter, autoCompleteTextView);
        autoCompleteTextView.addTextChangedListener(textWatcher);
    }
}
