package com.example.myapplication.ui.tools;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.Prevalent.Prevalent;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;
    private EditText name_Edit_text,phone_number_Edit_text,password_Edit_text;
    private Button button_update;
    private String checker= "";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);
        name_Edit_text=root.findViewById(R.id.update_name);
        phone_number_Edit_text=root.findViewById(R.id.update_phone_number);
        password_Edit_text=root.findViewById(R.id.update_password);
        button_update=root.findViewById(R.id.update_button);


        userInfoDisplay(name_Edit_text, phone_number_Edit_text, password_Edit_text, button_update);

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                   userInfoSaved();

            }
        });


        return root;


    }

    private void userInfoSaved()
    {
        if(TextUtils.isEmpty(name_Edit_text.getText().toString()))
        {
            Toast.makeText(getActivity(), "Name is mandatory", Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(password_Edit_text.getText().toString()))
        {
            Toast.makeText(getActivity(), "Password is mandatory", Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(phone_number_Edit_text.getText().toString()))
        {
            Toast.makeText(getActivity(), "Phone Number is mandatory", Toast.LENGTH_SHORT).show();
        }
        else
        {
            SaveUserDetails();

        }
    }

    private void SaveUserDetails()
    {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

        HashMap<String, Object> userMap=new HashMap<>();

        userMap.put("name", name_Edit_text.getText().toString());
        userMap.put("password", password_Edit_text.getText().toString());
        userMap.put("phone", phone_number_Edit_text.getText().toString());

        ref.child(Prevalent.currentOnlineUser.getStudentNo()).updateChildren(userMap);
        Toast.makeText(getActivity(), "Details has been successfully update", Toast.LENGTH_SHORT).show();
         Fragment toolsfrag=new ToolsFragment();
         getActivity().getSupportFragmentManager().beginTransaction().remove(toolsfrag).commit();

    }

    private void userInfoDisplay(EditText name_edit_text, EditText phone_number_edit_text, EditText password_edit_text, Button button_update)
    {
        DatabaseReference UsersRef= FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getStudentNo());

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    if(dataSnapshot.child("studentNo").exists())
                    {
                        String studentNo=dataSnapshot.child("studentNo").getValue().toString();
                        String name=dataSnapshot.child("name").getValue().toString();
                        String password=dataSnapshot.child("password").getValue().toString();
                        String phone=dataSnapshot.child("phone").getValue().toString();

                        name_Edit_text.setText(name);
                        phone_number_Edit_text.setText(phone);
                        password_Edit_text.setText(password);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }
}