package com.integration.popdeemtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.popdeem.sdk.uikit.activity.PDUIInboxActivity;
import com.popdeem.sdk.uikit.adapter.PDUIHomeFlowPagerAdapter;
import com.popdeem.sdk.uikit.utils.PDUIColorUtils;


/**
 * Created by innerglowitsolutions on 27/04/16.
 */
public class SecondActivity extends Fragment {

    public SecondActivity() {
    }

    public static SecondActivity newInstance() {
        return new SecondActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(com.popdeem.sdk.R.layout.fragment_pd_home_flow, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(com.popdeem.sdk.R.id.pd_home_inbox_fab);
        fab.setImageDrawable(PDUIColorUtils.getInboxButtonIcon(getActivity()));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PDUIInboxActivity.class));
            }
        });

        ViewPager viewPager = (ViewPager) view.findViewById(com.popdeem.sdk.R.id.pd_home_view_pager);
        viewPager.setAdapter(new PDUIHomeFlowPagerAdapter(getChildFragmentManager(), getActivity()));

        TabLayout tabLayout = (TabLayout) view.findViewById(com.popdeem.sdk.R.id.pd_home_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}


