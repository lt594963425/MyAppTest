package com.example.administrator.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.fragment.ContactFragment;
import com.example.administrator.fragment.SessionFragment;
import com.example.administrator.fragmenttabhost.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
public class QQChat2Activity extends FragmentActivity {
	@BindView(R.id.viewpager)
	ViewPager viewpager;
	@BindView(R.id.title)
	TextView title;
	@BindView(R.id.session)
	TextView session;
	@BindView(R.id.contact)
	TextView contact;

	private View.OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.session:
				viewpager.setCurrentItem(0);
				break;
			case R.id.contact:
				viewpager.setCurrentItem(1);
				break;
			}

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qqchat);
		ButterKnife.bind(this);

		title.setText("�Ự");
		session.setEnabled(false);
		session.setOnClickListener(listener);
		contact.setOnClickListener(listener);

		final List<Fragment> pages = new ArrayList<Fragment>();
		pages.add(new SessionFragment());
		pages.add(new ContactFragment());
		FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			// ����ҳ������
			@Override
			public int getCount() {
				return pages.size();
			}
			@Override
			public Fragment getItem(int position) {
				return pages.get(position);
			}
		};

		viewpager.setAdapter(adapter);
		viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int index) {

				// index
				if (index == 0) {
					title.setText("�Ự");
					session.setEnabled(false);
					contact.setEnabled(true);
				} else {
					title.setText("����");
					session.setEnabled(true);
					contact.setEnabled(false);// ����
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}
}
