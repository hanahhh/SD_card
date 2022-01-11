package com.example.week15;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {

    Context context;
    File[] filesAndFolders;

    public FileAdapter(Context context, File[] filesAndFolders) {
        this.context = context;
        this.filesAndFolders = filesAndFolders;
    }


    @NonNull
    @Override
    public FileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileAdapter.ViewHolder holder, int position) {
        Log.v("TAG TEST", filesAndFolders[position].getName());
        try {
            File selectedFile = filesAndFolders[position];
            Log.v("TAG", selectedFile.getName() + "test");
            holder.fileName.setText(selectedFile.getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(selectedFile.isDirectory()){
                        Intent intent = new Intent(context, MainActivity.class);
                        String path = selectedFile.getAbsolutePath();
                        intent.putExtra("path",path);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }else{
                        try {
                            Intent intent = new Intent();
                            intent.setAction(android.content.Intent.ACTION_VIEW);
                            String type = "txt/*";
                            intent.setDataAndType(Uri.parse(selectedFile.getAbsolutePath()), type);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }catch (Exception e){
                            Toast.makeText(context.getApplicationContext(),"Cannot open the file",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        } catch (Exception e) {

        }



    }

    @Override
    public int getItemCount() {
        return filesAndFolders.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageFile;
        TextView fileName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageFile = itemView.findViewById(R.id.image);
            fileName = itemView.findViewById(R.id.fileName);
        }
    }
}
