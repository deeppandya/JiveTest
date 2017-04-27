package mainpackage.jivetest.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import mainpackage.jivetest.R;
import mainpackage.jivetest.iviews.IFlickerPhotoView;
import mainpackage.jivetest.model.FlickerPhotos;
import mainpackage.jivetest.model.UserInfo;
import mainpackage.jivetest.presenter.FlickerPhotoViewPresenter;
import mainpackage.jivetest.support.Constants;
import mainpackage.jivetest.support.Scopes;
import mainpackage.jivetest.support.Utils;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;

public class FlickerPhotoViewActivity extends AppCompatActivity implements IFlickerPhotoView{

    private ImageView imgFullView;
    private TextView txtComments,txtUserName,txtRealName,txtLocation;

    private ProgressDialog mProgressDialog;
    private FlickerPhotoViewPresenter flickerPhotoViewPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flicker_photo_view);

        flickerPhotoViewPresenter = (FlickerPhotoViewPresenter) getSystemService(Scopes.FLICKERPHOTOVIEW);
        flickerPhotoViewPresenter.takeView(this);

        imgFullView = (ImageView) findViewById(R.id.imgFullView);
        txtComments = (TextView) findViewById(R.id.txtComments);
        txtUserName = (TextView) findViewById(R.id.txtUserName);
        txtRealName = (TextView) findViewById(R.id.txtRealName);
        txtLocation = (TextView) findViewById(R.id.txtLocation);

        txtComments.setMovementMethod(new ScrollingMovementMethod());

        setImage();

    }

    private void setImage() {
        if (getIntent() != null && getIntent().getParcelableExtra(Constants.PHOTO) != null) {

            FlickerPhotos flickerPhotos=(FlickerPhotos)getIntent().getParcelableExtra(Constants.PHOTO);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Glide.with(FlickerPhotoViewActivity.this).load(Utils.getFlickerPhotoUrl(flickerPhotos)).placeholder(getDrawable(R.mipmap.ic_launcher)).into(imgFullView);
            } else {
                Glide.with(FlickerPhotoViewActivity.this).load(Utils.getFlickerPhotoUrl(flickerPhotos)).into(imgFullView);
            }

            flickerPhotoViewPresenter.getUserName(flickerPhotos.getOwner());

            flickerPhotoViewPresenter.getPhotoComments(flickerPhotos.getId());

        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showProgressDialog(String progressDialogTitle) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setIndeterminate(true);
            SpannableString ss2=  new SpannableString(progressDialogTitle);
            ss2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 0, ss2.length(), 0);
            mProgressDialog.setMessage(ss2);
        }

        mProgressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void updateUserName(UserInfo userInfo) {
        txtUserName.setText("Username : "+userInfo.getUsername());
        txtRealName.setText("Realname : "+userInfo.getRealname());
        txtLocation.setText("Location : "+userInfo.getLocation());
    }

    @Override
    public void updatePhotoComments(String comments) {
        txtComments.setText(comments);
    }

    @Override
    public Object getSystemService(@NonNull String name) {

        MortarScope activityScope = MortarScope.findChild(getApplicationContext(), Scopes.FLICKERPHOTOVIEW);

        if (activityScope == null) {
            activityScope = MortarScope.buildChild(getApplicationContext()) //
                    .withService(BundleServiceRunner.SERVICE_NAME, new BundleServiceRunner())
                    .withService(Scopes.FLICKERPHOTOVIEW, new FlickerPhotoViewPresenter())
                    .build(Scopes.FLICKERPHOTOVIEW);
        }

        return activityScope.hasService(name) ? activityScope.getService(name)
                : super.getSystemService(name);
    }
}
