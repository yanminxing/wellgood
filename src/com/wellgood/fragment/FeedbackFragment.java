package com.wellgood.fragment;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.pc.ioc.inject.InjectBinder;
import com.android.pc.ioc.inject.InjectView;
import com.android.pc.ioc.view.listener.OnClick;
import com.android.pc.util.Handler_Inject;
import com.wellgood.activity.R;
import com.wellgood.contract.Contract;
import com.wellgood.contract.MyData;
import com.wellgood.service.SimpleClient;

/**
 * ������Ϣ
 * 
 * @author zhaojizhuang
 * 
 */
public class FeedbackFragment extends Fragment {
	
	public static String CLASS_NAME = "FeedbackFragment";
	
	EditText et_sendmessage;  //editview  ����ע�⣬ע�����edtitext��������
	@InjectView(binders = { @InjectBinder(method = "OnClick", listeners = { OnClick.class }) })
	Button btn_feedback_submit; // �ύ��ť
	
	View rootView;
	Handler handler=new MyHandler();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_feedback, container,false);
		Handler_Inject.injectOrther(this, rootView);
		
		et_sendmessage = (EditText) rootView.findViewById(R.id.et_sendmessage);

		return rootView;
	}
	
    @SuppressLint("HandlerLeak")				//����lint����
    private final  class MyHandler extends Handler{
    	  @SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			
			/**��½�ɹ�**/
			case Contract.FEEDBACK_SUCCESS:
				showtoast((String) msg.obj);
				et_sendmessage.setText("");
			default:
				break;
			}
			
		}
    	
    }
	
	
    public void OnClick(View view){
    	switch (view.getId()) {
    	
		case R.id.btn_feedback_submit:							//����
			Log.d(CLASS_NAME, "����˷���");
			
			sendMessageThread();
			break;
		default:
			break;
		}
    }

	public JSONObject sendMessage(String content) {
		JSONObject ss = null;
		


		try {
			JSONObject comment=new JSONObject();
			comment.put("usr_id", MyData.getUserID());
			comment.put("title", "�û�����");
		
			comment.put("content", content);
			String URL = Contract.CONNECT_HOST + "/comment/add?comment="+comment;
			Log.d(CLASS_NAME, "sendmessageURL:"+URL);
			ss = SimpleClient.doGet(URL, null);
			Log.d(CLASS_NAME, "senmessage" + ss);

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return ss;
	}

	public void sendMessageThread() {

		new Thread() {
			public void run() {

				try {
					String comment=et_sendmessage.getText().toString();
					
					if (comment.length()==0) {
						Message msg= new Message();
						msg.what=Contract.FEEDBACK_SUCCESS;
						msg.obj="��������������!";
						handler.sendMessage(msg);
						return;
					}
					JSONObject jsonObject = sendMessage(comment);
					if (jsonObject!=null) {
						Message msg= new Message();
						msg.what=Contract.FEEDBACK_SUCCESS;
						msg.obj="�����ύ�ɹ�����л����֧��";
						handler.sendMessage(msg);
						
					}
					Log.d(CLASS_NAME, "sendMessage:jsonObject" + jsonObject);
					// bufang();
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			};
		}.start();

	}
	public void showtoast(String message){
		Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
	}
}
