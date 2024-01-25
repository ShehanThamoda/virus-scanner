package se.sciens;

public class UtilConstant {
    public static final String INITIAL_FOLDER_PATH = "/home/devops/pictures/initial";
    public static final String SCAN_FOLDER_PATH = "/home/devops/pictures/scan/";
    public static final String THREAT_DEFAULT_FOLDER_PATH = "/home/devops/pictures/default/threat.jpg";
    public static final String JOB_NAME = "virusScanningJob";
    public static final String GROUP_NAME = "virusScanningJobGroup";
    public static final String TRIGGER_NAME = "triggerForGetFiles";
    public static final String CRON_JOB = "0 * * ? * *";  //every minute
    public static final String COMMAND_CLAMSCAN = "clamscan";

}
