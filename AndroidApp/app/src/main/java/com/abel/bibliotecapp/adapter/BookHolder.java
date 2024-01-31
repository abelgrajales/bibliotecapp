package com.abel.bibliotecapp.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abel.bibliotecapp.R;

public class BookHolder extends RecyclerView.ViewHolder {

    TextView name, editorial, autor, categoria, descripcion, cantidad;
    Button editar, borrar;

    public BookHolder(@NonNull View itemView){
        super(itemView);
        name = itemView.findViewById(R.id.bookNameItem);
        editorial = itemView.findViewById(R.id.bookEditorialItem);
        autor = itemView.findViewById(R.id.bookAutorItem);
        categoria = itemView.findViewById(R.id.bookCategoriaItem);
        descripcion = itemView.findViewById(R.id.bookDescItem);
        editar = itemView.findViewById(R.id.btnEditarItem);
        borrar = itemView.findViewById(R.id.btnBorrarItem);
        cantidad = itemView.findViewById(R.id.bookCantidadItem);
    }
}
