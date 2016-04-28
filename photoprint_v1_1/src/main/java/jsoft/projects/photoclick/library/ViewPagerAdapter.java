package jsoft.projects.photoclick.library;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import jsoft.projects.photoclick.R;

public class ViewPagerAdapter extends PagerAdapter {
	// Declare Variables
	Context context;
	int[] image;
	LayoutInflater inflater;

	public ViewPagerAdapter(Context context, int[] flag) {
		this.context = context;
		this.image = flag;
	}

	@Override
	public int getCount() {
		return image.length;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((RelativeLayout) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		// Declare Variables
		ImageView imgflag;

		inflater = (LayoutInflater) context .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemView = inflater.inflate(R.layout.viewpager_item, container,
				false);

		// Locate the ImageView in viewpager_item.xml
		imgflag = (ImageView) itemView.findViewById(R.id.flag);
		// Capture position and set to the ImageView
		imgflag.setImageResource(image[position]);

		// Add viewpager_item.xml to ViewPager
		((ViewPager) container).addView(itemView);

		return itemView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// Remove viewpager_item.xml from ViewPager
		((ViewPager) container).removeView((RelativeLayout) object);

	}
}
