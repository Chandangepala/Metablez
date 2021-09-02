package com.makemywallet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.makemywallet.ui.Models.BannerModel;
import com.makemywallet.ui.activity.LoginActivity;
import com.makemywallet.ui.constant.ImageFilePath;
import com.makemywallet.ui.constant.UtilsMethods;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    NavController navController;
    DrawerLayout drawer;
    NavigationView navigationView;
    private boolean doubleBackToExitPressedOnce = false;
    Button btnCancel;
    BottomSheetDialog dialog_1;
    LinearLayout lLayGallery, lLayCamera, lLayRemove;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private static final int PERMISSION_REQUEST_CODE_1 = 100;
    public static final int PICK_IMAGE_GALLERY_1 = 1;
    String mConvertedImg1;
    String imgExtension;
    TextView tvChoosePhoto, tvGallary, tvCamera, tvRemove;
    CircleImageView icUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        ImageView play = toolbar.findViewById(R.id.ivPlay);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
            }
        });

        View v = navigationView.getHeaderView(0);
        TextView txtUserName = (TextView) v.findViewById(R.id.txtUserName);
        TextView txtEmail = (TextView) v.findViewById(R.id.txtEmail);
        String userName = UtilsMethods.INSTANCE.getPreference(MainActivity.this, "userName").toLowerCase();
        String userEmail = UtilsMethods.INSTANCE.getPreference(MainActivity.this, "userEmail").toLowerCase();
         icUser = v.findViewById(R.id.icUser);
        if (userName != null && !userName.isEmpty()) {
            String mName = userName.substring(0, 1).toUpperCase() + userName.substring(1);
            txtUserName.setText(mName);
        }
        if (userEmail != null && !userEmail.isEmpty()) {
            txtEmail.setText(userEmail);
        }
        CircleImageView cirIvChooseImg = v.findViewById(R.id.cirIvChooseImg);
        cirIvChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBottomSheet();
            }
        });

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_aesp_wallet,R.id.nav_dmr_report,
                R.id.nav_account_statement, R.id.nav_express_dmr_payout,
                R.id.nav_recharge_history, R.id.nav_aeps_kyc,
                R.id.nav_profile, R.id.nav_change_mpin,
                R.id.nav_change_password, R.id.nav_contact_us, R.id.nav_rate_us,
                R.id.nav_help, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        //For nav item click listner
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.nav_home) {
                    setActionBarTitle(getResources().getString(R.string.menu_home));
                }else if (id == R.id.nav_aesp_wallet) {
                    setActionBarTitle(getResources().getString(R.string.menu_aesp_wallet));
                    Toast.makeText(MainActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_dmr_report) {
                    setActionBarTitle(getResources().getString(R.string.menu_dmr_report));
                } else if (id == R.id.nav_account_statement) {
                    setActionBarTitle(getResources().getString(R.string.menu_account_statement));
                } else if (id == R.id.nav_express_dmr_payout) {
                    setActionBarTitle(getResources().getString(R.string.menu_express_dmr_payout));
                } else if (id == R.id.nav_recharge_history) {
                    setActionBarTitle(getResources().getString(R.string.menu_recharge_history));
                } else if (id == R.id.nav_aeps_kyc) {
                    setActionBarTitle(getResources().getString(R.string.menu_aeps_kyc));
                    Toast.makeText(MainActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_icici_aeps_report) {
                    setActionBarTitle(getResources().getString(R.string.menu_icici_aeps_report));
                    Toast.makeText(MainActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_profile) {
                    setActionBarTitle(getResources().getString(R.string.menu_profile));
                } else if (id == R.id.nav_change_mpin) {
                    setActionBarTitle(getResources().getString(R.string.menu_change_mpin));
                } else if (id == R.id.nav_change_password) {
                    setActionBarTitle(getResources().getString(R.string.menu_change_password));
                } else if (id == R.id.nav_contact_us) {
                    setActionBarTitle(getResources().getString(R.string.menu_contact_us));
                    Toast.makeText(MainActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_rate_us) {
                    setActionBarTitle(getResources().getString(R.string.menu_rate_us));
                    Toast.makeText(MainActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_help) {
                    setActionBarTitle(getResources().getString(R.string.menu_help));
                    Toast.makeText(MainActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_contact_us) {
                    setActionBarTitle(getResources().getString(R.string.menu_contact_us));
                    Toast.makeText(MainActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.nav_logout) {
                    UtilsMethods.INSTANCE.getShowToast(MainActivity.this, getResources().getString(R.string.logout_success));
                    UtilsMethods.INSTANCE.setPreference(MainActivity.this, "user_id", "");
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                NavigationUI.onNavDestinationSelected(menuItem, navController);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.item_notification);
        // item.setTitle(Utils.getPrefrence(this, "mstrName"));
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_notification) {
            Toast.makeText(MainActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    //For Camera 1
    private void getBottomSheet() {
        View view = getLayoutInflater().inflate(R.layout.camera_bottom_sheet, null);
        dialog_1 = new BottomSheetDialog(MainActivity.this);
        dialog_1.setContentView(view);
        tvChoosePhoto = dialog_1.findViewById(R.id.tvChoosePhoto);
        tvGallary = dialog_1.findViewById(R.id.tvGallary);
        tvCamera = dialog_1.findViewById(R.id.tvCamera);
        tvRemove = dialog_1.findViewById(R.id.tvRemove);
        btnCancel = dialog_1.findViewById(R.id.btnCancel);
        lLayGallery = dialog_1.findViewById(R.id.lLayGallery);
        lLayCamera = dialog_1.findViewById(R.id.lLayCamera);
        lLayRemove = dialog_1.findViewById(R.id.lLayRemove);

        lLayGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhotoFromGallary();
            }
        });

        //click for camera Image 1
        lLayCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilsMethods.getShowToast(MainActivity.this, "Comming soon");
            }
        });
        lLayRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_1.dismiss();
                icUser.setImageDrawable(getResources().getDrawable(R.drawable.user));
                mConvertedImg1 = null;
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_1.dismiss();
            }
        });

        dialog_1.show();
    }

    private void choosePhotoFromGallary() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_GALLERY_1);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //for gallery Image
        if (requestCode == PICK_IMAGE_GALLERY_1 && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                final Uri imageUri = data.getData();
                String path = ImageFilePath.getPath(MainActivity.this, imageUri);
                Log.d("TAG", "onActivityResult: " + path);
                imgExtension = path.substring(path.lastIndexOf("."));
                if (imgExtension.equalsIgnoreCase(".jpg")) {
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false);
                    mConvertedImg1 = convertToBase64(resizedBitmap);
                    icUser.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(this, "Please select jpg image only", Toast.LENGTH_SHORT).show();
                }
                dialog_1.dismiss();
            } else {
                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_SHORT).show();
            }
        } /*else if (requestCode == PERMISSION_REQUEST_CODE_1 && resultCode == RESULT_OK) {
            //for camera Image 1
            if (data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                Uri tempUriImg1 = getImageUri(getContext(), imageBitmap);
                // CALL THIS METHOD TO GET THE ACTUAL PATH
                File finalFileImg = new File(getRealPathFromURI(tempUriImg1));
                String fileEx = String.valueOf(finalFileImg);
                imgExtension = fileEx.substring(fileEx.lastIndexOf("."));
                Bitmap bitmap = BitmapFactory.decodeFile(fileEx);
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false);
                mConvertedImg1 = convertToBase64(resizedBitmap);
                binding.ivUserProfile.setImageBitmap(imageBitmap);
                dialog_1.dismiss();
            }
    }*/
}

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    // For Image path
    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (this.getContentResolver() != null) {
            Cursor cursor = this.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    //For Converting Image into Base64
    private String convertToBase64(Bitmap bitmap) {
        String encodedStr = "";
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
        byte[] ba = bao.toByteArray();
        String encodedImage = Base64.encodeToString(ba, Base64.NO_WRAP);
        byte[] utf8Bytes = new byte[1024];
        try {
            utf8Bytes = encodedImage.getBytes("UTF-8");
            encodedStr = new String(utf8Bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodedStr;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        UtilsMethods.INSTANCE.getShowToast(this, "Please click BACK again to exit");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}