package com.javacodegeeks.animalsrecognition;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.javacodegeeks.animalsrecognition.ml.Model;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class recognitionFragment extends Fragment {

    Button camera, gallery, malay, eng;
    ImageView imageView;
    TextView result, desc;
    int imageSize = 32;

    public recognitionFragment(){
        // require a empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recognition, container, false);

        camera = view.findViewById(R.id.button);
        gallery = view.findViewById(R.id.button2);
        malay = view.findViewById(R.id.malay);
        eng = view.findViewById(R.id.eng);

        result = view.findViewById(R.id.result);
        desc = view.findViewById(R.id.desc);
        imageView = view.findViewById(R.id.imageView);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getContext(),android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 3);
                } else {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                }
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(cameraIntent, 1);
            }
        });

        return view;
    }
    public void classifyImage(Bitmap image) {
        try {
            Model model = Model.newInstance(getContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 32, 32, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int[] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
            int pixel = 0;
            //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
            for (int i = 0; i < imageSize; i++) {
                for (int j = 0; j < imageSize; j++) {
                    int val = intValues[pixel++]; // RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 1));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 1));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 1));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            Model.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();
            // find the index of the class with the biggest confidence.
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidences.length; i++) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }
            String[] classes = {"Deer","Asian elephant", "Orangutan", "Sun bear", "Malayan tiger"};
            result.setText(classes[maxPos]);

            if(result.getText().equals("Malayan tiger")){
                desc.setText(R.string.MalayanTiger);
                malay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        desc.setText(R.string.HarimauMalaya);
                        result.setText("Harimau Malaya");
                    }
                });
                eng.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        desc.setText(R.string.MalayanTiger);
                        result.setText("Malayan tiger");
                    }
                });
            }

            else if(result.getText().equals("Asian elephant")){
                desc.setText(R.string.AsianElephant);
                malay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        desc.setText(R.string.GajahAsia);
                        result.setText("Gajah Malaysia");
                    }
                });
                eng.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        desc.setText(R.string.AsianElephant);
                        result.setText("Asian Elephant");
                    }
                });
            }
            else if(result.getText().equals("Sun bear")){
                desc.setText(R.string.SunBear);
                malay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        desc.setText(R.string.BeruangMatahari);
                        result.setText("Beruang Madu/Matahari");
                    }
                });
                eng.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        desc.setText(R.string.SunBear);
                        result.setText("Sun bear");
                    }
                });
            }
            else if(result.getText().equals("Deer")){
                desc.setText(R.string.deer);
                malay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        desc.setText(R.string.Rusa);
                        result.setText("Rusa");
                    }
                });
                eng.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        desc.setText(R.string.deer);
                        result.setText("Deer");
                    }
                });
            }
            else if(result.getText().equals("Orangutan")){
                desc.setText(R.string.OrangUtan);
                malay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        desc.setText(R.string.OrangUtanMelayu);
                    }
                });
                eng.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        desc.setText(R.string.OrangUtan);
                    }
                });
            }
            else desc.setText("");

            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == -1) {
            if (requestCode == 3) {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                int dimension = Math.min(image.getWidth(), image.getHeight());
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                imageView.setImageBitmap(image);

                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                classifyImage(image);
            } else {
                Uri dat = data.getData();
                Bitmap image = null;
                try {
                    image = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), dat);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageView.setImageBitmap(image);

                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                classifyImage(image);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}