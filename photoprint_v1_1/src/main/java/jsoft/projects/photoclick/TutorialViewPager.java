package jsoft.projects.photoclick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import com.viewpagerindicator.CirclePageIndicator;
import jsoft.projects.photoclick.library.ViewPagerAdapter;
import jsoft.projects.photoclick.libs.SessionMngr;
import util.DpsControlApplication;

public class TutorialViewPager extends Activity {

    // Declare Variables
    SessionMngr session;
    Button btnSkip;
    ViewPager viewPager;
    PagerAdapter adapter;
    int[] image;
//    UnderlinePageIndicator mIndicator;
    CirclePageIndicator  mIndicator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Get the view from viewpager_main.xml
        setContentView(R.layout.viewpager_main);

        session = new SessionMngr(getApplicationContext());
        DpsControlApplication.exception_handler(this);

        btnSkip =(Button)findViewById(R.id.btnSkip);
        btnSkip.setVisibility(View.INVISIBLE);

        btnSkip.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                session.setIntroValues(1);

                Intent myintent8 = new Intent(TutorialViewPager.this,Login.class);
                startActivity(myintent8);
                finish();

            }
        });

        // Generate sample data
        image = new int[] { R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d };

        // Locate the ViewPager in viewpager_main.xml
        viewPager = (ViewPager) findViewById(R.id.pager);
        // Pass results to ViewPagerAdapter Class
        adapter = new ViewPagerAdapter(TutorialViewPager.this, image);
        // Binds the Adapter to the ViewPager
        viewPager.setAdapter(adapter);

        // ViewPager Indicator
        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
//        mIndicator.setFades(false);
        mIndicator.setViewPager(viewPager);

        mIndicator.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {

                final int action = event.getAction();

                switch(action) {

                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.e("------","1");
                        return true;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.e("------","2");
                        return true;
                    case DragEvent.ACTION_DRAG_LOCATION:
                        Log.e("------","3");
                        return true;
                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.e("------","4");
                        return true;
                    case DragEvent.ACTION_DROP:
                        Log.e("------","5");
                        return true;
                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.e("------","6");
                        return true;
                }

                        return false;
            }
        });


        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {



                System.out.print("----------------1");
                if(i==3){
                    btnSkip.setVisibility(View.VISIBLE);
                }    else{
                    btnSkip.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageSelected(int i) {

                System.out.print("------------------2");
            }

            @Override
            public void onPageScrollStateChanged(int i) {

                System.out.print("--------------3");
            }
        });

//        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int i, float v, int i2) {
//
//                if(i==3){
//                    btnSkip.setVisibility(View.VISIBLE);
//                }    else{
//                    btnSkip.setVisibility(View.INVISIBLE);
//                }
//            }
//
//            @Override
//            public void onPageSelected(int i) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int i) {
//            }
//        });


    }
}