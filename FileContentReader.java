package se.sciens;

import com.sun.jdi.InternalException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileContentReader implements Job {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        FileContentReader fileContentReader = new FileContentReader();
        fileContentReader.readAllImages();
    }

    private void readAllImages() {
        logger.info("START: Read all initial images");
        Path folderPath = Paths.get(UtilConstant.INITIAL_FOLDER_PATH);
        List<String> fileNames = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(folderPath)) {
            for (Path path : directoryStream) {
                fileNames.add(path.toString());
            }
        } catch (IOException ex) {
            logger.error("Error reading files");
            ex.printStackTrace();
        }
        if (fileNames.size() > 0) {
            fileNames.stream().forEach(file -> scanVirus(file));
        } else {
            logger.info("No files in the initial folder to scan");
        }
        logger.info("END: Read all initial images");
    }

    private void scanVirus(String filePath) {
        logger.info("Start to scan. file:" + filePath);
        try {
            String[] command = {UtilConstant.COMMAND_CLAMSCAN, filePath};

            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true);
            System.out.println("scan starting..");
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            int exitCode = process.waitFor();
            logger.info("exitCode:" + exitCode);

            if (exitCode == 0) {
                logger.info("File is clean.");
                try (InputStream inputStream = new FileInputStream(filePath)) {
                    String scanPath = UtilConstant.SCAN_FOLDER_PATH + new File(filePath).getName();
                    logger.info("Scan Path:" + scanPath);
                    uploadImageToFile(scanPath,inputStream);
                    logger.info("File move to new folder successfully!");
                    deleteFileFromFolder(filePath);
                } catch (IOException ioe) {
                    throw new InternalException("ExceptionConstant.FILE_SAVE_FAIL");
                }
            } else if (exitCode == 1) {
                logger.info("Virus found in the file. File path:"+filePath);
                deleteFileFromFolder(filePath);
                //get default image
                String sourceFile = UtilConstant.THREAT_DEFAULT_FOLDER_PATH;
                InputStream inputStream = new FileInputStream(sourceFile);
                String scanPath = UtilConstant.SCAN_FOLDER_PATH + new File(filePath).getName();
                uploadImageToFile(scanPath,inputStream);
                logger.info("Default threat file move to new folder successfully!");
            } else {
                logger.info("Error scanning the file.");
            }
            System.out.println("scan end.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void uploadImageToFile(String filePath, InputStream inputStream) throws IOException{
        Path newPath = Path.of(filePath);
        Files.copy(inputStream, newPath, StandardCopyOption.REPLACE_EXISTING);
    }

    private void deleteFileFromFolder (String filePath) throws IOException{
        Files.delete(Path.of(filePath));
        logger.info("File deleted from initial folder successfully");
    }


}
