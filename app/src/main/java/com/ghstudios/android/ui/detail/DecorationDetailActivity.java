package com.ghstudios.android.ui.detail;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ghstudios.android.data.database.DataManager;
import com.ghstudios.android.mhgendatabase.R;
import com.ghstudios.android.ui.adapter.DecorationDetailPagerAdapter;
import com.ghstudios.android.ui.dialog.WishlistDataAddDialogFragment;
import com.ghstudios.android.ui.general.GenericTabActivity;
import com.ghstudios.android.ui.list.adapter.MenuSection;

public class DecorationDetailActivity extends GenericTabActivity {
    /**
     * A key for passing a decoration ID as a long
     */
    public static final String EXTRA_DECORATION_ID =
            "com.daviancorp.android.android.ui.detail.decoration_id";

    private static final String DIALOG_WISHLIST_ADD = "wishlist_add";
    private static final int REQUEST_ADD = 0;

    private ViewPager viewPager;
    private DecorationDetailPagerAdapter mAdapter;

    private long id;
    private String name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = getIntent().getLongExtra(EXTRA_DECORATION_ID, -1);
        name = DataManager.get(getApplicationContext()).getDecoration(id).getName();
        setTitle(name);

        // Initialization
        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new DecorationDetailPagerAdapter(getSupportFragmentManager(), id);
        viewPager.setAdapter(mAdapter);

        mSlidingTabLayout.setViewPager(viewPager);

    }

    @Override
    protected int getSelectedSection() {
        return MenuSection.DECORATION;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        MenuInflater inflater = new MenuInflater(getApplicationContext());
//        inflater.inflate(R.menu.menu_wishlist_add, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.wishlist_add:
                FragmentManager fm = getSupportFragmentManager();
                WishlistDataAddDialogFragment dialogCopy = WishlistDataAddDialogFragment
                        .newInstance(id, name);
                dialogCopy.show(fm, DIALOG_WISHLIST_ADD);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
