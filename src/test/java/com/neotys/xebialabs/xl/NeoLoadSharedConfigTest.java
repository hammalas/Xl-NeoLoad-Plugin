package com.neotys.xebialabs.xl;

import com.xebialabs.pages.*;
import com.xebialabs.specs.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

public class NeoLoadSharedConfigTest extends BaseTest {

	public static final String SETTINGS = "Settings";
	public static final String SHARED_CONFIGURATION = "Shared configuration";
	WebDriverWait wait;
	private static final String login = "admin";
	private static final String password = "Neotys13&#";

	@BeforeClass
	public void testLogin() {
		System.out.println("called before class");
		wait = new WebDriverWait(BaseTest.driver, 10L);
		LoginPage.login(login, password);
	}

	@Test
	public void openConfigurationNeoloadCloud() {
		MainMenu.clickMenu(SETTINGS);
		SubMenu.clickSubMenu(SHARED_CONFIGURATION);
		SharedConfigurationPage.openSharedConfiguration("NeoLoad Cloud");
		SharedConfigurationPropertiesPage.checkSharedConfigurationHeader("Cloud");
		SharedConfigurationPropertiesPage.setEditFieldBySequence(1, "NeoLoad Cloud Configuration Test");
		SharedConfigurationPropertiesPage.setEditFieldBySequence(2, "admin");
		SharedConfigurationPropertiesPage.setEditFieldBySequence(3, "admin");
		saveConfig();
	}

	@Test
	public void openConfigurationNeoloadCollaboration() {
		MainMenu.clickMenu(SETTINGS);
		SubMenu.clickSubMenu(SHARED_CONFIGURATION);
		SharedConfigurationPage.openSharedConfiguration("NeoLoad Collaboration");
		SharedConfigurationPropertiesPage.checkSharedConfigurationHeader("NeoLoad Collaboration");
		SharedConfigurationPropertiesPage.setEditFieldBySequence(1, "NTS Collaboration Test");
		SharedConfigurationPropertiesPage.setEditFieldBySequence(2, "http://nts:8800/nts/svnroot/repository_1/");
		SharedConfigurationPropertiesPage.setEditFieldBySequence(3, "admin");
		SharedConfigurationPropertiesPage.setEditFieldBySequence(4, "admin");
		saveConfig();
	}

	@Test
	public void openConfigurationNeoloadController() {
		MainMenu.clickMenu(SETTINGS);
		SubMenu.clickSubMenu(SHARED_CONFIGURATION);
		SharedConfigurationPage.openSharedConfiguration("NeoLoad Controller");
		SharedConfigurationPropertiesPage.checkSharedConfigurationHeader("NeoLoad Controller");
		SharedConfigurationPropertiesPage.setEditFieldBySequence(1, "NeoLoad Windows");
		//SharedConfigurationPropertiesPage.setOptionFromSelectFieldBySequence(1, "Windows - Telnet");
		//SharedConfigurationPropertiesPage.setEditFieldBySequence(2, "localhost");
		SharedConfigurationPropertiesPage.setEditFieldBySequence(3, "path");
		SharedConfigurationPropertiesPage.setEditFieldBySequence(4, login);
		SharedConfigurationPropertiesPage.setEditFieldBySequence(5, password);
		saveConfig();
	}

	@Test
	public void openConfigurationNeoloadTeamServer() {
		MainMenu.clickMenu(SETTINGS);
		SubMenu.clickSubMenu(SHARED_CONFIGURATION);
		SharedConfigurationPage.openSharedConfiguration("NeoLoad Team Server");
		SharedConfigurationPropertiesPage.checkSharedConfigurationHeader("NeoLoad Team Server");
		SharedConfigurationPropertiesPage.setEditFieldBySequence(1, "NeoLoad Team Server");
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
		SharedConfigurationPage.openSharedConfiguration("NeoLoad Web");
		SharedConfigurationPropertiesPage.checkSharedConfigurationHeader("NeoLoad Web");
		SharedConfigurationPropertiesPage.setEditFieldBySequence(1, "NeoLoad Web PROD");
		SharedConfigurationPropertiesPage.setEditFieldBySequence(2, "https://neoload.neotys.com");
		saveConfig();
	}

	@Test
	public void openNeoloadReleaseTest() {
		MainMenu.clickMenu("Design");
		SubMenu.clickSubMenu("Template");
		TemplateListPage.clickNewTemplate();
		CreateTemplatePage.createTemplateByName("test_NL");
		ReleasePage.newReleaseFromTemplate();
		CreateReleasePage.createReleaseByName("Test NeoLoad");
		ReleasePage.addTask("NL test", "Neo Load", "NeoLoad Test");
		TaskDetailPage.selectItemByIndex(1, "NeoLoad Windows");
	}

//	@AfterMethod
//	public void logout(){
//		System.out.println("called after method");
//		MainMenu.logout();
//	}

	@AfterClass
	public void endSuite() {
		deleteAllConfig();
		MainMenu.logout();
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
		deleteConfig("NeoLoad Cloud Configuration Test");
		deleteConfig("NTS Collaboration Test");
		deleteConfig("NeoLoad Windows");
		deleteConfig("NeoLoad Team Server");
		deleteConfig("NeoLoad Web PROD");
	}


}