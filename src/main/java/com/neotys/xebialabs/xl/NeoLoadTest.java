package com.neotys.xebialabs.xl;


import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.neotys.nls.security.tools.PasswordEncoder;
import com.xebialabs.deployit.plugin.api.udm.ConfigurationItem;
import com.xebialabs.overthere.CmdLine;
import com.xebialabs.overthere.ConnectionOptions;
import com.xebialabs.overthere.OperatingSystemFamily;
import com.xebialabs.overthere.Overthere;
import com.xebialabs.overthere.OverthereConnection;
import com.xebialabs.overthere.OverthereFile;
import com.xebialabs.overthere.util.CapturingOverthereExecutionOutputHandler;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Arrays;

import static com.neotys.xebialabs.xl.NeoLoadTest.OperatingSystem.WINDOWS_RM;
import static com.neotys.xebialabs.xl.NeoLoadTest.OperatingSystem.WINDOWS_TELNET;
import static com.xebialabs.overthere.ConnectionOptions.ADDRESS;
import static com.xebialabs.overthere.ConnectionOptions.OPERATING_SYSTEM;
import static com.xebialabs.overthere.ConnectionOptions.PASSWORD;
import static com.xebialabs.overthere.ConnectionOptions.USERNAME;
import static com.xebialabs.overthere.OperatingSystemFamily.UNIX;
import static com.xebialabs.overthere.cifs.CifsConnectionType.TELNET;
import static com.xebialabs.overthere.cifs.CifsConnectionType.WINRM_NATIVE;
import static com.xebialabs.overthere.ssh.SshConnectionBuilder.CONNECTION_TYPE;
import static com.xebialabs.overthere.ssh.SshConnectionType.SCP;
import static com.xebialabs.overthere.ssh.SshConnectionType.SFTP;
import static com.xebialabs.overthere.util.CapturingOverthereExecutionOutputHandler.capturingHandler;

/**
 * Created by hrexed on 04/12/17.
 */
public class NeoLoadTest {

    private static final String WINDOWS_TELNET_LABEL = "Windows - Telnet";
    private static final String WINDOWS_RM_LABEL = "Windows - WinRM";
    private static final String LINUX_LABEL = "Linux";
    private static final String MAC_LABEL = "Mac OS";
    private static final String UNIX_SCP_HOME = "/home/";
    private static final String MAC_SCP_HOME = "/Users/";
    private static final String UNIX_FILE_SEPARATOR = "/";
    private static final String WINDOWS_FILE_SEPARATOR = "\\";
    private OperatingSystem operatingSystem;
    private String nlInstallationPath;
    private String nlProjectPath;
    private String nlScenarioName;
    private String nlHost;
    private String nlUsername;
    private String nlPassword;
    private boolean nlIsCollab;
    private String nlCollabUrl;
    private String nlCollabUsername;
    private String nlCollabProjectPath;
    private String nlCollabPassword;
    private String ntsUrl;
    private String ntsUsername;
    private String ntsPassword;
    private String nbHour;
    private String nbVu;
    private boolean nlIsNTS;
    private String ntsLicenseID;
    private String nlCollabProjectName;
    private String nlWebUrl;
    private String nlWebAPIToken;
    private String nlCloudUserName;
    private String nlCloudPassword;
    private boolean nlIsCloud;
    private boolean nlIsNlweb;
    private String nlTestDescription;
    private String releaseName;
    private String releaseId;
    private String cloudYml = null;
    private String variables = null;
    private boolean isVariableUsed ;
    private boolean isCloudUsed ;
    private OverthereConnection overthereConnection;
    private String windowsUserHomeDirectory;

