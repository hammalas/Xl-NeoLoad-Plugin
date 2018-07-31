package com.neotys.xebialabs.xl;

import com.xebialabs.pages.*;
import com.xebialabs.specs.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

public class NeoLoadSharedConfigTest extends BaseTest {

	private static final String SETTINGS = "Settings";
	private static final String SHARED_CONFIGURATION = "Shared configuration";
	private static final String NEO_LOAD_CLOUD_CONFIGURATION_TEST = "NeoLoad Cloud Configuration Test";
	private static final String NTS_COLLABORATION_TEST = "NTS Collaboration Test";
	private static final String NEO_LOAD_WINDOWS = "NeoLoad Windows";
	private static final String NEO_LOAD_TEAM_SERVER = "NeoLoad Team Server";
	private static final String NEO_LOAD_WEB_PROD = "NeoLoad Web PROD";
	private static final String NEO_LOAD_COLLABORATION = "NeoLoad Collaboration";
	private static final String NEO_LOAD_CONTROLLER = "NeoLoad Controller";
	private static final String NEO_LOAD_WEB = "NeoLoad Web";
	private static final String NEO_LOAD_CLOUD = "NeoLoad Cloud";
	private static final String NL_TEST = "NL test";
	public static final String TEMPLATE_NEOLOAD_TEST = "Template Neoload Test";
	private WebDriverWait wait;
	private static final String login = "admin";
	private static final String password = "Neotys13&#";

	@BeforeClass
	public void testLogin() {
		System.out.println("called before class");
		wait = new WebDriverWait(BaseTest.driver, 5L);
		LoginPage.login(login, password);
	}

	@Test
	public void openConfigurationNeoloadCloud() {
		MainMenu.clickMenu(SETTINGS);
		SubMenu.clickSubMenu(SHARED_CONFIGURATION);
		SharedConfigurationPage.openSharedConfiguration(NEO_LOAD_CLOUD);
		SharedConfigurationPropertiesPage.checkSharedConfigurationHeader(NEO_LOAD_CLOUD);
		SharedConfigurationPropertiesPage.setEditFieldBySequence(1, NEO_LOAD_CLOUD_CONFIGURATION_TEST);
		SharedConfigurationPropertiesPage.setEditFieldBySequence(2, login);
		SharedConfigurationPropertiesPage.setEditFieldBySequence(3, password);
		saveConfig();
	}

	@Test
	public void openConfigurationNeoloadCollaboration() {
		MainMenu.clickMenu(SETTINGS);
		SubMenu.clickSubMenu(SHARED_CONFIGURATION);
		SharedConfigurationPage.openSharedConfiguration(NEO_LOAD_COLLABORATION);
		SharedConfigurationPropertiesPage.checkSharedConfigurationHeader(NEO_LOAD_COLLABORATION);
		SharedConfigurationPropertiesPage.setEditFieldBySequence(1, NTS_COLLABORATION_TEST);
		SharedConfigurationPropertiesPage.setEditFieldBySequence(2, "http://nts:8800/nts/svnroot/repository_1/");
		SharedConfigurationPropertiesPage.setEditFieldBySequence(3, login);
		SharedConfigurationPropertiesPage.setEditFieldBySequence(4, password);
		saveConfig();
	}

	@Test
	public void openConfigurationNeoloadController() {
		MainMenu.clickMenu(SETTINGS);
		SubMenu.clickSubMenu(SHARED_CONFIGURATION);
		SharedConfigurationPage.openSharedConfiguration(NEO_LOAD_CONTROLLER);
		SharedConfigurationPropertiesPage.checkSharedConfigurationHeader(NEO_LOAD_CONTROLLER);
		SharedConfigurationPropertiesPage.setEditFieldBySequence(1, NEO_LOAD_WINDOWS);
		SharedConfigurationPropertiesPage.setEditFieldBySequence(3, "path");
		SharedConfigurationPropertiesPage.setEditFieldBySequence(4, login);
		SharedConfigurationPropertiesPage.setEditFieldBySequence(5, password);
		saveConfig();
	}

