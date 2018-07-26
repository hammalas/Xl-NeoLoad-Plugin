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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;

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

    private static final String SCRIPT_NAME = "NeoLoad-Test";
    private static final String LINUX = "Linux";
    private static final String WINDOWS = "WINDOWS";
    private static final String WINDOWSRM = "WINDOWSRM";
    private static final String MACOS = "MacOs";
    private String windowsUserHomeDirectory;
    private static final String UNixScpHome = "/home/";
    private static final String MacScpHome = "/Users/";
    private ConnectionOptions options;
    private String OS;
    private String NLInstallationPath;
    private String NLProjectPath;
    private String NLScenarioName;
    private String NLHost;
    private String NLUsername;
    private String NLPassword;
    private boolean NLIsCollab;
    private String NLCollabUrl;
    private String NLCollabUsername;
    private String NLCollabProjectPath;
    private String NLCollabPassword;
    private String NTSUrl;
    private String NTSUsername;
    private String NTSPassword;
    private String Nbhour;
    private String NbVU;
    private boolean NLIsNTS;
    private String NTSLicenseID;
    private String NLCollabProjectName;
    private String NLWEBURL;
    private String NLWEBAPIToken;
    private String NLCloudUsername;
    private String NLCloudPassword;
    private boolean NLIsCloud;
    private boolean NlISNLWeb;
    private String NLTestDescription;
    private final static String SPACE = " ";
    private String ReleaseName;
    private String ReleaseId;
    private String CloudYML = null;
    private String Variables = null;
    private boolean IsVariableUsed = false;
    private boolean IsCloudUsed = false;
    private OverthereConnection overthereConnection;

    public NeoLoadTest(String Host, String Username, String password, String path, String Os, String NeoloadScenario, String LocalNeoLoadProject, String NeoLoadTestDescription, String NeoloadWebAPItoken, String NLCollabProjectName, String CollaborationProjectPath, String nbVU, String nbhour) {

        this.NLHost = Host;
        this.NLUsername = Username;
        this.NLPassword = password;
        this.NLInstallationPath = path;
        this.OS = Os;
        this.NLScenarioName = NeoloadScenario;
        this.NLProjectPath = LocalNeoLoadProject;
        this.NLTestDescription = NeoLoadTestDescription;
        this.NLWEBAPIToken = NeoloadWebAPItoken;
        this.NLCollabProjectPath = CollaborationProjectPath;
        this.NLCollabProjectName = NLCollabProjectName;
        this.NLIsCollab = false;
        this.NLIsNTS = false;
        this.NlISNLWeb = false;
        this.NLIsCloud = false;
        this.Nbhour = nbhour;
        this.NbVU = nbVU;
        options = new ConnectionOptions();
    }

    public void SetReleaseInformation(String id, String name) {
        this.ReleaseId = id;
        this.ReleaseName = name;
    }

    public void AddCloudYML(String yml) {
        if (yml != null) {
            this.CloudYML = yml;
            IsCloudUsed = true;
        }
    }

    public void setVarialbe(String Variable) {
        if (Variable != null) {
            Variables = Variable;
            IsVariableUsed = true;
        }
    }

    public void SetNLCloud(String User, String pass) {
        this.NLCloudPassword = pass;
        this.NLCloudUsername = User;
        this.NLIsCloud = true;
    }

    public void SetWebAPI(String WebURL) {
        this.NLWEBURL = WebURL;
        this.NlISNLWeb = true;
    }

    public void SetNTS(String url, String username, String password, String licenseID) {
        this.NLIsNTS = true;
        this.NTSUrl = url;
        this.NTSUsername = username;
        this.NTSPassword = password;
        this.NTSLicenseID = licenseID;
    }

    public void SetCollab(String url, String username, String password) {
        this.NLIsCollab = true;
        this.NLCollabUrl = url;
        this.NLCollabUsername = username;
        this.NLCollabPassword = password;
    }

    private String GenerateFileTempFolder() {
        if (this.OS.equalsIgnoreCase(WINDOWS) || this.OS.equalsIgnoreCase(WINDOWSRM)) {
            return retrieveWindowsTempFolder();
        } else {
            if (this.OS.equalsIgnoreCase(LINUX)) {
                if (this.NLUsername.equalsIgnoreCase("root"))
                    return UNixScpHome + "/";
                else
                    return UNixScpHome + "/" + this.NLUsername + "/";
            } else {
                if (this.OS.equalsIgnoreCase(MACOS))
                    return MacScpHome + this.NLUsername + "/";
                else
                    return null;
            }
        }
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

    public NeoLoadTest(ConfigurationItem remoteScript) {
        options = new ConnectionOptions();

        ConfigurationItem NeoLoad = remoteScript.getProperty("NeoLoadPath");
        ConfigurationItem NTS = remoteScript.getProperty("NeoLoadTeamServer");
        ConfigurationItem COllab = remoteScript.getProperty("NeoLoadCollaboration");
        ConfigurationItem NLWeb = remoteScript.getProperty("NeoLoadWebAPI");
        //----setting related to NeoLoad---------------
        this.NLHost = NeoLoad.getProperty("NL_Host");
        this.NLUsername = NeoLoad.getProperty("username");
        this.NLPassword = NeoLoad.getProperty("password");
        this.NLInstallationPath = NeoLoad.getProperty("NL_Controller_Path");
        this.OS = NeoLoad.getProperty("OS");
        //-----------------------------------------------

        //-----settings on the test execution--------------
        this.NLScenarioName = remoteScript.getProperty("NeoloadScenario");
        this.NLProjectPath = remoteScript.getProperty("LocalNeoLoadProject");
        this.NLTestDescription = remoteScript.getProperty("NeoLoadTestDescription");
        //--------------------------------------------------

        //-----NL Web settings-------------------------------------
        if (NLWeb != null) {
            this.NLWEBURL = NLWeb.getProperty("NL_WEB_URL");
            this.NLWEBAPIToken = remoteScript.getProperty("NeoloadWebAPItoken");
            this.NlISNLWeb = true;
        } else
            this.NlISNLWeb = false;
        //---------------------------------------------------------

        //-----getting the information related to NTS--------------
        if (NTS != null) {
            this.NLIsNTS = true;
            this.NTSUrl = NTS.getProperty("TeamServerHost");
            this.NTSUsername = NTS.getProperty("username");
            this.NTSPassword = NTS.getProperty("password");
            this.NTSLicenseID = NTS.getProperty("licenceID");

        } else
            this.NLIsNTS = false;
        //---------------------------------------------------------

        //-----getting the information related to Collab--------------
        if (COllab != null) {
            this.NLIsCollab = true;
            this.NLCollabUrl = COllab.getProperty("Url");
            this.NLCollabUsername = COllab.getProperty("username");
            this.NLCollabPassword = COllab.getProperty("password");
            this.NLCollabProjectName = remoteScript.getProperty("CollabProjectName");
            this.NLCollabProjectPath = remoteScript.getProperty("CollaborationProjectPath");
        } else
            this.NLIsCollab = false;
        //---------------------------------------------------------

    }

    private CmdLine GenerateCmdLine() throws GeneralSecurityException, UnsupportedEncodingException {

        String CMDline;
        CmdLine cmd = new CmdLine();

        CMDline = this.NLInstallationPath + "/bin";
        if (OS.equalsIgnoreCase(WINDOWS) || OS.equalsIgnoreCase(WINDOWSRM)) {
            CMDline += "/NeoLoadCmd.exe";
        } else {
            CMDline += "/NeoLoadCmd";
        }
        cmd.addRaw("\"" + CMDline + "\"");

        if (NLIsCollab) {
            cmd.addRaw("-checkoutProject " + "'" + this.NLCollabProjectName + "'");
            cmd.addRaw("-Collab " + "'" + this.NLCollabUrl + this.NLCollabProjectPath + "'");
            cmd.addRaw("-CollabLogin " + "'" + this.NLCollabUsername + ":" + PasswordEncoder.encode(this.NLCollabPassword) + "'");
        }

        if (NLIsNTS) {
            cmd.addRaw("-NTS " + "'" + this.NTSUrl + "'");
            cmd.addRaw("-NTSCollabPath " + "'" + this.NLCollabProjectPath + "'");
            cmd.addRaw("-NTSLogin " + "'" + this.NTSUsername + ":" + PasswordEncoder.encode(this.NTSPassword) + "'");
            cmd.addRaw("-checkoutProject " + "'" + this.NLCollabProjectName + "'");
            cmd.addRaw("-publishTestResult ");
            cmd.addRaw("-leaseLicense " + "'" + this.NTSLicenseID + ":" + NbVU + ":" + Nbhour + "'");
        }

        if (NlISNLWeb) {
            cmd.addRaw("-nlweb" + SPACE);
            cmd.addRaw("-nlwebAPIURL " + "'" + this.NLWEBURL + "'");
            cmd.addRaw("-nlwebToken " + "'" + this.NLWEBAPIToken + "'");
        }

        if (!NLIsCollab && !NLIsNTS) {
            cmd.addRaw("-project " + "'" + this.NLProjectPath + "'");
        }

        if (NLIsCloud) {
            cmd.addRaw("-NCPLogin '" + this.NLCloudUsername + ":" + PasswordEncoder.encode(this.NLCloudPassword) + "'");
        }

        if (IsVariableUsed) {
            cmd.addRaw("-variables '" + this.Variables + "'");
        }

        if (IsCloudUsed) {
            cmd.addRaw("-loadGenerators '" + GenerateFileTempFolder() + "tmp.yaml'");
        }

        cmd.addRaw("-SLAJUnitMapping 'pass'");
        cmd.addRaw("-SLAJUnitResults '" + GenerateFileTempFolder() + "junit.xml'");

        if (this.ReleaseName != null) {
            cmd.addRaw("-description '" + this.NLTestDescription + "_" + this.ReleaseName + "_" + this.ReleaseId + "'");
        } else {
            cmd.addRaw("-description '" + this.NLTestDescription + "'");
        }

        cmd.addRaw("-report '" + GenerateFileTempFolder() + "report.xml," + GenerateFileTempFolder() + "report.pdf'");
        cmd.addRaw("-launch " + "'" + this.NLScenarioName + "'");
        cmd.addRaw("-noGUI");

        return cmd;
    }

    private byte[] getFilebyteArray(OverthereFile file) throws IOException {
        if (file.exists()) {
            InputStream i = file.getInputStream();
            return IOUtils.toByteArray(i);
        } else return null;

    }

    private void CopyFile(OverthereFile file) throws IOException {
        if (file.exists()) {
            InputStream is = file.getInputStream();
            OutputStream outputStream = new FileOutputStream(new File(file.getName()));

            int read;
            byte[] bytes = new byte[1024];

            while ((read = is.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }
    }

    private OverthereConnection GetConnection() {
        if (overthereConnection == null) {
            options.set(ADDRESS, this.NLHost);
            options.set(USERNAME, this.NLUsername);
            options.set(PASSWORD, this.NLPassword);

            if (this.OS.equals(LINUX)) {
                options.set(OPERATING_SYSTEM, UNIX);
                options.set(CONNECTION_TYPE, SCP);
                overthereConnection = Overthere.getConnection("ssh", options);
            } else if (this.OS.equals(WINDOWS)) {
                options.set(OPERATING_SYSTEM, OperatingSystemFamily.WINDOWS);
                options.set(CONNECTION_TYPE, TELNET);
                overthereConnection = Overthere.getConnection("cifs", options);
            } else if (this.OS.equals(WINDOWSRM)) {
                options.set(OPERATING_SYSTEM, OperatingSystemFamily.WINDOWS);
                options.set(CONNECTION_TYPE, WINRM_NATIVE);
                options.set("pathShareMappings", ImmutableMap.of());
                overthereConnection = Overthere.getConnection("cifs", options);
            } else if (this.OS.equals(MACOS)) {
                options.set(OPERATING_SYSTEM, UNIX);
                options.set(CONNECTION_TYPE, SFTP);
                overthereConnection = Overthere.getConnection("ssh", options);
            }
            overthereConnection.setWorkingDirectory(overthereConnection.getFile(GenerateFileTempFolder()));
        }

        return overthereConnection;
    }

    private StringBuilder CreateRemoteFile(OverthereConnection connection, String Content) throws IOException {
        StringBuilder result = new StringBuilder();

        OverthereFile motd = connection.getFile(GenerateFileTempFolder() + "tmp.yaml");
        OutputStream w = motd.getOutputStream();
        try {
            result.append(Content);
            w.write(Content.getBytes());
        } finally {
            w.close();
            return result;
        }
    }

    public CmdResponse execute() {
        int rc;
        CapturingOverthereExecutionOutputHandler stdout = capturingHandler();
        CapturingOverthereExecutionOutputHandler stderr = capturingHandler();
        CmdLine script;
        OverthereFile junit;
        CmdResponse response = null;
        StringBuilder log = null;
        StringBuilder comment = new StringBuilder();
        try {

            comment.append("Connection to remote Controller\n");
            overthereConnection = GetConnection();

            if (overthereConnection != null) {
                comment.append("Connection Done.....\n");

                script = GenerateCmdLine();

                System.out.println("Execute script: " + script);

                if (CloudYML != null) {
                    comment.append("Setting Cloud Session to the controller.....\n");
                    log = CreateRemoteFile(overthereConnection, CloudYML);
                }

                comment.append("Launching test.....\n").append(script);

                rc = overthereConnection.execute(stdout, stderr, script);
                comment.append("Test finished.....\n");

                response = new CmdResponse(rc, stdout.getOutput(), stderr.getOutput());

                final String tempFolder = GenerateFileTempFolder();
                junit = overthereConnection.getFile(Paths.get(tempFolder, "report.dtd").toString());
                CopyFile(junit);

                response.setReportdtdbytes(getFilebyteArray(junit));
                junit = overthereConnection.getFile(Paths.get(tempFolder, "report.xml").toString());

                if (log != null) {
                    response.addToOut(log);
                }

                if (junit.exists()) {
                    log = Getdata(junit.getInputStream(), response);
                    response.setReportXMLbytes(getFilebyteArray(junit));
                    junit.delete();

                    if (log != null) {
                        response.addToOut(log);
                    }
                }

                junit = overthereConnection.getFile(Paths.get(tempFolder, "report.pdf").toString());
                if (junit.exists()) {
                    response.setPDFbytes(getFilebyteArray(junit));
                    junit.delete();
                }

                junit = overthereConnection.getFile(Paths.get(tempFolder, "junit.xml").toString());
                if (junit.exists()) {
                    GetJunitData(junit.getInputStream(), response);
                    response.setJunitxmlbytes(getFilebyteArray(junit));
                    response.rc = 1;
                    junit.delete();
                } else {
                    response.addToErr("No Junit.xml found\n");
                }


                if (IsCloudUsed) {
                    junit = overthereConnection.getFile(Paths.get(tempFolder, "tmp.yaml").toString());
                    if (junit.exists()) {
                        junit.delete();
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
            response.SetCommenrt(comment);
            if (overthereConnection != null) {
                overthereConnection.close();
            }
            return response;
        }
    }

    public StringBuilder Getdata(InputStream input, CmdResponse res) {
        StringBuilder output = new StringBuilder();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        String hit;
        String responsetime;
        String error;
        try {
            // use the factory to create a documentbuilder
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(input);
            hit = GetData(doc, "avg_hits/s", output);
            responsetime = GetData(doc, "avg_reqresponsetime", output);
            error = GetData(doc, "total_errors", output);

            res.addstat(responsetime, error, hit);
        } catch (Exception ex) {
            output.append(ex.getMessage());
        } finally {
            return output;
        }
    }

    public StringBuilder GetJunitData(InputStream input, CmdResponse res) {
        StringBuilder output = new StringBuilder();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        int total;
        int failure;
        int success;
        StringBuilder out = new StringBuilder();
        StringBuilder error = new StringBuilder();
        StringBuilder comment = new StringBuilder();
        try {
            // use the factory to create a documentbuilder
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(input);
            total = GetStats(doc, "/resource/testsuite/testcase");
            success = GetStats(doc, "/resource/testsuite/testcase/sucess");
            failure = GetStats(doc, "/resource/testsuite/testcase/failure");
            out.append("Results :\n");
            out.append("\t" + success + " SLAS were sucessful out of " + total);

            comment.append("Results :\n");
            comment.append("\t" + success + " SLAS were sucessful out of " + total);

            if (failure > 0) {
                error.append(failure + " SlA were in error out of " + total + "\n");
                comment.append(failure + " SlA were in error out of " + total + "\n");
                GetError(doc, error, comment);
                res.addToErr(error);
            }

            res.addToOut(output);

        } catch (Exception ex) {
            res.addToErr(ex.getStackTrace().toString());
        } finally {
            res.addToOut(out);
            res.addToOut(error);
            return comment;
        }
    }

    public int GetStats(Document doc, String Query) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList nodes = (NodeList) xPath.evaluate(Query, doc.getDocumentElement(), XPathConstants.NODESET);
        return nodes.getLength();
    }

    public void GetError(Document doc, StringBuilder output, StringBuilder comment) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList nodes = (NodeList) xPath.evaluate("/resource/testsuite/testcase/failure", doc.getDocumentElement(), XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); ++i) {
            Node e = nodes.item(i);
            output.append("Failure n° " + (i + 1) + "\n");
            output.append("\t" + e.getFirstChild().getNodeValue() + "\n");
            comment.append("Failure n° " + (i + 1) + "\n");
            comment.append("\t" + e.getFirstChild().getNodeValue() + "\n");
        }
    }

    public String GetData(Document doc, String key, StringBuilder output) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        String result = null;
        NodeList nodes = (NodeList) xPath.evaluate("/report/summary/statistics/statistic[@name='" + key + "']", doc.getDocumentElement(), XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); ++i) {
            Element e = (Element) nodes.item(i);
            result = e.getAttribute("value");
        }
        return result;
    }

    public class CmdResponse {
        public int rc;
        public String stdout;
        public String stderr;
        public String responsetime;
        public String error;
        public String hits;

        public byte[] ReportXMLbytes;
        public byte[] Reportdtdbytes;
        public byte[] junitxmlbytes;
        public byte[] pdfbytes;
        public String comment = null;

        public CmdResponse(int rc, String stdout, String stderr) {
            this.rc = rc;
            this.stdout = stdout;
            this.stderr = stderr;
        }

        public CmdResponse() {
            this.rc = 0;
            this.stdout = "";
            this.stderr = "";
        }

        private void SetCommenrt(StringBuilder com) {
            this.comment = com.toString();
        }

        public void addstat(String responsetime, String error, String his) {
            this.responsetime = responsetime;
            this.error = error;
            this.hits = his;
        }

        public void addToOut(StringBuilder s) {
            this.stdout += "\n" + s.toString();
        }

        public void addToOut(String s) {
            this.stdout += "\n" + s;
        }

        public void addToErr(StringBuilder s) {
            this.stderr += "\n" + s.toString();
            this.rc = 0;
        }

        public void addToErr(String s) {
            this.stderr += "\n" + s;
            this.rc = 1;
        }

        public void setReportXMLbytes(byte[] reportXMLbytes) {
            ReportXMLbytes = reportXMLbytes;
        }

        public void setReportdtdbytes(byte[] reportdtdbytes) {
            Reportdtdbytes = reportdtdbytes;
        }

        public void setPDFbytes(byte[] pdf) {
            pdfbytes = pdf;
        }

        public void setJunitxmlbytes(byte[] junitxmlbytes) {
            this.junitxmlbytes = junitxmlbytes;
        }
    }
}
