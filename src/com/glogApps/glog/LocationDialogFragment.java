package com.glogApps.glog;

import com.gordApps.glog.R;

import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import android.support.v4.app.DialogFragment;
import android.widget.Toast;

public class LocationDialogFragment extends DialogFragment {

    
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.Done)//(R.string.location_dialog_message)
                .setPositiveButton(R.string.Done,//R.string.location_dialog_positive_button,
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent settingsIntent = new Intent(
                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(settingsIntent);
                        }
                    })
                .setNegativeButton("Cancelar",//R.string.location_dialog_negative_button,
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getActivity(),
                                "no hay ubucacion", Toast.LENGTH_LONG)//R.string.no_location_message, Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
        return builder.create();
    }
}
