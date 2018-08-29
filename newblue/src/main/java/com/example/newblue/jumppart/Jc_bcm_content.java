package com.example.newblue.jumppart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.newblue.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Jc_bcm_content extends AppCompatActivity {

	//private Net MyNet = new Net(this, this);
	TextView ALT,AST,TBIL,DBIL,IBIL,TP,ALB,GLO,AG,UREA,CRE,UA,GLU,TG,CHOL,HDLC,LDLC,batch;
	int id=0;
	@BindView(R.id.follow_toolbar_title)
	TextView follow_toolbar_title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.follow_jc_bcm_content);
		ButterKnife.bind(this);
		initview();
		follow_toolbar_title.setText("生化分析");
		Intent intent1 = getIntent();
		id = intent1.getIntExtra("id",0);
		initdata();
		

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//CacheActivity.getInstance().removeActivity(this);
	}

	public void initview()
	{
		
		ALT=(TextView)findViewById(R.id.ALT);
		AST=(TextView)findViewById(R.id.AST);
		TBIL=(TextView)findViewById(R.id.TBIL);
		DBIL=(TextView)findViewById(R.id.DBIL);
		IBIL=(TextView)findViewById(R.id.IBIL);
		TP=(TextView)findViewById(R.id.TP);
		ALB=(TextView)findViewById(R.id.ALB);
		AG=(TextView)findViewById(R.id.AG);
		GLO=(TextView)findViewById(R.id.GLO);
		UREA=(TextView)findViewById(R.id.UREA);
		CRE=(TextView)findViewById(R.id.CRE);
		GLU=(TextView)findViewById(R.id.GLU);
		UA=(TextView)findViewById(R.id.UA);
		TG=(TextView)findViewById(R.id.TG);
		CHOL=(TextView)findViewById(R.id.CHOL);
		HDLC=(TextView)findViewById(R.id.HDLC);
		LDLC=(TextView)findViewById(R.id.LDLC);
		batch=(TextView)findViewById(R.id.batch);
	}
	
	public void initdata()
	{
		ALT.setText(Jc_Bcm.listmap.get(id).get("ALT")+"");
		AST.setText(Jc_Bcm.listmap.get(id).get("AST")+"");
		TBIL.setText(Jc_Bcm.listmap.get(id).get("TBIL")+"");
		DBIL.setText(Jc_Bcm.listmap.get(id).get("DBIL")+"");
		IBIL.setText(Jc_Bcm.listmap.get(id).get("IBIL")+"");
		TP.setText(Jc_Bcm.listmap.get(id).get("TP")+"");
		ALB.setText(Jc_Bcm.listmap.get(id).get("ALB")+"");
		AG.setText(Jc_Bcm.listmap.get(id).get("A/G")+"");
		GLO.setText(Jc_Bcm.listmap.get(id).get("GLO")+"");
		UREA.setText(Jc_Bcm.listmap.get(id).get("UREA")+"");
		CRE.setText(Jc_Bcm.listmap.get(id).get("CRE")+"");
		GLU.setText(Jc_Bcm.listmap.get(id).get("GLU")+"");
		UA.setText(Jc_Bcm.listmap.get(id).get("UA")+"");
		TG.setText(Jc_Bcm.listmap.get(id).get("TG")+"");
		CHOL.setText(Jc_Bcm.listmap.get(id).get("CHOL")+"");
		HDLC.setText(Jc_Bcm.listmap.get(id).get("HDL-C")+"");
		LDLC.setText(Jc_Bcm.listmap.get(id).get("LDL-C")+"");

		batch.setText("盘片批号："+Jc_Bcm.listmap.get(id).get("batch")+"-"+Jc_Bcm.listmap.get(id).get("id")+"");
		
	}

	@OnClick({R.id.follow_save,R.id.follow_re_back})
	public void onClick(View v){
		int id = v.getId();
		if(id == R.id.follow_save){
			save();
		}else if(id == R.id.follow_re_back){
			finish();
		}
	}
	/**
	 * 保存按钮
	 */
	public void save()
	{
		JSONObject json =new JSONObject();
		try {
			json.put("alt", Jc_Bcm.listmap.get(id).get("ALT")+"");
			json.put("ast", Jc_Bcm.listmap.get(id).get("AST")+"");
			json.put("tbil", Jc_Bcm.listmap.get(id).get("TBIL")+"");
			json.put("dbil", Jc_Bcm.listmap.get(id).get("DBIL")+"");
			json.put("ibil", Jc_Bcm.listmap.get(id).get("IBIL")+"");
			json.put("tp", Jc_Bcm.listmap.get(id).get("TP")+"");
			json.put("alb", Jc_Bcm.listmap.get(id).get("ALB")+"");
			json.put("ag", Jc_Bcm.listmap.get(id).get("A/G")+"");
			json.put("glo", Jc_Bcm.listmap.get(id).get("GLO")+"");
			json.put("urea", Jc_Bcm.listmap.get(id).get("UREA")+"");
			json.put("cre", Jc_Bcm.listmap.get(id).get("CRE")+"");
			json.put("glu", Jc_Bcm.listmap.get(id).get("GLU")+"");
			json.put("ua", Jc_Bcm.listmap.get(id).get("UA")+"");
			json.put("tg", Jc_Bcm.listmap.get(id).get("TG")+"");
			json.put("chol", Jc_Bcm.listmap.get(id).get("CHOL")+"");
			json.put("hdlc", Jc_Bcm.listmap.get(id).get("HDL-C")+"");
			json.put("ldlc", Jc_Bcm.listmap.get(id).get("LDL-C")+"");
			json.put("edittime", Jc_Bcm.listmap.get(id).get("time")+"");
			json.put("userid", "2222321");
			json.put("duid", "22332134");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jsontr="type=bcm&data="+json;
		//MyNet.Post(Net.SERVLET_EXAM_ADD, "bcm", jsontr, 0);
	}

}
