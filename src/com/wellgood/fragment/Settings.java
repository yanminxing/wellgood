package com.wellgood.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.pc.ioc.inject.InjectBinder;
import com.android.pc.ioc.inject.InjectView;
import com.android.pc.ioc.view.listener.OnClick;
import com.android.pc.util.Handler_Inject;
import com.wellgood.activity.AboutActivity;
import com.wellgood.activity.CountCenterActivity;
import com.wellgood.activity.FeedBackActivity;
import com.wellgood.activity.R;
import com.wellgood.activity.ShangcangActivity;
import com.wellgood.activity.SystemSettingActivity;
import com.wellgood.contract.MyData;
/**
 * 设置板块
 * @author Windows 7
 *
 */
public class Settings extends BaseFragment{
	
	//注解
	@InjectView(binders = { @InjectBinder(method = "click", listeners = { OnClick.class }) })
	View setting_hangye,				//我的行业 
	setting_shoucang, 					//收藏
	setting_countcenter, 				//账户中心
	setting_systemsetting, 				//系统设置
	setting_feedback,					//反馈
	setting_about;						//关于
	@InjectView
	TextView user_name;
	
	View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {	
		
		
		 //ViewUtils.inject(this, view); //注入view和事件
	            view=inflater.inflate(R.layout.fragment_settings, null);
		
	      //注入view和事件
	            Handler_Inject.injectOrther(this, view);
	            
	            
	            getActivity().setTitle("设置");
	            String usserString =MyData.getName();
	            user_name.setText(usserString);
	            
	            return view;
	}	
	
	public void click(View v) {
		Intent intent ;
		switch (v.getId()) {
		case R.id.setting_hangye:
			break;
		case R.id.setting_shoucang:
			intent = new Intent (getActivity(),ShangcangActivity.class);
			startActivity(intent);		
			break;
		case R.id.setting_countcenter:	
			 intent = new Intent (getActivity(),CountCenterActivity.class);
			startActivity(intent);			
			break;
		case R.id.setting_systemsetting:	
			 intent = new Intent (getActivity(),SystemSettingActivity.class);
			startActivity(intent);		
			break;
		case R.id.setting_feedback:					
			 intent = new Intent (getActivity(),FeedBackActivity.class);
			startActivity(intent);		
			break;
		case R.id.setting_about:					
			intent = new Intent (getActivity(),AboutActivity.class);
			startActivity(intent);		
			break;
			}
		}
	
}