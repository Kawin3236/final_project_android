package kmitl.kawin58070006.horyuni.controller.fragment;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kmitl.kawin58070006.horyuni.R;
import kmitl.kawin58070006.horyuni.controller.activity.LoginActivity;
import kmitl.kawin58070006.horyuni.model.ImageUpload;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends Fragment {
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private ImageView imageView;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;
    private ImageView imageView6;
    private EditText dormitoryName;
    private EditText editTextMoreDetai;
    private Uri imgUri1;
    private Uri imgUri2;
    private Uri imgUri3;
    private Uri imgUri4;
    private Uri imgUri5;
    private Uri imgUri6;
    private Button btnUpload;
    private int check;
    private int dismissCheck;


    public static final String FB_Storage_Path = "image/";
    public static final String FB_Database_Path = "post";
    public static final int Request_Code = 1234;
    public static final int Request_Code2 = 2;
    public static final int Request_Code3 = 3;
    public static final int Request_Code4 = 4;
    public static final int Request_Code5 = 5;
    public static final int Request_Code6 = 6;

    private List<Uri> listUri = new ArrayList<>();
    private List<String> listUriString = new ArrayList<>();
    private List<StorageReference> storageReferencesList = new ArrayList<>();

    private Spinner spinner;
    private String zone;
    private ProgressDialog dialog = null;

    private ImageUpload imageUpload;
    ArrayAdapter<CharSequence> adapter;

    private static String username;
    private static String uriProfile;

    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private String date;
    private int selectImg = 0;

    private StorageReference ref;

    public PostFragment() {
        // Required empty public constructor
    }

    public static PostFragment newInstance(String username, String uriProfile) {

        Bundle args = new Bundle();
        setUsername(username);
        setUriProfile(uriProfile);
        PostFragment fragment = new PostFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static void setUriProfile(String uriProfile) {
        PostFragment.uriProfile = uriProfile;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post, container, false);
        imageView = (ImageView) rootView.findViewById(R.id.imageView);
        imageView2 = (ImageView) rootView.findViewById(R.id.imageView2);
        imageView3 = (ImageView) rootView.findViewById(R.id.imageView3);
        imageView4 = (ImageView) rootView.findViewById(R.id.imageView4);
        imageView5 = (ImageView) rootView.findViewById(R.id.imageView5);
        imageView6 = (ImageView) rootView.findViewById(R.id.imageView6);
        dormitoryName = (EditText) rootView.findViewById(R.id.editName);
        editTextMoreDetai = (EditText) rootView.findViewById(R.id.editMoreDetail);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImg(Request_Code);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImg(Request_Code2);
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImg(Request_Code3);
            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImg(Request_Code4);
            }
        });
        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImg(Request_Code5);
            }
        });
        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImg(Request_Code6);
            }
        });


        spinner = rootView.findViewById(R.id.spinnerTxtZone);
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.dormitory_zone, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                zone = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(FB_Database_Path);
        btnUpload = rootView.findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectImg > 0 && !dormitoryName.getText().toString().isEmpty() && !editTextMoreDetai.getText().toString().isEmpty()) {
                    if (imgUri1 != null) addToStorage(imgUri1);
                    if (imgUri2 != null) addToStorage(imgUri2);
                    if (imgUri3 != null) addToStorage(imgUri3);
                    if (imgUri4 != null) addToStorage(imgUri4);
                    if (imgUri5 != null) addToStorage(imgUri5);
                    if (imgUri6 != null) addToStorage(imgUri6);

                    dialog = new ProgressDialog(getActivity());
                    dialog.setMessage("กำลังอัพโหลดโพสต์");
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(false);
                    dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                            }
                            return true;
                        }
                    });
                    //Add file to reference
                    for (int i = 0; i < storageReferencesList.size(); i++) {
                        storageReferencesList.get(i).putFile(listUri.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                try {
                                    check++;
                                    String uploadId = databaseReference.push().getKey();
                                    listUriString.add(taskSnapshot.getDownloadUrl().toString());
                                    if (storageReferencesList.size() == 1 && check == storageReferencesList.size())
                                        imageUpload = new ImageUpload(uploadId, uriProfile.toString(), username.toString(), getDateTime(), dormitoryName.getText().toString(), zone, editTextMoreDetai.getText().toString(), listUriString.get(0));
                                    else if (storageReferencesList.size() == 2 && check == storageReferencesList.size())
                                        imageUpload = new ImageUpload(uploadId, uriProfile.toString(), username, getDateTime(), dormitoryName.getText().toString(), zone, editTextMoreDetai.getText().toString(), listUriString.get(0), listUriString.get(1));
                                    else if (storageReferencesList.size() == 3 && check == storageReferencesList.size())
                                        imageUpload = new ImageUpload(uploadId, uriProfile.toString(), username, getDateTime(), dormitoryName.getText().toString(), zone, editTextMoreDetai.getText().toString(), listUriString.get(0), listUriString.get(1), listUriString.get(2));
                                    else if (storageReferencesList.size() == 4 && check == storageReferencesList.size())
                                        imageUpload = new ImageUpload(uploadId, uriProfile.toString(), username, getDateTime(), dormitoryName.getText().toString(), zone, editTextMoreDetai.getText().toString(), listUriString.get(0), listUriString.get(1), listUriString.get(2), listUriString.get(3));
                                    else if (storageReferencesList.size() == 5 && check == storageReferencesList.size())
                                        imageUpload = new ImageUpload(uploadId, uriProfile.toString(), username, getDateTime(), dormitoryName.getText().toString(), zone, editTextMoreDetai.getText().toString(), listUriString.get(0), listUriString.get(1), listUriString.get(2), listUriString.get(3), listUriString.get(4));
                                    else if (storageReferencesList.size() == 6 && check == storageReferencesList.size())
                                        imageUpload = new ImageUpload(uploadId, uriProfile.toString(), username, getDateTime(), dormitoryName.getText().toString(), zone, editTextMoreDetai.getText().toString(), listUriString.get(0), listUriString.get(1), listUriString.get(2), listUriString.get(3), listUriString.get(4), listUriString.get(5));

                                    //Save image info in to firebase database

                                    databaseReference.child(uploadId).setValue(imageUpload);
                                } catch (Exception e) {
                                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                                    startActivity(intent);
                                }
                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        //Dimiss dialog when error
                                        dialog.dismiss();
                                        //Display err toast msg
                                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        dismissCheck++;
                                        if (dismissCheck == storageReferencesList.size())
                                            dialog.dismiss();
                                    }
                                });
                    }

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainer, HomeFragment.newInstance())
                            .addToBackStack(null)
                            .commit();
                } else {
                    Toast.makeText(getContext(), "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }

    public static void setUsername(String username) {
        PostFragment.username = username;
    }

    public void addToStorage(Uri uri) {
        ref = storageReference.child(FB_Storage_Path + System.currentTimeMillis() + "." + getImageExt(uri));
        storageReferencesList.add(ref);
        listUri.add(uri);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri1 = data.getData();
            selectImg++;
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imgUri1);
                imageView.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == Request_Code2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri2 = data.getData();
            selectImg++;
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imgUri2);
                imageView2.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == Request_Code3 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri3 = data.getData();
            selectImg++;

            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imgUri3);
                imageView3.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == Request_Code4 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri4 = data.getData();
            selectImg++;
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imgUri4);
                imageView4.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == Request_Code5 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri5 = data.getData();
            selectImg++;
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imgUri5);
                imageView5.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == Request_Code6 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imgUri6 = data.getData();
            selectImg++;
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imgUri6);
                imageView6.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void selectImg(final int Request_Code) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select image"), Request_Code);
    }

    public String getDateTime() {
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyy HH:mm");
        date = simpleDateFormat.format(calendar.getTime());
        return date;
    }
}
