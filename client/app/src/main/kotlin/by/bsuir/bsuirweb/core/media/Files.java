package by.bsuir.bsuirweb.core.media;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

public final class Files
{
    private Files() {
    }

    private static final String AUTHORITY = "by.bsuir.bsuirweb.files";

    public static String getMime(File file) {
        String fileExtension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
        String fileMime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);

        if (!TextUtils.isEmpty(fileMime)) {
            return fileMime;
        } else {
            return "image/jpeg";
        }
    }

    public static Uri getUri(Context context, File file) {
        return FileProvider.getUriForFile(context, Files.AUTHORITY, file);
    }

    public static void copy(InputStream sourceStream, File destinationFile) {
        try {
            BufferedSource bufferedSource = Okio.buffer(Okio.source(sourceStream));
            BufferedSink bufferedTarget = Okio.buffer(Okio.sink(destinationFile));

            bufferedTarget.writeAll(bufferedSource);
            bufferedTarget.flush();

            bufferedSource.close();
            bufferedTarget.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File createPhotoFile(Context context) {
        try {
            File fileDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File file = File.createTempFile(Ids.generateString(), ".jpg", fileDirectory);

            file.mkdirs();

            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}