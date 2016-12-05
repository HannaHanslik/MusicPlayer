package com.example.hanna.musicplayer;

import android.content.Context;
import android.media.MediaScannerConnection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Hanna on 05.12.2016.
 */

public class PlayListSaver {
    private File file ;
    private Context context;
    private String filename = "playlist.txt";

    public PlayListSaver(Context context){
        file = new File(context.getExternalFilesDir(null),filename );
        this.context =context;

    }

    public String save(ArrayList<Song> playlist){
        try
        {
            if (!file.exists())
                file.createNewFile();
            // Adds a line to the trace file
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
            for (Song song : playlist) {
                writer.write(song.getUrl() + "|"+song.getTitle()+"|"+song.getPerformer()+"\n");
            }

            writer.close();
            MediaScannerConnection.scanFile(context, new String[] { file.toString() }, null, null);

        }
        catch (IOException e) {
            return e.getMessage();
        }
        return "";
    }

    public ArrayList<Song> getPlaylist(){
        String  thisLine = null;
        ArrayList<Song> songs = new ArrayList<>();
        try {
            // open input stream test.txt for reading purpose.
            BufferedReader br = new BufferedReader(new FileReader(filename));
            while ((thisLine = br.readLine()) != null) {
                String[] s = thisLine.toString().split("|");
                songs.add(new Song(s[0],s[1],s[2]));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return songs;
    }


}
