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

	private static final String SCRIPT_NAME = "NeoLoad-Test";
	public static final String WINDOWS_TELNET_LABEL = "Windows - Telnet";
	public static final String WINDOWS_RM_LABEL = "Windows - WinRM";
	public static final String LINUX_LABEL = "Linux";
	public static final String MAC_LABEL = "Mac OS";
	private static final String UNIX_SCP_HOME = "/home/";
	private static final String MAC_SCP_HOME = "/Users/";
	private static final String SPACE = " ";
	private String windowsUserHomeDirectory;
	private ConnectionOptions options;
	private OperatingSystem OS;
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
	private String nlTestDescription;
	private String releaseName;
	private String releaseId;
	private String cloudYml = null;
	private String variables = null;
	private boolean isVariableUsed = false;
	private boolean isCloudUsed = false;
	private OverthereConnection overthereConnection;

	public NeoLoadTest(String host, String username, String password, String path, String OSString,
					   String neoloadScenario, String localNeoLoadProject, String neoLoadTestDescription, String neoloadWebAPIToken,
					   String nlcollabprojectname, String collaborationProjectPath, String nbVu, String nbHour) {
		this.NLHost = host;
		this.NLUsername = username;
		this.NLPassword = password;
		this.NLInstallationPath = path;
		this.OS = OperatingSystem.fromName(OSString);
		this.NLScenarioName = neoloadScenario;
		this.NLProjectPath = localNeoLoadProject;
		this.nlTestDescription = neoLoadTestDescription;
		this.NLWEBAPIToken = neoloadWebAPIToken;
		this.NLCollabProjectPath = collaborationProjectPath;
		this.NLCollabProjectName = nlcollabprojectname;
		this.NLIsCollab = false;
		this.NLIsNTS = false;
		this.NlISNLWeb = false;
		this.NLIsCloud = false;
		this.Nbhour = nbHour;
		this.NbVU = nbVu;
		options = new ConnectionOptions();
	}

	public NeoLoadTest(ConfigurationItem remoteScript) {
		options = new ConnectionOptions();

		ConfigurationItem neoLoad = remoteScript.getProperty("NeoLoadPath");
		ConfigurationItem nts = remoteScript.getProperty("NeoLoadTeamServer");
		ConfigurationItem collab = remoteScript.getProperty("NeoLoadCollaboration");
		ConfigurationItem nlweb = remoteScript.getProperty("NeoLoadWebAPI");
		//----setting related to neoLoad---------------
		this.NLHost = neoLoad.getProperty("NL_Host");
		this.NLUsername = neoLoad.getProperty("username");
		this.NLPassword = neoLoad.getProperty("password");
		this.NLInstallationPath = neoLoad.getProperty("NL_Controller_Path");
		this.OS = neoLoad.getProperty("OS");
		//-----------------------------------------------

		//-----settings on the test execution--------------
		this.NLScenarioName = remoteScript.getProperty("NeoloadScenario");
		this.NLProjectPath = remoteScript.getProperty("LocalNeoLoadProject");
		this.nlTestDescription = remoteScript.getProperty("NeoLoadTestDescription");
		//--------------------------------------------------

		//-----NL Web settings-------------------------------------
		if (nlweb != null) {
			this.NLWEBURL = nlweb.getProperty("NL_WEB_URL");
			this.NLWEBAPIToken = remoteScript.getProperty("NeoloadWebAPItoken");
			this.NlISNLWeb = true;
		} else
			this.NlISNLWeb = false;
		//---------------------------------------------------------

		//-----getting the information related to nts--------------
		if (nts != null) {
			this.NLIsNTS = true;
			this.NTSUrl = nts.getProperty("TeamServerHost");
			this.NTSUsername = nts.getProperty("username");
			this.NTSPassword = nts.getProperty("password");
			this.NTSLicenseID = nts.getProperty("licenceID");

		} else
			this.NLIsNTS = false;
		//---------------------------------------------------------

		//-----getting the information related to Collab--------------
		if (collab != null) {
			this.NLIsCollab = true;
			this.NLCollabUrl = collab.getProperty("Url");
			this.NLCollabUsername = collab.getProperty("username");
			this.NLCollabPassword = collab.getProperty("password");
			this.NLCollabProjectName = remoteScript.getProperty("CollabProjectName");
			this.NLCollabProjectPath = remoteScript.getProperty("CollaborationProjectPath");
		} else
			this.NLIsCollab = false;
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
		this.NLCloudPassword = pass;
		this.NLCloudUsername = user;
		this.NLIsCloud = true;
	}

	public void setWebAPI(String webURL) {
		this.NLWEBURL = webURL;
		this.NlISNLWeb = true;
	}

	public void setNTS(String url, String username, String password, String licenseID) {
		this.NLIsNTS = true;
		this.NTSUrl = url;
		this.NTSUsername = username;
		this.NTSPassword = password;
		this.NTSLicenseID = licenseID;
	}

	public void setCollab(String url, String username, String password) {
		this.NLIsCollab = true;
		this.NLCollabUrl = url;
		this.NLCollabUsername = username;
		this.NLCollabPassword = password;
	}

	private String generateFileTempFolder() {

		switch (OS){
			case MAC:
				return MAC_SCP_HOME + this.NLUsername + "/";
			case LINUX:
				if (this.NLUsername.equalsIgnoreCase("root"))
					return UNIX_SCP_HOME + "/";
				else
					return UNIX_SCP_HOME + "/" + this.NLUsername + "/";
			case WINDOWS_TELNET:
			case WINDOWS_RM:
				return retrieveWindowsTempFolder();
		}
		return null;
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
		String cmdLine = Paths.get(this.NLInstallationPath, "bin").toString();

		if (OS == WINDOWS_TELNET || OS == WINDOWS_RM) {
			cmdLine = Paths.get(cmdLine, "NeoLoadCmd.exe").toString();
		} else {
			cmdLine = Paths.get(cmdLine, "NeoLoadCmd").toString();
		}
		cmd.addRaw("\"" + cmdLine + "\"");

		if (NLIsCollab) {
			cmd.addRaw("-Collab " + "'" + this.NLCollabUrl + this.NLCollabProjectPath + "'");
            cmd.addRaw("-CollabLogin " + "'" + this.NLCollabUsername + ":" + PasswordEncoder.encode(this.NLCollabPassword) + "'");
            cmd.addRaw("-checkoutProject " + "'" + this.NLCollabProjectName + "'");
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

		if (isVariableUsed) {
			cmd.addRaw("-variables '" + this.variables + "'");
		}

		final String fileTempFolder = generateFileTempFolder();
		if (isCloudUsed) {
			cmd.addRaw("-loadGenerators '" + Paths.get(fileTempFolder, "tmp.yaml'").toString());
		}

		cmd.addRaw("-SLAJUnitMapping 'pass'");
		cmd.addRaw("-SLAJUnitResults '" + Paths.get(fileTempFolder, "junit.xml'").toString());

		if (this.releaseName != null) {
			cmd.addRaw("-description '" + this.nlTestDescription + "_" + this.releaseName + "_" + this.releaseId + "'");
		} else {
			cmd.addRaw("-description '" + this.nlTestDescription + "'");
		}

		cmd.addRaw("-report '" + Paths.get(fileTempFolder,"report.xml").toString() + "," + Paths.get(fileTempFolder, "report.pdf").toString() + "'");
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

	private void copyFile(OverthereFile file) throws IOException {
		if (file.exists()) {
			InputStream is = file.getInputStream();
			try (OutputStream outputStream = new FileOutputStream(new File(file.getName()))) {

				int read;
				byte[] bytes = new byte[1024];

				while ((read = is.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}
			}
		}
	}

	private OverthereConnection getConnection() {
		if (overthereConnection == null) {
			options.set(ADDRESS, this.NLHost);
			options.set(USERNAME, this.NLUsername);
			options.set(PASSWORD, this.NLPassword);

			switch (OS){
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

	private StringBuilder createRemoteFile(OverthereConnection connection, String content) throws IOException {
		StringBuilder result = new StringBuilder();

		OverthereFile overthereFile = connection.getFile(generateFileTempFolder() + "tmp.yaml");
        try (OutputStream w = overthereFile.getOutputStream()) {
            result.append(content);
            w.write(content.getBytes());
        }
        return result;
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
			overthereConnection = getConnection();

			if (overthereConnection != null) {
				comment.append("Connection Done.....\n");

				script = generateCmdLine();

				System.out.println("Execute script: " + script);

				if (cloudYml != null) {
					comment.append("Setting Cloud Session to the controller.....\n");
					log = createRemoteFile(overthereConnection, cloudYml);
				}

				comment.append("Launching test.....\n").append(script);

				rc = overthereConnection.execute(stdout, stderr, script);
				comment.append("Test finished.....\n");

				response = new CmdResponse(rc, stdout.getOutput(), stderr.getOutput());

				final String tempFolder = generateFileTempFolder();
				junit = overthereConnection.getFile(Paths.get(tempFolder, "report.dtd").toString());
				copyFile(junit);

				response.setReportDTDBytes(getFilebyteArray(junit));
				junit = overthereConnection.getFile(Paths.get(tempFolder, "report.xml").toString());

				if (log != null) {
					response.addToOut(log);
				}

				if (junit.exists()) {
					log = getData(junit.getInputStream(), response);
					response.setReportXMLBytes(getFilebyteArray(junit));
					junit.delete();

					if (log != null) {
						response.addToOut(log);
					}
				}

				junit = overthereConnection.getFile(Paths.get(tempFolder, "report.pdf").toString());
				if (junit.exists()) {
					response.setPDFBytes(getFilebyteArray(junit));
					junit.delete();
				}

				junit = overthereConnection.getFile(Paths.get(tempFolder, "junit.xml").toString());
				if (junit.exists()) {
					getJunitData(junit.getInputStream(), response);
					response.setJunitXMLBytes(getFilebyteArray(junit));
					response.rc = 1;
					junit.delete();
				} else {
					response.addToErr("No Junit.xml found\n");
				}


				if (isCloudUsed) {
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
			response.setComment(comment);
			if (overthereConnection != null) {
				overthereConnection.close();
			}
			return response;
		}
	}

	private StringBuilder getData(InputStream input, CmdResponse res) {
		StringBuilder output = new StringBuilder();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		String hit;
		String responseTime;
		String error;
		try {
			// use the factory to create a documentbuilder
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(input);
			hit = retrieveData(doc, "avg_hits/s");
			responseTime = retrieveData(doc, "avg_reqresponsetime");
			error = retrieveData(doc, "total_errors");

			res.addStat(responseTime, error, hit);
		} catch (Exception ex) {
			output.append(ex.getMessage());
		} finally {
			return output;
		}
	}

	public StringBuilder getJunitData(InputStream input, CmdResponse res) {
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
			total = getStats(doc, "/resource/testsuite/testcase");
			success = getStats(doc, "/resource/testsuite/testcase/sucess");
			failure = getStats(doc, "/resource/testsuite/testcase/failure");
			out.append("Results :\n");
			out.append("\t" + success + " SLAS were sucessful out of " + total);

			comment.append("Results :\n");
			comment.append("\t" + success + " SLAS were sucessful out of " + total);

			if (failure > 0) {
				error.append(failure + " SlA were in error out of " + total + "\n");
				comment.append(failure + " SlA were in error out of " + total + "\n");
				getError(doc, error, comment);
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

	public int getStats(Document doc, String query) throws XPathExpressionException {
		XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList nodes = (NodeList) xPath.evaluate(query, doc.getDocumentElement(), XPathConstants.NODESET);
		return nodes.getLength();
	}

	public void getError(Document doc, StringBuilder output, StringBuilder comment) throws XPathExpressionException {
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

	public class CmdResponse {
		public int rc;
		public String stdout;
		public String stderr;
		public String responseTime;
		public String error;
		public String hits;

		public byte[] reportXMLBytes;
		public byte[] reportDTDBytes;
		public byte[] junitXMLBytes;
		public byte[] reportPDFBytes;
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

		private void setComment(StringBuilder com) {
			this.comment = com.toString();
		}

		public void addStat(String responseTime, String error, String his) {
			this.responseTime = responseTime;
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

		public void setReportXMLBytes(byte[] reportXMLBytes) {
			this.reportXMLBytes = reportXMLBytes;
		}

		public void setReportDTDBytes(byte[] reportDTDBytes) {
			this.reportDTDBytes = reportDTDBytes;
		}

		public void setPDFBytes(byte[] reportPDFBytes) {
			this.reportPDFBytes = reportPDFBytes;
		}

		public void setJunitXMLBytes(byte[] junitXMLBytes) {
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

		 public static OperatingSystem fromName(String osString) {
			 switch (osString) {
				 case WINDOWS_TELNET_LABEL:
				 	return WINDOWS_TELNET;
				 case WINDOWS_RM_LABEL:
				 	return WINDOWS_RM;
				 case LINUX_LABEL:
				 	return LINUX;
				 case MAC_LABEL:
				 	return MAC;
			 }
			 throw new IllegalArgumentException("Can not support this OS : " + osString);
		 }
	 }
}