    public NeoLoadTest(String host, String username, String password, String path, String OperatingSystemLabel,
                       String neoloadScenario, String localNeoLoadProject, String neoLoadTestDescription, String neoloadWebAPIToken,
                       String nlcollabprojectname, String collaborationProjectPath, String nbVu, String nbHour) {
        this.nlHost = host;
        this.nlUsername = username;
        this.nlPassword = password;
        this.nlInstallationPath = path;
        this.operatingSystem = OperatingSystem.fromName(OperatingSystemLabel);
        this.nlScenarioName = neoloadScenario;
        this.nlProjectPath = localNeoLoadProject;
        this.nlTestDescription = neoLoadTestDescription;
        this.nlWebAPIToken = neoloadWebAPIToken;
        this.nlCollabProjectPath = collaborationProjectPath;
        this.nlCollabProjectName = nlcollabprojectname;
        this.nbHour = nbHour;
        this.nbVu = nbVu;
    }

    public NeoLoadTest(ConfigurationItem remoteScript) {
        ConfigurationItem neoLoad = remoteScript.getProperty("NeoLoadPath");
        ConfigurationItem nts = remoteScript.getProperty("NeoLoadTeamServer");
        ConfigurationItem collab = remoteScript.getProperty("NeoLoadCollaboration");
        ConfigurationItem nlweb = remoteScript.getProperty("NeoLoadWebAPI");
        //----setting related to neoLoad---------------
        this.nlHost = neoLoad.getProperty("NL_Host");
        this.nlUsername = neoLoad.getProperty("username");
        this.nlPassword = neoLoad.getProperty("password");
        this.nlInstallationPath = neoLoad.getProperty("NL_Controller_Path");
        this.operatingSystem = neoLoad.getProperty("OS");
        //-----------------------------------------------

        //-----settings on the test execution--------------
        this.nlScenarioName = remoteScript.getProperty("NeoloadScenario");
        this.nlProjectPath = remoteScript.getProperty("LocalNeoLoadProject");
        this.nlTestDescription = remoteScript.getProperty("NeoLoadTestDescription");
        //--------------------------------------------------

        //-----NL Web settings-------------------------------------
        if (nlweb != null) {
            this.nlWebUrl = nlweb.getProperty("NL_WEB_URL");
            this.nlWebAPIToken = remoteScript.getProperty("NeoloadWebAPItoken");
            this.nlIsNlweb = true;
        } else
            this.nlIsNlweb = false;
        //---------------------------------------------------------

        //-----getting the information related to nts--------------
        if (nts != null) {
            this.nlIsNTS = true;
            this.ntsUrl = nts.getProperty("TeamServerHost");
            this.ntsUsername = nts.getProperty("username");
            this.ntsPassword = nts.getProperty("password");
            this.ntsLicenseID = nts.getProperty("licenceID");

        } else
            this.nlIsNTS = false;
        //---------------------------------------------------------

        //-----getting the information related to Collab--------------
        if (collab != null) {
            this.nlIsCollab = true;
            this.nlCollabUrl = collab.getProperty("Url");
            this.nlCollabUsername = collab.getProperty("username");
            this.nlCollabPassword = collab.getProperty("password");
            this.nlCollabProjectName = remoteScript.getProperty("CollabProjectName");
            this.nlCollabProjectPath = remoteScript.getProperty("CollaborationProjectPath");
        } else
            this.nlIsCollab = false;
        //---------------------------------------------------------

    }

    public void setReleaseInformation(String id, String name) {
        this.releaseId = id;
        this.releaseName = name;
    }

    public void addCloudYML(String yml) {
        if (yml != null) {
            this.cloudYml = yml;
            isCloudUsed = true;
        }
    }

    public void setVarialbe(String variable) {
        if (variable != null) {
            variables = variable;
            isVariableUsed = true;
        }
    }

    public void setNLCloud(String user, String pass) {
        this.nlCloudPassword = pass;
        this.nlCloudUserName = user;
        this.nlIsCloud = true;
    }

    public void setWebAPI(String webURL) {
        this.nlWebUrl = webURL;
        this.nlIsNlweb = true;
    }

    public void setNTS(String url, String username, String password, String licenseID) {
        this.nlIsNTS = true;
        this.ntsUrl = url;
        this.ntsUsername = username;
        this.ntsPassword = password;
        this.ntsLicenseID = licenseID;
    }

