package com.example.martin.recyclingapp;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

/**
 * Created by Martin on 11/02/2018.
 */

public class ResultFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        Bundle bundle = getArguments();

        String scannedBarcode = bundle.getString("barcode");

        ShareButton shareButton = view.findViewById(R.id.resultShareButton);
        TextView productName = view.findViewById(R.id.resultProductNameTextView);
        TextView category = view.findViewById(R.id.resultProductCategoryTextView);
        TextView dateScanned = view.findViewById(R.id.resultDateTextView);
        TextView barcode = view.findViewById(R.id.resultBarcodeTextView);
        TextView material = view.findViewById(R.id.resultProductMaterialTextView);

        if (bundle.containsKey("name")) {

            productName.setText(bundle.getString("name"));
            category.setText(bundle.getString("category"));
            barcode.setText(bundle.getString("barcode"));
            material.setText(bundle.getString("material"));
            dateScanned.setText(bundle.getString("dateScanned"));

        } else {

            // firebase get logic goes here

        }

        CallbackManager callbackManager = CallbackManager.Factory.create();
        ShareDialog dialog = new ShareDialog(getActivity());
        shareButton.setShareContent(getLinkContent());

        shareButton.setOnClickListener(v -> {

            if (dialog.canShow(ShareLinkContent.class)) {
                dialog.show(getLinkContent());
            }

            dialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {

                @Override
                public void onSuccess(Sharer.Result result) {
                    Toast.makeText(getActivity(),
                            "Link posted successfully",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancel() {
                    Toast.makeText(getActivity(),
                            "Post action cancelled...",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(FacebookException error) {
                    Toast.makeText(getActivity(),
                            "Something went wrong...",
                            Toast.LENGTH_SHORT).show();
                }
            });
        });

        return view;
    }

    private ShareLinkContent getLinkContent() {
        return new ShareLinkContent.Builder()
                .setQuote("Check out the app here!")
                .setContentUrl(Uri.parse("https://github.com/04HuynhM"))
                .build();
    }
}