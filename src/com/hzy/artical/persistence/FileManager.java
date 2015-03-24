package com.hzy.artical.persistence;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.os.Environment;

public class FileManager {

	private static FileManager fm = new FileManager();

	private FileManager() {
	}

	public static FileManager getFileManager() {
		return fm;
	}

	static final int READ_BLOCK_SIZE = 100;

	public boolean IsExternalStorageAvailableAndWriteable() {
		boolean externalStorageAvailable = false;
		boolean externalStorageWriteable = false;
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// ---you can read and write the media---
			externalStorageAvailable = externalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// ---you can only read the media---
			externalStorageAvailable = true;
			externalStorageWriteable = false;
		} else {
			// ---you cannot read nor write the media---
			externalStorageAvailable = externalStorageWriteable = false;
		}
		return externalStorageAvailable && externalStorageWriteable;
	}

	public boolean writeFile(Context ctx, InputStream is, String fileName) {
		if (ctx == null || is == null || fileName == null) {
			return false;
		}

		if (IsExternalStorageAvailableAndWriteable()) {
			File extStorage = ctx.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
			try {
				FileOutputStream fos = new FileOutputStream(extStorage.getAbsolutePath() + "/" + fileName);
				BufferedOutputStream bos = new BufferedOutputStream(fos);

				byte[] buffer = new byte[READ_BLOCK_SIZE];
				int len = -1;
				while ((len = is.read(buffer)) != -1) {
					bos.write(buffer, 0, len);
					bos.flush();
				}
				bos.close();
				is.close();
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

}