    public void setCollab(String url, String username, String password) {
        this.nlIsCollab = true;
        this.nlCollabUrl = url;
        this.nlCollabUsername = username;
        this.nlCollabPassword = password;
    }

    private String generateFileTempFolder() {
        if (operatingSystem == null) {
            return "";
        }
        switch (operatingSystem) {
            case MAC:
            return MAC_SCP_HOME + this.nlUsername + "/";
            case LINUX:
            if (this.nlUsername.equalsIgnoreCase("root")) {
                return UNIX_SCP_HOME + "/";
            } else {
                return UNIX_SCP_HOME + "/" + this.nlUsername + "/";
            }
            case WINDOWS_TELNET:
            case WINDOWS_RM:
            return retrieveWindowsTempFolder();
        }
        return "";
    }

    private String retrieveWindowsTempFolder() {
        if (windowsUserHomeDirectory == null) {
            CapturingOverthereExecutionOutputHandler stdoutEcho = capturingHandler();
            CapturingOverthereExecutionOutputHandler stderrEcho = capturingHandler();
            overthereConnection.execute(stdoutEcho, stderrEcho, CmdLine.build("echo", "%HOMEDRIVE%%HOMEPATH%"));
            windowsUserHomeDirectory = Iterables.getLast(Splitter.on("\n").split(stdoutEcho.getOutput()));
        }
        return windowsUserHomeDirectory;
    }

    private CmdLine generateCmdLine() throws GeneralSecurityException, UnsupportedEncodingException {

        CmdLine cmd = new CmdLine();
        String cmdLine = Paths.get(this.nlInstallationPath, "bin").toString();

        if (operatingSystem == WINDOWS_TELNET || operatingSystem == WINDOWS_RM) {
            cmdLine = Paths.get(cmdLine, "NeoLoadCmd.exe").toString();
        } else {
            cmdLine = Paths.get(cmdLine, "NeoLoadCmd").toString();
        }
        cmd.addRaw("\"" + replaceFileSeparator(cmdLine, operatingSystem) + "\"");

        if (nlIsCollab) {
            cmd.addRaw("-Collab " + "'" + this.nlCollabUrl + this.nlCollabProjectPath + "'");
            cmd.addRaw("-CollabLogin " + "'" + this.nlCollabUsername + ":" + PasswordEncoder.encode(this.nlCollabPassword) + "'");
            cmd.addRaw("-checkoutProject " + "'" + this.nlCollabProjectName + "'");
        }

        if (nlIsNTS) {
            cmd.addRaw("-NTS " + "'" + this.ntsUrl + "'");
            cmd.addRaw("-NTSCollabPath " + "'" + this.nlCollabProjectPath + "'");
            cmd.addRaw("-NTSLogin " + "'" + this.ntsUsername + ":" + PasswordEncoder.encode(this.ntsPassword) + "'");
            cmd.addRaw("-checkoutProject " + "'" + this.nlCollabProjectName + "'");
            cmd.addRaw("-publishTestResult ");
            cmd.addRaw("-leaseLicense " + "'" + this.ntsLicenseID + ":" + nbVu + ":" + nbHour + "'");
        }

        if (nlIsNlweb) {
            cmd.addRaw("-nlweb ");
            cmd.addRaw("-nlwebAPIURL " + "'" + this.nlWebUrl + "'");
            cmd.addRaw("-nlwebToken " + "'" + this.nlWebAPIToken + "'");
        }

        if (!nlIsCollab && !nlIsNTS) {
            cmd.addRaw("-project " + "'" + this.nlProjectPath + "'");
        }

        if (nlIsCloud) {
            cmd.addRaw("-NCPLogin '" + this.nlCloudUserName + ":" + PasswordEncoder.encode(this.nlCloudPassword) + "'");
        }

        if (isVariableUsed) {
            cmd.addRaw("-variables '" + this.variables + "'");
        }

        final String fileTempFolder = generateFileTempFolder();
        if (isCloudUsed) {
            cmd.addRaw("-loadGenerators '" + replaceFileSeparator(Paths.get(fileTempFolder, "tmp.yaml'").toString(), operatingSystem));
        }

        cmd.addRaw("-SLAJUnitMapping 'pass'");
        cmd.addRaw("-SLAJUnitResults '" + replaceFileSeparator(Paths.get(fileTempFolder, "junit.xml'").toString(), operatingSystem));

        if (this.releaseName != null) {
            cmd.addRaw("-description '" + this.nlTestDescription + "_" + this.releaseName + "_" + this.releaseId + "'");
        } else {
            cmd.addRaw("-description '" + this.nlTestDescription + "'");
        }

        cmd.addRaw("-report '" + replaceFileSeparator(Paths.get(fileTempFolder, "report.xml").toString(), operatingSystem)
                + "," + replaceFileSeparator(Paths.get(fileTempFolder, "report.pdf").toString(), operatingSystem) + "'");
        cmd.addRaw("-launch " + "'" + this.nlScenarioName + "'");
        cmd.addRaw("-noGUI");

        return cmd;
    }

