package com.example.testimageupload.utiidades;

import android.net.Uri;
import android.widget.ImageView;

import java.io.File;

public class InterfazVisualFiles {

    public static void cargarImagenFromDirectory(ImageView imageView, File downloaderFolder,String fileName){

        File file = new File(downloaderFolder,fileName);

        if (file.exists()) {
            // Convertir File a Uri
            Uri imageUri = Uri.fromFile(file);

            // Obtener el ImageView y mostrar la imagen

            imageView.setImageURI(imageUri);
        }
    }
}
