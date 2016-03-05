package com.wellgood.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnClickListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.pc.ioc.inject.InjectInit;
import com.android.pc.ioc.inject.InjectLayer;
import com.hikvision.vmsnetsdk.CameraInfo;
import com.hikvision.vmsnetsdk.ControlUnitInfo;
import com.hikvision.vmsnetsdk.RegionInfo;
import com.wellgood.adapter.GridItem;
import com.wellgood.application.APP;
import com.wellgood.camera.CameraUtils;
import com.wellgood.camera.Constants;
import com.wellgood.camera.MsgIds;
import com.wellgood.contract.Contract;
/**
 * ��ʼ����
 * @author ZhaoJizhuang
 *
 */
@InjectLayer(R.layout.appstart)
public class APPStart extends Activity {
	public static String CLASS_NAME="APPStart";
	private int[] images=new int[]{R.drawable.c1,R.drawable.c2,R.drawable.c3,R.drawable.c4,R.drawable.c5};
	ProgressBar pb;
	public static final int prom=0x33;
    //���ù������ɼ�  
    public int pro;
    public TextView textView;

	

    public static final String SAVED_DATA_KEY = "SAVED_DATA";
    private static final int FETCH_DATA_TASK_DURATION = 500;

    /** ������Ϣ�Ķ��� */
    private MsgHandler          handler       = new MsgHandler();
    /**  ���������б�**/
    List<ControlUnitInfo> ctrlUnitList = new ArrayList<ControlUnitInfo>();
    /**����ͷ�б�**/
    List<CameraInfo> cameraList = new ArrayList<CameraInfo>();
    List<CameraInfo> cameraList1 = new ArrayList<CameraInfo>();
    List<CameraInfo> cameraList2 = new ArrayList<CameraInfo>();
    List<CameraInfo> cameraList3 = new ArrayList<CameraInfo>();
    List<CameraInfo> cameraList4 = new ArrayList<CameraInfo>();
    List<CameraInfo> cameraList5 = new ArrayList<CameraInfo>();
    List<CameraInfo> cameraList6 = new ArrayList<CameraInfo>();
    List<CameraInfo> cameraList7 = new ArrayList<CameraInfo>();
    /**�����б�**/
    List<RegionInfo> regionList=new ArrayList<RegionInfo>();
    /**���������б�**/
    List<RegionInfo> subregionList=new ArrayList<RegionInfo>();
    public static String  servAddr = "http://112.12.17.3";
    public static String userName="dbwl" ;
    public static String password="12345" ;
    /**��������ͷ��·��**/
    //private String path="0/0/0/1";
    
    //private BroadcastReceiver netReceiver=new MyReceiver();
   /* String userName="test" ;
    String password="12345" ;*/
	View view;

	@InjectInit
	private void init(){
		Log.d(CLASS_NAME, "init()");
/*		Intent intent = new Intent (APPStart.this,LoginActivity.class);
    	//pb.setProgress(100);
		//Intent intent = new Intent (APPStart.this,ListActivity.class);
		//Intent intent = new Intent (APPStart.this,WallView.class);		
    	APPStart.this.finish();
		startActivity(intent);*/		
		
	}
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.appstart);
		  //ViewUtils.inject(this);
		APP.getIns().addActivity(this);
		pb = (ProgressBar)findViewById(R.id.progressBar);  
		
		
		
		//ע����������receiver
		//registerDateTransReceiver();
		
		
		
		//textView=(TextView) findViewById(R.id.probartext);
		   setProgressBarIndeterminateVisibility(true);  
		   
			if (isServiceRunning(APPStart.this,"com.wellgood.service.BackGroundService")) {
				
				
				APPStart.this.finish();
			}else {
				loadThread();
			}
			
		
		

	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
			
	}


    /**
     * ��������״̬��receiver ��action
     */
	public static final String CONNECTIVITY_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    /**
     * ע����������receiver
     */
