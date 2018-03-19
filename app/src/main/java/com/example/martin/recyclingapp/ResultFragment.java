package com.example.martin.recyclingapp;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

/**
 * Created by Martin on 11/02/2018.
 */

public class ResultFragment extends Fragment {

    ShareButton shareButton;
    CallbackManager callbackManager;
    ShareDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        Bundle bundle = getArguments();
        String scannedBarcode = bundle.getString("barcode");

        shareButton = view.findViewById(R.id.resultShareButton);

        callbackManager = CallbackManager.Factory.create();
        dialog = new ShareDialog(getActivity());

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(getActivity(),
                                "Link Posted Successfully",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(getActivity(),
                                "Something went wrong...",
                                Toast.LENGTH_SHORT).show();
                    }
                });


                shareButton.setShareContent(getLinkContent());

                if (dialog.canShow(ShareLinkContent.class)) {
                    dialog.show(getLinkContent());
                }
            }
        });

        return view;
    }

    public ShareLinkContent getLinkContent(){
        return new ShareLinkContent.Builder()
                .setQuote("Check out the app here!")
                .setContentUrl(Uri.parse("https://github.com/04HuynhM"))
                .build();
    }


}
