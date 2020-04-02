package c.cx;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;


import butterknife.ButterKnife;
/**
 * @author syl
 * @time 2018/8/23 上午11:44
 */
public class RecordVoicePopWindow extends PopupWindow {

    TextView mTvRcStatus;
    ImageView mIvRcStatus;
    ImageView mIvRcVolume;
    TextView mTvRcTime;
    private View v;

    public RecordVoicePopWindow(Context context) {
        super(context);
        v=LayoutInflater.from(context).inflate(R.layout.community_pop_record_voice, null);
        setContentView(v);
        mTvRcStatus=v.findViewById(R.id.tv_rc_status);
        mIvRcStatus=v.findViewById(R.id.iv_rc_status);
        mIvRcVolume=v.findViewById(R.id.iv_rc_volume);
        mTvRcTime=v.findViewById(R.id.tv_rc_time);
        ButterKnife.bind(this, v);
        // 设置SelectPicPopupWindow弹出窗体的宽
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        setFocusable(true);
        setOutsideTouchable(false);
        setTouchable(false);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        setBackgroundDrawable(dw);
    }

    /**
     * 即将超时
     *
     * @param remainder
     */
    public void showTimeOutTipView(int remainder) {
        mIvRcStatus.setVisibility(View.GONE);
        mIvRcVolume.setVisibility(View.GONE);
        mTvRcStatus.setVisibility(View.VISIBLE);
        mTvRcStatus.setText(R.string.community_chat_list_remove_above_cancel_send);
        mTvRcTime.setText(String.format("%s", remainder));
        mTvRcTime.setVisibility(View.VISIBLE);
    }

    /**
     * 正常录制
     */
    public void showRecordingTipView() {
        mIvRcStatus.setVisibility(View.VISIBLE);
        mIvRcVolume.setVisibility(View.VISIBLE);
        mIvRcStatus.setImageResource(R.mipmap.community_record_volume_microphone);
        mTvRcStatus.setVisibility(View.VISIBLE);
        mTvRcStatus.setText(R.string.community_chat_list_remove_above_cancel_send);
        mTvRcTime.setVisibility(View.GONE);
    }

    /**
     * 录制时间太短
     */
    public void showRecordTooShortTipView() {
        mIvRcStatus.setVisibility(View.VISIBLE);
        mIvRcVolume.setVisibility(View.GONE);
        mIvRcStatus.setImageResource(R.mipmap.community_record_volume_warning);
        mTvRcStatus.setText(R.string.community_chat_list_rec_voice_short);
    }

    /**
     * 松开手指，取消发送
     */
    public void showCancelTipView() {
        mIvRcStatus.setVisibility(View.VISIBLE);
        mIvRcVolume.setVisibility(View.GONE);
        mIvRcStatus.setImageResource(R.mipmap.community_record_volume_cancel);
        mTvRcStatus.setVisibility(View.VISIBLE);
        mTvRcStatus.setText(R.string.community_chat_list_loosen_cancel_send);
        mTvRcTime.setVisibility(View.GONE);
    }

    /**
     * 更新当前音量大小
     */
    public void updateCurrentVolume(int decibel) {
        switch (decibel / 5) {
            case 0:
                mIvRcVolume.setImageResource(R.mipmap.community_record_volume_01);
                break;
            case 1:
                mIvRcVolume.setImageResource(R.mipmap.community_record_volume_01);
                break;
            case 2:
                mIvRcVolume.setImageResource(R.mipmap.community_record_volume_02);
                break;
            case 3:
                mIvRcVolume.setImageResource(R.mipmap.community_record_volume_03);
                break;
            case 4:
                mIvRcVolume.setImageResource(R.mipmap.community_record_volume_04);
                break;
            case 5:
                mIvRcVolume.setImageResource(R.mipmap.community_record_volume_05);
                break;
            case 6:
                mIvRcVolume.setImageResource(R.mipmap.community_record_volume_06);
                break;
            default:
                mIvRcVolume.setImageResource(R.mipmap.community_record_volume_06);
        }
    }
}