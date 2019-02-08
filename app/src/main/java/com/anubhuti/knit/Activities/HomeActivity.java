package com.anubhuti.knit.Activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.anubhuti.knit.Fragments.ContactUsFragment;
import com.anubhuti.knit.Fragments.EventsFragment;
import com.anubhuti.knit.Fragments.HomeFragment;
import com.anubhuti.knit.Fragments.SponserFragment;
import com.anubhuti.knit.Fragments.StartFragment;
import com.anubhuti.knit.Fragments.TeamFragment;
import com.anubhuti.knit.Migration.UserMigration;
import com.anubhuti.knit.R;
import com.anubhuti.knit.Utils.ApplicationContextProvider;
import com.anubhuti.knit.Utils.Config;
import com.anubhuti.knit.menu.DrawerAdapter;
import com.anubhuti.knit.menu.DrawerItem;
import com.anubhuti.knit.menu.SimpleItem;
import com.anubhuti.knit.menu.SpaceItem;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

public class HomeActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {

    private static final int POS_HOME = 0;
    private static final int POS_EVENTS = 1;
    private static final int POS_STAR_ATTRACTION = 2;
    private static final int POS_ENQUIRY = 3;
    private static final int POS_TEAM_DETAILS = 4;
    private static final int POS_SPONSER_DETAIL=5;
    private static final int POS_SHARE_APP=6;
    private static final int POS_RATE_US=7;

    private UserMigration userMigration=new UserMigration();
    private GoogleSignInClient mGoogleSignInClient;

    private TextView toolBarTitle;

    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolBarTitle=this.findViewById(R.id.toolbar_title);
        toolBarTitle.setText("Anubhuti19");

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(true)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_HOME).setChecked(true),
                createItemFor(POS_EVENTS),
                createItemFor(POS_STAR_ATTRACTION),
                createItemFor(POS_ENQUIRY),
                createItemFor(POS_TEAM_DETAILS),
                createItemFor(POS_SPONSER_DETAIL),
                new SpaceItem(10),
                createItemFor(POS_SHARE_APP),
                createItemFor(POS_RATE_US)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(true);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_HOME);

    }

    @Override
    public void onItemSelected(int position) {

//        if (position == POS_LOGOUT) {
//            finish();
//        } TODO :- handel Log out in profile secrtion

        slidingRootNav.closeMenu();
        switch (position){
            case 0:
                HomeFragment homeFragment=new HomeFragment();
                showFragment(homeFragment);
                break;
            case 1:
                EventsFragment eventsFragment=new EventsFragment();
                showFragment(eventsFragment);
                break;
            case 2:
                StartFragment startFragment=new StartFragment();
                showFragment(startFragment);
                break;
            case 3:
                ContactUsFragment fragment=new ContactUsFragment();
                showFragment(fragment);
                break;
            case 4:
                TeamFragment teamFragment=new TeamFragment();
                showFragment(teamFragment);
                break;
            case 5:
                SponserFragment sponserFragment=new SponserFragment();
                showFragment(sponserFragment);
                break;
            case 7:
                rateApp();
                break;
            case 8:
                shareApp();
                break;
        }
    }

    private void showFragment(final Fragment fragment) {


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container_home, fragment);
                        fragmentTransaction.commit();
                    }
                }, 300);

//        getFragmentManager().beginTransaction()
//                .replace(R.id.container, fragment)
//                .commit();
    }

    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withTextTint(color(R.color.cream))
                .withSelectedTextTint(color(R.color.voilet));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    public void fbClick(View view) {

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/anubhutiknit/"));
            startActivity(intent);
        } catch(Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/anubhutiknit/")));
        }
    }

    public void instaClick(View view) {

        Uri uri = Uri.parse("https://instagram.com/_u/anubhuti.knit?utm_source=ig_profile_share&igshid=1h2asc6dnqphz");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://instagram.com/anubhuti.knit?utm_source=ig_profile_share&igshid=1h2asc6dnqphz")));
        }
    }

    public void youtubeClick(View view) {

        Intent intent=null;
        try {
            intent =new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse("https://www.youtube.com/channel/UCcpPd0QLYTpzYoR6XvBtE1w"));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.youtube.com/channel/UCcpPd0QLYTpzYoR6XvBtE1w"));
            startActivity(intent);
        }
    }

    public void anubhutiClick(View view) {

        String url = "https://anubhuti19.in/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void rateApp(){


        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        try {
            Config.toastShort(ApplicationContextProvider.getContext(), "Thanks for your Intrest :D");
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        }
        catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    private void shareApp(){

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "Hey check out this cool Anubhuti2K19 App at: https://play.google.com/store/apps/details?id=com.anubhuti.knit");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().freeMemory();
    }

    public void logout(View view) {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient= GoogleSignIn.getClient(this, gso);

//        LoginManager.getInstance().logOut();
        mGoogleSignInClient.signOut();
        userMigration.setuserLogout();

        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
