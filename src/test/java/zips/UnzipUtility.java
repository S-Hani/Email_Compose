package zips;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
 
/**
 * This utility extracts files and directories of any zip file to
 * a destination directory.
 *
 */
public class UnzipUtility {
    public static void unzip(String zipFilePath, String destDirectory) throws IOException {
    	ZipFile zipFile = new ZipFile(zipFilePath);
		Enumeration<?> enu = zipFile.entries();
		while (enu.hasMoreElements()) {
			ZipEntry zipEntry = (ZipEntry) enu.nextElement();

			String name = destDirectory+"/"+zipEntry.getName();
//			long size = zipEntry.getSize();
//			long compressedSize = zipEntry.getCompressedSize();

			File file = new File(name);
			if (name.endsWith("/")) {
				file.mkdirs();
				continue;
			}

			File parent = file.getParentFile();
			if (parent != null) {
				parent.mkdirs();
			}

			InputStream is = zipFile.getInputStream(zipEntry);
			FileOutputStream fos = new FileOutputStream(file);
			byte[] bytes = new byte[1024];
			int length;
			while ((length = is.read(bytes)) >= 0) {
				fos.write(bytes, 0, length);
			}
			is.close();
			fos.close();

		}
		zipFile.close();
    }
}
