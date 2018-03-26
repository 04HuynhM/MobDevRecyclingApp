package com.example.martin.recyclingapp;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.martin.recyclingapp.db.AppDatabase;
import com.example.martin.recyclingapp.db.Item;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        ImageView productImage = view.findViewById(R.id.resultProductImageView);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM HH:mm",
                getResources().getConfiguration().locale);
        String currentDate = sdf.format(date);

        if (bundle.containsKey("name")) {
//            String categoryString = bundle.getString("category");
            productName.setText(bundle.getString("name"));
//            category.setText(categoryString);
            barcode.setText(bundle.getString("barcode"));
            material.setText(bundle.getString("materials"));
            dateScanned.setText(bundle.getString("dateScanned"));
            System.out.println(category.getText().toString());
            System.out.println(barcode.getText().toString());
            System.out.println(material.getText().toString());
            System.out.println(productName.getText().toString());
            System.out.println(dateScanned.getText().toString());

//            switch (categoryString) {
//                case "paper":
//                    productImage.setImageResource(R.drawable.ic_paper);
//                    break;
//                case "plastic":
//                    productImage.setImageResource(R.drawable.ic_bottle);
//                    break;
//                case "burnable":
//                    productImage.setImageResource(R.drawable.ic_burnables);
//                    break;
//                case "lightbulb":
//                    productImage.setImageResource(R.drawable.ic_bulb);
//                    break;
//                case "battery":
//                    productImage.setImageResource(R.drawable.ic_battery);
//                    break;
//                case "can":
//                    productImage.setImageResource(R.drawable.ic_can);
//                    break;
//                case "oil":
//                    productImage.setImageResource(R.drawable.ic_oil);
//                    break;
//            }

        } else {

            Item item = checkAppDatabase(bundle);

            if (item != null) {

                productName.setText(item.getProductName());
                category.setText(item.getCategory());
                dateScanned.setText(currentDate);
                barcode.setText(item.getBarcodeNumber());
                material.setText(item.getProductMaterial());

//                new DownloadImageAsyncTask(productImage).execute(item.getImageUrl());

            } else {

                Item firebaseItem = AppDatabase.getAppDatabase(getActivity())
                        .getItemFromFirebase(barcode.getText()
                                .toString());

                if (firebaseItem != null) {
                    Toast.makeText(getActivity(), "firebase item not null", Toast.LENGTH_SHORT).show();
                    productName.setText(firebaseItem.getProductName());
                    category.setText(firebaseItem.getCategory());
                    dateScanned.setText(currentDate);
                    barcode.setText(firebaseItem.getBarcodeNumber());
                    material.setText(firebaseItem.getProductMaterial());

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    AlertDialog dialog = builder.setTitle("Not in Database")
                            .setMessage("This item is not in our database. " +
                                    "Would you like to add it?")
                            .setPositiveButton("Yes", (dialogInterface, i) -> {

                                NewItemFragment newItem = new NewItemFragment();
                                Bundle barcodeBundle = new Bundle();
                                barcodeBundle.putString("barcode", scannedBarcode);
                                barcodeBundle.putString("date", currentDate);
                                newItem.setArguments(barcodeBundle);

                                getActivity().getFragmentManager()
                                        .beginTransaction()
                                        .add(android.R.id.content, newItem)
                                        .commit();
                            })
                            .setNegativeButton("No", (dialogInterface, i) ->
                                    dialogInterface.dismiss()).create();

                    dialog.setOnShowListener(dialogInterface -> {
                        dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
                                .setTextColor(Color.BLACK);
                        dialog.getButton(DialogInterface.BUTTON_POSITIVE)
                                .setTextColor(Color.BLACK);
                    });
                    dialog.show();
                }
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
        }
        return view;
    }

    private ShareLinkContent getLinkContent() {
        return new ShareLinkContent.Builder()
                .setQuote("Check out the app here!")
                .setContentUrl(Uri.parse("https://github.com/04HuynhM"))
                .build();
    }

    private Item checkAppDatabase(Bundle bundle) {

        final Item[] item = new Item[1];
        new Thread(()-> {
             item[0] = AppDatabase.getAppDatabase(getActivity())
                .itemDao()
                .findByUid(bundle
                        .getString("barcode"));

        }).start();
        return item[0];
    }
}