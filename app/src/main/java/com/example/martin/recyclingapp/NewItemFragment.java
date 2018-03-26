package com.example.martin.recyclingapp;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.martin.recyclingapp.db.AppDatabase;
import com.example.martin.recyclingapp.db.Item;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewItemFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_item, container, false);

        TextView barcode = v.findViewById(R.id.new_item_barcode);
        EditText name = v.findViewById(R.id.new_item_name);
        EditText material = v.findViewById(R.id.new_item_material);
        RadioGroup boxGroup = v.findViewById(R.id.new_item_radiogroup);
        Button addItemButton = v.findViewById(R.id.add_item_button);


        Bundle bundle = getArguments();
        barcode.setText(bundle.getString("barcode"));
        String currentDate = bundle.getString("date");


        addItemButton.setOnClickListener(view -> {

            if(!name.getText().toString().equals("")
                    && !material.getText().toString().equals("")
                    && boxGroup.getCheckedRadioButtonId()!=-1) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                AlertDialog dialog = builder.setTitle("Add Item")
                        .setMessage("Please make sure all information is correct. Continue?")
                        .setPositiveButton("Yes", (dialogInterface, i) -> {

                            StringBuilder categoryBuilder = new StringBuilder();

                            int checkedId = boxGroup.getCheckedRadioButtonId();

                            switch (checkedId) {
                                case R.id.new_item_radio_paper:
                                    categoryBuilder.append("Paper");
                                    break;
                                case R.id.new_item_radio_plastic:
                                    categoryBuilder.append("Plastic");
                                    break;
                                case R.id.new_item_radio_burnable:
                                    categoryBuilder.append("Burnable");
                                    break;
                                case R.id.new_item_radio_lightbulb:
                                    categoryBuilder.append("Lightbulb");
                                    break;
                                case R.id.new_item_radio_battery:
                                    categoryBuilder.append("Battery");
                                    break;
                                case R.id.new_item_radio_can:
                                    categoryBuilder.append("Can");
                                    break;
                                case R.id.new_item_radio_oil:
                                    categoryBuilder.append("Oil");
                                    break;
                            }

                            Item item = new Item(barcode.getText().toString(),
                                    barcode.getText().toString(),
                                    name.getText().toString(),
                                    material.getText().toString(),
                                    currentDate,
                                    categoryBuilder.toString());

                            AppDatabase.getAppDatabase(getActivity())
                                    .addItemToFirebaseUser(item);

                            ResultFragment fragment = new ResultFragment();
                            Bundle resultBundle = new Bundle();

                            resultBundle.putString("name",
                                    item.getProductName());
                            resultBundle.putString("category",
                                    item.getProductCategory());
                            resultBundle.putString("dateScanned",
                                    item.getDateScanned());
                            resultBundle.putString("materials",
                                    item.getProductMaterial());
                            resultBundle.putString("barcode",
                                    item.getBarcodeNumber());

                            fragment.setArguments(resultBundle);

                            getActivity()
                                    .getFragmentManager()
                                    .beginTransaction()
                                    .remove(this)
                                    .commit();

                            getActivity()
                                    .getFragmentManager()
                                    .beginTransaction()
                                    .replace(android.R.id.content, fragment)
                                    .addToBackStack("resultFragment")
                                    .commit();

                        }).setNegativeButton("No", (dialogInterface, i) ->
                                dialogInterface.dismiss()).create();

                dialog.setOnShowListener(dialogInterface -> {
                    dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
                            .setTextColor(Color.BLACK);
                    dialog.getButton(DialogInterface.BUTTON_POSITIVE)
                            .setTextColor(Color.BLACK);
                });
                dialog.show();
            } else {
                Toast.makeText(getActivity(),
                        "Please fill all the fields",
                        Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }

}
