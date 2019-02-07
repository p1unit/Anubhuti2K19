package com.anubhuti.knit.Activities;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.anubhuti.knit.Model.EventCatogry;
import com.anubhuti.knit.R;
import com.anubhuti.knit.Utils.ApplicationContextProvider;
import com.anubhuti.knit.Utils.Config;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

public class PopUpContact extends AppCompatActivity {

    private TextView name1;
    private TextView name2;
    private TextView phone1;
    private TextView phone2;
    private ImageView phoneimg1;
    private ImageView phoneimg2;

    String phoneno="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pop_up_contact);


        Intent intent=getIntent();
        final EventCatogry obj= (EventCatogry) intent.getSerializableExtra("EventData");

        name1=this.findViewById(R.id.name1);
        name2=this.findViewById(R.id.name2);
        phone1=this.findViewById(R.id.phone1);
        phone2=this.findViewById(R.id.phone2);

        name1.setText(obj.getName1());
        name2.setText(obj.getName2());
        phone1.setText(obj.getPhone1());
        phone2.setText(obj.getPhone2());

        phoneimg1=this.findViewById(R.id.phone_image1);
        phoneimg2=this.findViewById(R.id.phone_image2);

        phoneimg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneno=obj.getPhone1();
                askPermission();
            }
        });

        phoneimg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneno=obj.getPhone2();
                askPermission();
            }
        });

    }


    private void askPermission() {

        TedPermission.with(ApplicationContextProvider.getContext())
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this feature\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CALL_PHONE)
                .check();
    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {

            callPhone();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {

            Config.toastShort(ApplicationContextProvider.getContext(), Config.PERMISSION_REQUEST);
        }


    };

    private void callPhone() {
        String s=phoneno;
        String ss="tel:"+ s;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(ss));
        startActivity(intent);
    }
}


//    public void showAlertDialog(){
//
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
//        LayoutInflater inflater = this.getLayoutInflater();
//        final View dialogView = inflater.inflate(R.layout.event_contact, null);
//        dialogBuilder.setView(dialogView);
//        final TextView phone1 =  (TextView) dialogView.findViewById(R.id.phone1);
//        final TextView phone2 = (TextView) dialogView.findViewById(R.id.phone2);
//        final TextView name1 =  (TextView) dialogView.findViewById(R.id.name1);
//        final TextView name2 = (TextView) dialogView.findViewById(R.id.name2);
//        dialogBuilder.setTitle(Html.fromHtml("<font color = '#ff6556'> Contact Details</font>"));
//
//        phone1.setText(eventdata.getPhone1());
//        phone2.setText(eventdata.getPhone2());
//        name1.setText(eventdata.getName1());
//        name2.setText(eventdata.getName2());
//
//        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//
//                userMigration.setPaymentEmail(emailTxt.getText().toString());
//                userMigration.setPaymentPhone(phoneTxt.getText().toString());
//                paymentDetailDone();
//                dialog.dismiss();
//            }
//        });
//        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                dialog.dismiss();
//            }
//        });
//        AlertDialog b = dialogBuilder.create();
//        b.show();
//    }