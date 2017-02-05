package com.tjx.dimchoi.searchpage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.SearchView;

import com.tjx.dimchoi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by y_kch on 5/2/2017.
 */

public class SearchPreview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_preview);

        SearchView searchView = (SearchView) findViewById(R.id.search_view);
        ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(pager);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        //adapter.addFragment(new ListContentFragment(), "List");
        //adapter.addFragment(new TileContentFragment(), "Tile");
        adapter.addFragment(new CardContentFragment(), "All");

        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
