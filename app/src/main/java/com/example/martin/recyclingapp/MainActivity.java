package com.example.martin.recyclingapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.martin.recyclingapp.adapters.MainActivityPagerAdapter;

import com.example.martin.recyclingapp.db.AppDatabase;
import com.example.martin.recyclingapp.db.ConstantsAndUtils;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private static final int MENU_SETTINGS_INDEX = 0;
    private static final int MENU_SIGN_OUT_INDEX = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.inflateMenu(R.menu.menu_main);

        MenuItem mainMenu = toolbar.getMenu().getItem(0);

        MenuItem settings = mainMenu.getSubMenu().getItem(MENU_SETTINGS_INDEX);
        MenuItem signOut = mainMenu.getSubMenu().getItem(MENU_SIGN_OUT_INDEX);

        settings.setOnMenuItemClickListener(menuItem -> {
            //TODO
            Toast.makeText(this, "Settings Menu", Toast.LENGTH_SHORT).show();
            return true;
        });

        signOut.setOnMenuItemClickListener(menuItem -> {
            performSignOut();
            return true;
        });

        TabLayout tabLayout = findViewById(R.id.tab_layout_main_activity);
        FloatingActionButton fab = findViewById(R.id.fab_scan_main_activity);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_overall));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_nearby));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_history));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.view_pager_main_activity);
        viewPager.setOverScrollMode(ViewPager.OVER_SCROLL_NEVER);

        Bundle bundle = new Bundle();
        bundle.putString("uid", getIntent().getStringExtra("uid"));

        final MainActivityPagerAdapter pagerAdapter =
                new MainActivityPagerAdapter(getFragmentManager(),
                        tabLayout.getTabCount(),
                        bundle);

        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CameraActivity.class);
            startActivityForResult(intent, 0);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==0) {
            if(resultCode== CommonStatusCodes.SUCCESS) {
                if(data!=null) {

                    Barcode barcode = data.getParcelableExtra("barcode");
                    Bundle bundle = new Bundle();
                    bundle.putString("barcode", barcode.displayValue);
                    FragmentManager fragmentManager = getFragmentManager();
                    ResultFragment resultFragment = new ResultFragment();
                    resultFragment.setArguments(bundle);

                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

                    transaction.replace(android.R.id.content, resultFragment)
                            .addToBackStack(null).commit();
                }
                else {
                    //TODO
                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void performSignOut() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//            AlertDialog dialog = builder.setTitle("Log Out")
//                    .setMessage("Are you sure you want to log out?")
//                    .setPositiveButton("Log Out", (dialogInterface, i) -> {
//                        //
//                    })
//                    .setNegativeButton("Cancel", (dialogInterface, i) ->
//                            dialogInterface.dismiss()).create();
//
//            dialog.setOnShowListener(dialogInterface -> {
//                dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
//                        .setTextColor(Color.BLACK);
//                dialog.getButton(DialogInterface.BUTTON_POSITIVE)
//                        .setTextColor(Color.BLACK);
//            });
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();

            new Thread(() -> {
                AppDatabase.getAppDatabase(this).itemDao().clearItems();
            });

            //dialog.show();
            ConstantsAndUtils.triggerRebirth(this);
        } else {
            Toast.makeText(this, "You are not signed in.", Toast.LENGTH_LONG).show();
        }
    }
}
