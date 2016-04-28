package jsoft.projects.photoclick;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.ToxicBakery.viewpager.transforms.StackTransformer;
import jsoft.projects.photoclick.libs.SessionMngr;
import util.DpsControlApplication;

import java.util.ArrayList;


public class TutorialActivity extends Activity {

    SessionMngr  session;
    Button btnSkip;

    private int[] GalImages = new int[] {
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d
    };

    TextView text;

    ArrayList<Img> list = new ArrayList<Img>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tutorial);

        session = new SessionMngr(getApplicationContext());
        DpsControlApplication.exception_handler(this);

        btnSkip =(Button)findViewById(R.id.btnSkip);
        btnSkip.setVisibility(View.INVISIBLE);


        text =(TextView) findViewById(R.id.pagenumber);



        btnSkip.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                session.setIntroValues(1);

                Intent myintent8 = new Intent(TutorialActivity.this,Login.class);
                startActivity(myintent8);
                finish();

            }
        });

        list.clear();
        for(int i=0;i<GalImages.length;i++){
            list.add(new Img(GalImages[i]))   ;
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        ImageAdapter adapter = new ImageAdapter(this,list);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new StackTransformer());

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

                if(i==0){
                    text.setVisibility(View.VISIBLE);
                    text.setText("1");
                }   else if(i==1){
                    text.setVisibility(View.VISIBLE);
                    text.setText("2");
                }   else if(i==2){
                    text.setVisibility(View.VISIBLE);
                    text.setText("3");
                }

                if(i==3){
                    text.setVisibility(View.VISIBLE);
                    text.setText("4");
                    btnSkip.setVisibility(View.VISIBLE);
                }    else{
                    btnSkip.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

    }


    public class ImageAdapter extends PagerAdapter {
        Context context;

       ArrayList<Img>  imglist;

        ImageAdapter(Context context, ArrayList<Img> galaryimg)
        {

            this.context = context;
            this.imglist = galaryimg;
        }
        @Override
        public int getCount() {
            return imglist.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view == ((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int i) {


            Img pojo = imglist.get(i);

            ImageView imageView = new ImageView(TutorialActivity.this);
//            int padding = context.getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
            imageView.setPadding(0, 0, 0, 0);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(pojo.getImgid());
            ((ViewPager) container).addView(imageView, 0);


//
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((ImageView) object);
        }
    }

    public class Img{

        int imgid;

        Img(int id){
            this.imgid=id;
        }


        public void setImgid(int id){
            this.imgid=id;
        }

        public int getImgid(){
            return imgid;
        }


    }


}