    private String replaceFileSeparator(final String filePath, final OperatingSystem operatingSystem) {
        switch (operatingSystem) {
            case LINUX:
            case MAC:
                return filePath.replaceAll("\\\\", UNIX_FILE_SEPARATOR);
            case WINDOWS_TELNET:
            case WINDOWS_RM:
                return filePath.replaceAll("/", WINDOWS_FILE_SEPARATOR);
        }
        throw new IllegalArgumentException("Can not support this operation system");
    }

    private byte[] getFileByteArray(OverthereFile file, CmdResponse response) {
        byte[] bytes = null;
        if (file.exists()) {
            try (InputStream i = file.getInputStream()){
                bytes = IOUtils.toByteArray(i);
            } catch (IOException e) {
                response.addToErr("Can not read overthereFile: " + file.getPath()+ "\n");
            }
        }
        return bytes;
    }

    private OverthereConnection getConnection() {
        if (overthereConnection == null) {
            ConnectionOptions options = new ConnectionOptions();
            options.set(ADDRESS, this.nlHost);
            options.set(USERNAME, this.nlUsername);
            options.set(PASSWORD, this.nlPassword);

            switch (operatingSystem) {
                case MAC:
                    options.set(OPERATING_SYSTEM, UNIX);
                    options.set(CONNECTION_TYPE, SFTP);
                    overthereConnection = Overthere.getConnection("ssh", options);
                    break;
                case LINUX:
                    options.set(OPERATING_SYSTEM, UNIX);
                    options.set(CONNECTION_TYPE, SCP);
                    overthereConnection = Overthere.getConnection("ssh", options);
                    break;
                case WINDOWS_TELNET:
                    options.set(OPERATING_SYSTEM, OperatingSystemFamily.WINDOWS);
                    options.set(CONNECTION_TYPE, TELNET);
                    overthereConnection = Overthere.getConnection("cifs", options);
                    break;
                case WINDOWS_RM:
                    options.set(OPERATING_SYSTEM, OperatingSystemFamily.WINDOWS);
                    options.set(CONNECTION_TYPE, WINRM_NATIVE);
                    options.set("pathShareMappings", ImmutableMap.of());
                    overthereConnection = Overthere.getConnection("cifs", options);
                    break;
            }

            overthereConnection.setWorkingDirectory(overthereConnection.getFile(generateFileTempFolder()));
        }
        return overthereConnection;
    }

    private StringBuilder createRemoteFile(String content) throws IOException {
        StringBuilder result = new StringBuilder();

        OverthereFile overthereFile = overthereConnection.getFile(generateFileTempFolder() + "tmp.yaml");
        try (OutputStream w = overthereFile.getOutputStream()) {
            result.append(content);
            w.write(content.getBytes());
        }
        return result;
    }

