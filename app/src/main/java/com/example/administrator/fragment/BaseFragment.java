package com.example.administrator.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
	private View view;
	// һ��Viewֻ�ܱ� ��Ӳ�������ViewPagerһ��
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = createView(inflater, container, savedInstanceState);
		}
//		else {
//			// has parent
//			ViewGroup parent = (ViewGroup) view.getParent();
//			if (parent != null) {
//				parent.removeView(view);
//			}
//		}
		return view;

	}

	protected abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
}
