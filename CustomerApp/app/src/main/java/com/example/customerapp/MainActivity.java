package com.example.customerapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.customerapp.Fragment.ActivityFragment;
import com.example.customerapp.Fragment.HomeFragment;
import com.example.customerapp.Fragment.MessageFragment;
import com.example.customerapp.Fragment.NoticeFragment;
import com.example.customerapp.Fragment.SettingFragment;

public class MainActivity extends AppCompatActivity {

    LinearLayout layoutHome, layoutActivity, layoutMessage, layoutNotice, layoutSetting;
    ImageView imgHome, imgActivity, imgMessage, imgNotice, imgSetting;
    TextView textHome, textActivity, textMessage, textNotice, textSetting;
    private int selectedTab = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        getSupportFragmentManager().beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.fragment_container, HomeFragment.class, null)
            .commit();

        layoutHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTab != 1)
                {
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_container, HomeFragment.class, null)
                            .commit();

                    textActivity.setVisibility(View.GONE);
                    textMessage.setVisibility(View.GONE);
                    textNotice.setVisibility(View.GONE);
                    textSetting.setVisibility(View.GONE);

                    imgActivity.setImageResource(R.drawable.ic_activity);
                    imgMessage.setImageResource(R.drawable.ic_message);
                    imgNotice.setImageResource(R.drawable.ic_notice);
                    imgSetting.setImageResource(R.drawable.ic_setting);

                    layoutActivity.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    layoutMessage.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    layoutNotice.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    layoutSetting.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    textHome.setVisibility(View.VISIBLE);
                    imgHome.setImageResource(R.drawable.ic_home_selected);
                    layoutHome.setBackgroundResource(R.drawable.round_background);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    layoutHome.startAnimation(scaleAnimation);

                    selectedTab = 1;
                }
            }
        });

        layoutActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTab != 2)
                {
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_container, ActivityFragment.class, null)
                            .commit();

                    textHome.setVisibility(View.GONE);
                    textMessage.setVisibility(View.GONE);
                    textNotice.setVisibility(View.GONE);
                    textSetting.setVisibility(View.GONE);

                    imgHome.setImageResource(R.drawable.ic_home);
                    imgMessage.setImageResource(R.drawable.ic_message);
                    imgNotice.setImageResource(R.drawable.ic_notice);
                    imgSetting.setImageResource(R.drawable.ic_setting);

                    layoutHome.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    layoutMessage.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    layoutNotice.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    layoutSetting.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    textActivity.setVisibility(View.VISIBLE);
                    imgActivity.setImageResource(R.drawable.ic_activity_selected);
                    layoutActivity.setBackgroundResource(R.drawable.round_background);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    layoutActivity.startAnimation(scaleAnimation);

                    selectedTab = 2;
                }
            }
        });

        layoutMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTab != 3)
                {
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_container, MessageFragment.class, null)
                            .commit();

                    textHome.setVisibility(View.GONE);
                    textActivity.setVisibility(View.GONE);
                    textNotice.setVisibility(View.GONE);
                    textSetting.setVisibility(View.GONE);

                    imgHome.setImageResource(R.drawable.ic_home);
                    imgActivity.setImageResource(R.drawable.ic_activity);
                    imgNotice.setImageResource(R.drawable.ic_notice);
                    imgSetting.setImageResource(R.drawable.ic_setting);

                    layoutHome.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    layoutActivity.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    layoutNotice.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    layoutSetting.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    textMessage.setVisibility(View.VISIBLE);
                    imgMessage.setImageResource(R.drawable.ic_message_selected);
                    layoutMessage.setBackgroundResource(R.drawable.round_background);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    layoutMessage.startAnimation(scaleAnimation);

                    selectedTab = 3;
                }
            }
        });

        layoutNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTab != 4)
                {
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_container, NoticeFragment.class, null)
                            .commit();

                    textHome.setVisibility(View.GONE);
                    textActivity.setVisibility(View.GONE);
                    textMessage.setVisibility(View.GONE);
                    textSetting.setVisibility(View.GONE);

                    imgHome.setImageResource(R.drawable.ic_home);
                    imgActivity.setImageResource(R.drawable.ic_activity);
                    imgMessage.setImageResource(R.drawable.ic_message);
                    imgSetting.setImageResource(R.drawable.ic_setting);

                    layoutHome.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    layoutActivity.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    layoutMessage.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    layoutSetting.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    textNotice.setVisibility(View.VISIBLE);
                    imgNotice.setImageResource(R.drawable.ic_notice_selected);
                    layoutNotice.setBackgroundResource(R.drawable.round_background);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    layoutNotice.startAnimation(scaleAnimation);

                    selectedTab = 4;
                }
            }
        });

        layoutSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTab != 5)
                {
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_container, SettingFragment.class, null)
                            .commit();

                    textHome.setVisibility(View.GONE);
                    textActivity.setVisibility(View.GONE);
                    textMessage.setVisibility(View.GONE);
                    textNotice.setVisibility(View.GONE);

                    imgHome.setImageResource(R.drawable.ic_home);
                    imgActivity.setImageResource(R.drawable.ic_activity);
                    imgMessage.setImageResource(R.drawable.ic_message);
                    imgNotice.setImageResource(R.drawable.ic_notice);

                    layoutHome.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    layoutActivity.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    layoutMessage.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    layoutNotice.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    textSetting.setVisibility(View.VISIBLE);
                    imgSetting.setImageResource(R.drawable.ic_setting_selected);
                    layoutSetting.setBackgroundResource(R.drawable.round_background);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    layoutSetting.startAnimation(scaleAnimation);

                    selectedTab = 5;
                }
            }
        });
    }

    public void init(){
        layoutHome = findViewById(R.id.layout_home);
        layoutActivity = findViewById(R.id.layout_activity);
        layoutMessage = findViewById(R.id.layout_message);
        layoutNotice = findViewById(R.id.layout_notice);
        layoutSetting = findViewById(R.id.layout_setting);

        imgHome = findViewById(R.id.img_home);
        imgActivity = findViewById(R.id.img_activity);
        imgMessage = findViewById(R.id.img_message);
        imgNotice = findViewById(R.id.img_notice);
        imgSetting = findViewById(R.id.img_setting);

        textHome = findViewById(R.id.text_home);
        textActivity = findViewById(R.id.text_activity);
        textMessage = findViewById(R.id.text_message);
        textNotice = findViewById(R.id.text_notice);
        textSetting = findViewById(R.id.text_setting);
    }

}