    public CmdResponse execute() {
        CapturingOverthereExecutionOutputHandler stdout = capturingHandler();
        CapturingOverthereExecutionOutputHandler stderr = capturingHandler();
        CmdResponse response = null;
        StringBuilder log = null;
        StringBuilder comment = new StringBuilder();
        try {

            comment.append("Connection to remote Controller\n");
            overthereConnection = getConnection();

            if (overthereConnection != null) {
                comment.append("Connection Done.....\n");

                CmdLine script = generateCmdLine();

                if (cloudYml != null) {
                    comment.append("Setting Cloud Session to the controller.....\n");
                    log = createRemoteFile(cloudYml);
                }

                comment.append("Launching test.....\n").append(script);

                int rc = overthereConnection.execute(stdout, stderr, script);
                comment.append("Test finished.....\n");

                response = new CmdResponse(rc, stdout.getOutput(), stderr.getOutput());

                if (log != null) {
                    response.addToOut(log);
                }

                final String tempFolder = generateFileTempFolder();

                OverthereFile reportDTDFile = overthereConnection.getFile(replaceFileSeparator(Paths.get(tempFolder, "report.dtd").toString(), operatingSystem));
                if (reportDTDFile != null && reportDTDFile.exists()) {
                    response.setReportDTDBytes(getFileByteArray(reportDTDFile, response));
                    reportDTDFile.delete();
                } else {
                    response.addToErr("Can not find report file: report.dtd.\n");
                }

                OverthereFile reportXMLFile = overthereConnection.getFile(replaceFileSeparator(Paths.get(tempFolder, "report.xml").toString(), operatingSystem));
                if (reportXMLFile != null && reportXMLFile.exists()) {
                    response.setReportXMLBytes(getFileByteArray(reportXMLFile, response));
                    reportXMLFile.delete();
                } else {
                    response.addToErr("Can not find report file: report.xml.\n");
                }

                OverthereFile reportPDFFile = overthereConnection.getFile(replaceFileSeparator(Paths.get(tempFolder, "report.pdf").toString(), operatingSystem));
                if (reportPDFFile != null && reportPDFFile.exists()) {
                    response.setPDFBytes(getFileByteArray(reportPDFFile, response));
                    reportPDFFile.delete();
                } else {
                    response.addToErr("Can not find report file: report.pdf.\n");
                }

                OverthereFile junitXMLFile = overthereConnection.getFile(replaceFileSeparator(Paths.get(tempFolder, "junit.xml").toString(), operatingSystem));
                if (junitXMLFile != null && junitXMLFile.exists()) {
                    getJunitData(junitXMLFile.getInputStream(), response);
                    response.setJunitXMLBytes(getFileByteArray(junitXMLFile, response));
                    junitXMLFile.delete();
                } else {
                    response.addToErr("Can not find report file: junit.xml.\n");
                }

                if (isCloudUsed) {
                    OverthereFile tmpYamlFile = overthereConnection.getFile(replaceFileSeparator(Paths.get(tempFolder, "tmp.yaml").toString(), operatingSystem));
                    if (tmpYamlFile.exists()) {
                        tmpYamlFile.delete();
                    }
                }
            }
        } catch (Exception e) {
            if (response == null) {
                response = new CmdResponse();
            }
            StringWriter stacktrace = new StringWriter();
            e.printStackTrace(new PrintWriter(stacktrace, true));
            stderr.handleLine(stacktrace.toString());
            response.addToErr(stacktrace.toString());
        } finally {
            if (response == null) {
                response = new CmdResponse();
            }
            response.setComment(comment);
            if (overthereConnection != null) {
                overthereConnection.close();
            }
        }
        return response;
    }

    private void getData(InputStream inputStream, CmdResponse response) {
        try {
            // use the factory to create a document builder
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(inputStream);
            String hit = retrieveData(doc, "avg_hits/s");
            String responseTime = retrieveData(doc, "avg_reqresponsetime");
            String error = retrieveData(doc, "total_errors");

            response.addStat(responseTime, error, hit);
        } catch (Exception ex) {
            response.addToErr("Failed to get data.\n" );
        }
    }

