package com.regexp.practise.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileSystemHelper {

	private static final String osCommand = "cmd /c dir /b";

	/**
	 * Writes given content to the given file. The file must be file with full
	 * path.
	 * 
	 * @param fileName
	 * @param content
	 */
	public void writeFile(final String fileName, final String content) {
		try {
			final FileOutputStream fos = new FileOutputStream(fileName, false);
			final OutputStreamWriter writer = new OutputStreamWriter(fos,
					"UTF-8");
			writer.write(content);
			writer.close();
			fos.close();
		} catch (final IOException ex) {

		}
	}

	/**
	 * Reads the file from the given full path.
	 * 
	 * @param filePath
	 * @return
	 */
	public String readFile(final String filePath) {
		final StringBuffer buffer = new StringBuffer();
		try {
			final FileInputStream fis = new FileInputStream(filePath);
			final InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			final Reader reader = new BufferedReader(isr);
			int character;
			while ((character = reader.read()) > -1) {
				buffer.append((char) character);
			}
			reader.close();
			isr.close();
			fis.close();
			return buffer.toString();
		} catch (final IOException e) {
			
		}
		return filePath;
	}

	/**
	 * Lists the specified number of files from the given directory location.
	 * 
	 * @param dir
	 * @param upperLimit
	 * @return
	 */
	public List<String> listFiles(final File dir, final int upperLimit) {
		final List<String> files = new ArrayList<String>();
		try {
			final Process process = Runtime.getRuntime().exec(
					osCommand + " " + dir.getAbsolutePath());
			final InputStream inputStream = process.getInputStream();
			int counter = 0;
			int character;
			final StringBuilder buffer = new StringBuilder();
			while (((character = inputStream.read()) != -1)
					&& counter < upperLimit) {
				if (character == '\n') {
					files.add(buffer.toString().replaceAll("(\\r|\\n)", ""));
					buffer.setLength(0);
					counter++;
				} else {
					buffer.append((char) character);
				}
			}
			if (buffer.length() > 0) {
				files.add(buffer.toString().replaceAll("(\\r|\\n)", ""));
			}
		} catch (final IOException e) {
		
		}
		return files;
	}

	/**
	 * Lists the files from the given directory location.
	 * 
	 * @param dir
	 * @return
	 */
	public List<String> listFiles(final File dir) {
		final List<String> files = new ArrayList<String>();
		try {
			final Process process = Runtime.getRuntime().exec(
					osCommand + " " + dir.getAbsolutePath());
			final InputStream inputStream = process.getInputStream();

			int character;
			final StringBuilder buffer = new StringBuilder();
			while (((character = inputStream.read()) != -1)) {
				if (character == '\n') {
					files.add(buffer.toString().replaceAll("(\\r|\\n)", ""));
					buffer.setLength(0);
				} else {
					buffer.append((char) character);
				}
			}
			if (buffer.length() > 0) {
				files.add(buffer.toString().replaceAll("(\\r|\\n)", ""));
			}
		} catch (final IOException e) {
			
		}
		return files;
	}

	/**
	 * Moves the given file from source to target directory location.
	 * 
	 * @param sourceDir
	 * @param targetDir
	 * @param fileName
	 * @throws Exception 
	 */
	public void move(final String sourceDir, final String targetDir,
			final String fileName) throws Exception {
	
		move(targetDir, sourceDir + File.separator + fileName);
	}

	/**
	 * Moves the files to target location. Here the file is with full path.
	 * 
	 * @param targetDir
	 * @param fileName
	 * @throws Exception 
	 */
	public void move(final String targetDir, final String fileName) throws Exception {

		

		final File sourceFile = new File(fileName);

		final boolean success = sourceFile.renameTo(new File(targetDir,
				sourceFile.getName()));
		if (!success) {
			throw new Exception("file move failed");
		}
	}

	public void delete(final String path) {
	
		final File sourceFile = new File(path);
		sourceFile.delete();
	}

	/**
	 * Creates local store in EC2 instance.
	 * 
	 * @return
	 */
	public String createLocalStore(final String ec2Path) {
		final File dir = new File(preparePath(ec2Path, UUID.randomUUID()
				.toString()));
		dir.mkdir();

		return dir.getAbsolutePath();
	}

	/**
	 * Prepares the file path
	 * 
	 * @param pathParams
	 * @return
	 */
	public String preparePath(final String... pathParams) {
		final StringBuilder path = new StringBuilder();
		boolean first = true;
		for (final String pathParam : pathParams) {
			if (first) {
				first = false;
			} else {
				path.append(File.separator);
			}

			path.append(pathParam);
		}

		return path.toString();
	}

	/**
	 * Unzips the stream
	 * 
	 * @param zipFileStream
	 * @param targetDir
	 * @return
	 * @throws Exception 
	 */
	public String unzipStream(final ZipInputStream zipFileStream,
			final String targetDir, final String encoding) throws Exception {

		final byte[] buffer = new byte[1024];
		final StringWriter manifestWriter = new StringWriter();
		boolean manifest = false;
		try {
			// get the zipped file list entry
			ZipEntry zippedEntry = zipFileStream.getNextEntry();
			while (zippedEntry != null) {
				final String fileName = zippedEntry.getName();
				if (fileName.contains("manifest")) {
					manifest = true;
				}
				final File unzippedFile = new File(targetDir + File.separator
						+ fileName);
			

				if (!zippedEntry.isDirectory()) {
					new File(unzippedFile.getParent()).mkdirs();
					final FileOutputStream fileOutputStream = new FileOutputStream(
							unzippedFile);
					
					int length;
					while ((length = zipFileStream.read(buffer)) > 0) {
						fileOutputStream.write(buffer, 0, length);
						if (manifest) {
							manifestWriter.write(new String(buffer, encoding),
									0, length);
						}
					}
					if (manifest) {
						manifest = false;
					}
					fileOutputStream.close();
				}
				zippedEntry = zipFileStream.getNextEntry();
			}
			
		} catch (final IOException ex) {
			
			throw new Exception("error in unzipping ", ex);
		} finally {
			try {
				if (zipFileStream != null) {
					zipFileStream.closeEntry();
					zipFileStream.close();
				}
			} catch (final IOException ex) {
				
				throw new Exception("error closing zip stream ", ex);
			}
			if (manifestWriter != null) {
				try {
					manifestWriter.close();
				} catch (final IOException ex) {
					
					throw new Exception("error closing stream writer", ex);
				}
			}
		}

		return manifestWriter.toString();
	}

	/**
	 * Unzips the stream on local file system
	 * 
	 * @param zipFileStream
	 * @param targetDir
	 * @param encoding
	 * @param maintainDirectory
	 * @throws Exception 
	 */
	public List<String> unzipArchive(final ZipInputStream zipFileStream,
			final String targetDir, final String encoding,
			final boolean maintainDirectory) throws Exception {

		final byte[] buffer = new byte[1024];
		final List<String> filePaths = new ArrayList<String>();

		try {
			// get the zipped file list entry
			ZipEntry zippedEntry = zipFileStream.getNextEntry();

			while (zippedEntry != null) {
				String[] fileNames;
				String filename;

				if (maintainDirectory) {
					filename = zippedEntry.getName();
				} else {
					fileNames = zippedEntry.getName().split("/");
					filename = fileNames[fileNames.length - 1];
				}

				final File unzippedFile = new File(targetDir + File.separator
						+ filename);

				

				if (!zippedEntry.isDirectory()) {
					if (maintainDirectory) {
						new File(unzippedFile.getParent()).mkdirs();
					}

					final FileOutputStream fileOutputStream = new FileOutputStream(
							unzippedFile);
					
					filePaths.add(unzippedFile.getAbsolutePath());
					int length;
					while ((length = zipFileStream.read(buffer)) > 0) {
						fileOutputStream.write(buffer, 0, length);
					}
					fileOutputStream.close();
				}
				zippedEntry = zipFileStream.getNextEntry();
			}

		

		} catch (final IOException ex) {
			
			throw new Exception("error in unzipping ", ex);
		} finally {
			try {
				if (zipFileStream != null) {
					zipFileStream.closeEntry();
					zipFileStream.close();
				}
			} catch (final IOException ex) {
				
				throw new Exception("error closing zip stream ", ex);
			}
		}
		return filePaths;
	}

	/**
	 * Extracts the extension from a filename
	 * 
	 * @param pathName
	 * @return
	 */
	public String extractExtension(final String pathName) {

		String extension = null;
		final String[] tokens = pathName.split("\\.");
		if (tokens.length > 0) {
			extension = tokens[tokens.length - 1];
		}

		return extension;
	}

}