/*	private void registerDateTransReceiver() {
			Log.d(CLASS_NAME, "register receiver " + CONNECTIVITY_CHANGE_ACTION);
			IntentFilter filter = new IntentFilter();
			filter.addAction(CONNECTIVITY_CHANGE_ACTION);
			filter.setPriority(1000);
			registerReceiver(netReceiver, filter);
	}*/
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		//unregisterReceiver(netReceiver);
		handler=null;
		super.onDestroy();
	}
	/**
	 * ��������״̬��receiver������״̬һ�����仯�ͻ����
	 * @author Administrator
	 *
	 */
	private class MyReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			 String action = intent.getAction();
			    Log.d(CLASS_NAME, "PfDataTransReceiver receive action " + action);
			    if(TextUtils.equals(action, CONNECTIVITY_CHANGE_ACTION)){//����仯��ʱ��ᷢ��֪ͨ
			        Log.d(CLASS_NAME, "����仯��");
			        //������
			        if (getActiveNetwork(APPStart.this)==null) {
			        	//��������
			        	//��ת����������
			        	Toast.makeText(APPStart.this, "�Ѿ�����", Toast.LENGTH_LONG).show();
			        	dialog();
			        	
						//Intent wifiSettingsIntent = new Intent("android.settings.WIFI_SETTINGS");   
						//   startActivity(wifiSettingsIntent);   
					}
			        //��������
			        	else {
			        		//��������
			        		//getCameraListThread();
			        		Toast.makeText(APPStart.this, "�Ѿ�������", Toast.LENGTH_LONG).show();
			        		
					}
			        return;
			    }
		}
		
	}
    /**
     * ����������ص�aActiveInfo�����ж���������ޣ�������ص���null��
     * ��ʱ���Ƕ����ˣ�������ض���Ϊ�գ���������������
     * �ڷ��ص�NetworkInfo����������ж���ķ�����ȡ����ĵ�ǰ������Ϣ��������wifi����cmwap�ȣ��Ͳ���˵�ˡ�
     * @param context
     * @return
     */
	public static NetworkInfo getActiveNetwork(Context context){
	    if (context == null)
	        return null;
	    ConnectivityManager mConnMgr = (ConnectivityManager) context
	            .getSystemService(Context.CONNECTIVITY_SERVICE);
	    if (mConnMgr == null)
	        return null;
	    NetworkInfo aActiveInfo = mConnMgr.getActiveNetworkInfo(); // ��ȡ�����������Ϣ
	    return aActiveInfo;
	}
	
	
	
	

	/** handler  ��������ֻҪ
	 * ��run������------����Message,
	 * ��Handler��handleMessage----����Message��
	 * ͨ����ͬ��Messageִ�в�ͬ������  **/
	
	
    @SuppressLint("HandlerLeak")				//����lint����
    private final class MsgHandler extends Handler {

   

		@SuppressWarnings("unchecked")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                	
             /*   case MsgIds.GET_CAMERA_FAIL:
                	Log.d(CLASS_NAME, "��ȡ����ͷ�б�ʧ��");
                	break;*/
             /*   case prom:
                	pb.setProgress((Integer)msg.obj);
                	//textView.setText((Integer)msg.obj+"%");
                	break;*/
                case Contract.LOAD_COMPLATED:
                	//fillListData();
                	Intent intent = new Intent (APPStart.this,LoginActivity.class);
                	//Intent intent = new Intent (APPStart.this,MainActivity.class);
                	//pb.setProgress(100);
        			//Intent intent = new Intent (APPStart.this,ListActivity.class);
        			//Intent intent = new Intent (APPStart.this,WallView.class);		
                	APPStart.this.finish();
        			startActivity(intent);			
        			
                	break;
                	
                default:
                break;
            }

        }
    }
	
       
    protected void loadThread() {
 			Log.d(CLASS_NAME, "getCameraList");
 			  new Thread() {
 	                public void run() {

 	                	try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							handler.sendEmptyMessage(Contract.LOAD_COMPLATED);
						}
 	                
 	                	handler.sendEmptyMessage(Contract.LOAD_COMPLATED);
 			         };
 	         }.start();
 		}
	
	
	
	
        /**�ж�ĳ��service�Ƿ��Ѿ�������
         * @author zhaojizhuang
         * @param mContext 
         * @param className ������manifest��һ��
         * @return  <code>true</code> or <code>false</code>
         */
        public static boolean isServiceRunning(Context mContext,String className) {  
            boolean isRunning = false;  
            ActivityManager activityManager = (ActivityManager)  
            mContext.getSystemService(Context.ACTIVITY_SERVICE);   
            List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(200);  
            Log.d(CLASS_NAME, "serviceList"+serviceList);
            if (!(serviceList.size()>0)) {  
                return false;  
            }  
      
            for (int i=0; i<serviceList.size(); i++) {  
            	  Log.d(CLASS_NAME, "serviceList"+serviceList.get(i).service.getClassName());
                if (serviceList.get(i).service.getClassName().equals(className) == true) {  
                    isRunning = true;  
                    break;  
                }  
            }  
            return isRunning;  
    }  
	
    	@Override
		@Deprecated
		protected Dialog onCreateDialog(int id) {
			// TODO Auto-generated method stub
			return super.onCreateDialog(id);
			
		}




		/** ȷ��dialog select 0Ϊ���ţ�1λ����**/
    	private void dialog() {
    		AlertDialog.Builder builder = new Builder(APPStart.this);
    		builder.setMessage("���ڶ���״̬��������������");
    		builder.setTitle("��ʾ");
    		builder.setPositiveButton("ȷ��", new OnClickListener() {
    						@Override
    						public void onClick(DialogInterface dialog, int which) {
    							   dialog.dismiss();
    						//��ת����������
    						Intent wifiSettingsIntent = new Intent("android.settings.WIFI_SETTINGS");   
    						   startActivity(wifiSettingsIntent);   
    						APPStart.this.finish();
    						}
    		});
    		builder.setNegativeButton("ȡ��", new OnClickListener() {
    						 @Override
    				 public void onClick(DialogInterface dialog, int which) {
    						dialog.dismiss();
    						APPStart.this.finish();
    						 }
    		});
    		
    		builder.create().show();
    	}





	
	
	
	
	
}