    private String retrieveData(Document doc, String key) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        String result = null;
        NodeList nodes = (NodeList) xPath.evaluate("/report/summary/statistics/statistic[@name='" + key + "']", doc.getDocumentElement(), XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); ++i) {
            Element e = (Element) nodes.item(i);
            result = e.getAttribute("value");
        }
        return result;
    }

    private void getJunitData(InputStream input, CmdResponse response) {
        StringBuilder out = new StringBuilder();
        StringBuilder error = new StringBuilder();
        try {
            // use the factory to create a documentbuilder
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(input);
            int total = getStats(doc, "/resource/testsuite/testcase");
            int success = getStats(doc, "/resource/testsuite/testcase/sucess");
            int failure = getStats(doc, "/resource/testsuite/testcase/failure");
            out.append("Results :\n");
            out.append("\t").append(success).append(" SLAS were sucessful out of ").append(total);

            if (failure > 0) {
                error.append(failure).append(" SlA were in error out of ").append(total).append("\n");
                getError(doc, error);
                response.addToErr(error.toString());
            }
        } catch (Exception ex) {
            response.addToErr(Arrays.toString(ex.getStackTrace()));
        } finally {
            response.addToOut(out);
            response.addToOut(error);
        }
    }

    private int getStats(Document doc, String query) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList nodes = (NodeList) xPath.evaluate(query, doc.getDocumentElement(), XPathConstants.NODESET);
        return nodes.getLength();
    }

    private  void getError(Document doc, StringBuilder output) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList nodes = (NodeList) xPath.evaluate("/resource/testsuite/testcase/failure", doc.getDocumentElement(), XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); ++i) {
            Node e = nodes.item(i);
            output.append("Failure n° ").append(i + 1).append("\n");
            output.append("\t").append(e.getFirstChild().getNodeValue()).append("\n");
        }
    }

    public class CmdResponse {
        public int responseCode;
        public String stdout = "";
        public String stderr = "";
        private String responseTime;
        private String error;
        private String hits;

        public byte[] reportXMLBytes;
        public byte[] reportDTDBytes;
        public byte[] junitXMLBytes;
        public byte[] reportPDFBytes;
        public String comment = null;

        CmdResponse(int responseCode, String stdout, String stderr) {
            this.responseCode = responseCode;
            this.stdout = stdout;
            this.stderr = stderr;
        }

        CmdResponse() {

        }

        private void setComment(StringBuilder com) {
            this.comment = com.toString();
        }

        void addStat(String responseTime, String error, String his) {
            this.responseTime = responseTime;
            this.error = error;
            this.hits = his;
        }

        void addToOut(StringBuilder s) {
            this.stdout += "\n" + s.toString();
        }

        void addToErr(String s) {
            this.stderr += "\n" + s;
            this.responseCode = 1;
        }

        void setReportXMLBytes(byte[] reportXMLBytes) {
            this.reportXMLBytes = reportXMLBytes;
        }

        void setReportDTDBytes(byte[] reportDTDBytes) {
            this.reportDTDBytes = reportDTDBytes;
        }

        void setPDFBytes(byte[] reportPDFBytes) {
            this.reportPDFBytes = reportPDFBytes;
        }

        void setJunitXMLBytes(byte[] junitXMLBytes) {
            this.junitXMLBytes = junitXMLBytes;
        }
    }

    enum OperatingSystem {

        WINDOWS_TELNET(WINDOWS_TELNET_LABEL),
        WINDOWS_RM(WINDOWS_RM_LABEL),
        LINUX(LINUX_LABEL),
        MAC(MAC_LABEL);

        final String name;

        OperatingSystem(final String name) {
            this.name = name;
        }

        public static OperatingSystem fromName(String osLabel) {
            switch (osLabel) {
                case WINDOWS_TELNET_LABEL:
                    return WINDOWS_TELNET;
                case WINDOWS_RM_LABEL:
                    return WINDOWS_RM;
                case LINUX_LABEL:
                    return LINUX;
                case MAC_LABEL:
                    return MAC;
            }
            throw new IllegalArgumentException("Can not support this OS : " + osLabel);
        }
    }
}
