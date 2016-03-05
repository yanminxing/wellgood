package com.wellgood.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.pc.ioc.inject.InjectBinder;
import com.android.pc.ioc.inject.InjectInit;
import com.android.pc.ioc.inject.InjectView;
import com.android.pc.ioc.view.listener.OnClick;
import com.android.pc.util.Handler_Inject;
import com.wellgood.activity.ChongzhiActivity;
import com.wellgood.activity.R;
import com.wellgood.activity.TaocanActivity;

public class CountCenterFragment extends Fragment {
	public static String CLASS_NAME="CountCenterFragment";
	//ע��
	@InjectView(binders = { @InjectBinder(method = "click", listeners = { OnClick.class }) })
	View counter_taocanxuangou,				//�ײ�ѡ��
	counter_zhanghuchongzhi, 					//�˻���ֵ
	counter_chongzhijilu; 				//��ֵ��¼
	
	View rootView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//this.inflater = inflater;

		rootView = inflater.inflate(R.layout.fragment_count_center, container, false);
		Handler_Inject.injectOrther(this, rootView);
		return rootView;
	}

	@InjectInit
	private void init(){
		
	}
	
	public void click(View v) {
		Intent intent ;
		switch (v.getId()) {
		case R.id.counter_taocanxuangou:
			Log.d(CLASS_NAME, "������ײ�ѡ��");
			intent = new Intent (getActivity(),TaocanActivity.class);
			startActivity(intent);		
			break;
		case R.id.counter_zhanghuchongzhi:
			Log.d(CLASS_NAME, "������˻���ֵ");
			intent = new Intent (getActivity(),ChongzhiActivity.class);
			startActivity(intent);		
			break;
		case R.id.counter_chongzhijilu:	
			Log.d(CLASS_NAME, "����˳�ֵ��¼");
			// intent = new Intent (getActivity(),CountCenterActivity.class);
			//startActivity(intent);			
			break;

			}
		}
	
	
}
