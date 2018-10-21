package zhawenting.electroniccontroller.activity;

import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhawenting.electroniccontroller.R;
import zhawenting.electroniccontroller.base.BaseActivity;

public class VideoActivity extends BaseActivity {


    @BindView(R.id.videoview)
    VideoView videoview;
    private MediaController mediaController;
    private File mVideoFile;

    @Override
    protected void initView() {
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoview);//mVideoView必须套一层FramLayout,UI才能匹配在一起
        videoview.setMediaController(mediaController);
        mVideoFile = new File(Environment.getExternalStorageDirectory(),
                "advisorConn_login.mp4");
        videoview.setVideoPath(mVideoFile.getAbsolutePath());
        videoview.start();
    }

    @Override
    public int getLayoutResID() {
        return R.layout.activity_video;
    }

}