	@Test
	public void openConfigurationNeoloadTeamServer() {
		MainMenu.clickMenu(SETTINGS);
		SubMenu.clickSubMenu(SHARED_CONFIGURATION);
		SharedConfigurationPage.openSharedConfiguration(NEO_LOAD_TEAM_SERVER);
		SharedConfigurationPropertiesPage.checkSharedConfigurationHeader(NEO_LOAD_TEAM_SERVER);
		SharedConfigurationPropertiesPage.setEditFieldBySequence(1, NEO_LOAD_TEAM_SERVER);
		SharedConfigurationPropertiesPage.setEditFieldBySequence(2, "http://nts:9999/nts/");
		SharedConfigurationPropertiesPage.setEditFieldBySequence(3, login);
		SharedConfigurationPropertiesPage.setEditFieldBySequence(4, password);
		SharedConfigurationPropertiesPage.setEditFieldBySequence(5, "license ID");
		saveConfig();
	}

	@Test
	public void openConfigurationNeoloadWeb() {
		MainMenu.clickMenu(SETTINGS);
		SubMenu.clickSubMenu(SHARED_CONFIGURATION);
		SharedConfigurationPage.openSharedConfiguration(NEO_LOAD_WEB);
		SharedConfigurationPropertiesPage.checkSharedConfigurationHeader(NEO_LOAD_WEB);
		SharedConfigurationPropertiesPage.setEditFieldBySequence(1, NEO_LOAD_WEB_PROD);
		SharedConfigurationPropertiesPage.setEditFieldBySequence(2, "https://neoload.neotys.com");
		saveConfig();
	}

	@Test
	public void openNeoloadReleaseTest() {
		MainMenu.clickMenu("Design");
		SubMenu.clickSubMenu("Template");
		TemplateListPage.clickNewTemplate();
		CreateTemplatePage.createTemplateByName(TEMPLATE_NEOLOAD_TEST);
		ReleasePage.newReleaseFromTemplate();
		CreateReleasePage.createReleaseByName("Test NeoLoad");
		ReleasePage.addTask(NL_TEST, "Neo Load", "NeoLoad Test");
		TaskDetailPage.selectItemByIndex(1, NEO_LOAD_WINDOWS);
		TaskDetailPage.closeTaskDetails();
	}

	@AfterClass
	public void endSuite() {
		MainMenu.logout();
	}

	private void deleteTemplate(final String templateName) {
		MainMenu.clickMenu("Design");
		SubMenu.clickSubMenu("Template");
		BaseTest.driver.findElement(By.xpath("//strong[text()='" + templateName + "']/../../../span[text()='delete']")).click();
	}

	private void deleteTask(String taskName) {
		BaseTest.driver.findElement(By.xpath("//span[text()='" + taskName + "']/../../..//i[@class='context-menu-icon']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='remove-task-button']/..")));
		BaseTest.driver.findElement(By.xpath("//a[@class='remove-task-button']/..")).click();
	}

	private void saveConfig() {
		SharedConfigurationPropertiesPage.clickButtonByText("Save");
	}

	private void deleteConfig(String name) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[text()='" + name + "']")));
		BaseTest.driver.findElement(By.xpath("//td[text()='\"+name+\"']/../td[@class='action']" +
				"/span[@class='delete-instance']")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//td[text()='\"+name+\"']")));
	}

	private void deleteAllConfig() {
		deleteConfig(NEO_LOAD_CLOUD_CONFIGURATION_TEST);
		deleteConfig(NTS_COLLABORATION_TEST);
		deleteConfig(NEO_LOAD_WINDOWS);
		deleteConfig(NEO_LOAD_TEAM_SERVER);
		deleteConfig(NEO_LOAD_WEB_PROD);
	}


}