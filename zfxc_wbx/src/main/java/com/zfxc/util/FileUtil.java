package com.zfxc.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.widget.Toast;

public class FileUtil {
	public static void save(Context context,byte bs[])
    {
        try {
            FileOutputStream outStream=context.openFileOutput("aaa.jpg",Context.MODE_WORLD_READABLE);
            outStream.write(bs);
            outStream.close();
            Toast.makeText(context,"Saved",Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            return;
        }
        catch (IOException e){
            return ;
        }
    }

 
}
