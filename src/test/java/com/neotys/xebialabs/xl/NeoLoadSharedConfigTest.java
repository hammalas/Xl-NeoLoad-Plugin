package com.neotys.xebialabs.xl;

import com.xebialabs.pages.*;
import com.xebialabs.specs.BaseTest;
import org.testng.annotations.*;

public class NeoLoadSharedConfigTest extends BaseTest {

	@BeforeMethod
	public void testStartUp(){
		System.out.println("called before method");
		LoginPage.login("admin","Neotys13&#");
	}

	@Test
	public void openConfigurationNeoloadCloud() {
		MainMenu.clickMenu("Settings");
		SubMenu.clickSubMenu("Shared configuration");
		SharedConfigurationPage.openSharedConfiguration("Neo Load: Cloud");
		SharedConfigurationPropertiesPage.checkSharedConfigurationHeader("Cloud");
	}

	@Test
	public void openConfigurationNeoloadCollaboration(){
		MainMenu.clickMenu("Settings");
		SubMenu.clickSubMenu("Shared configuration");
		SharedConfigurationPage.openSharedConfiguration("NeoLoad Collaboration");
		SharedConfigurationPropertiesPage.checkSharedConfigurationHeader("NeoLoad Collaboration");
		SharedConfigurationPropertiesPage.setEditFieldBySequence(1, "NTS Collaboration");
		SharedConfigurationPropertiesPage.setEditFieldBySequence(2, "http://nts:8800/nts/svnroot/repository_1/");
		SharedConfigurationPropertiesPage.setEditFieldBySequence(3, "admin");
		SharedConfigurationPropertiesPage.setEditFieldBySequence(4, "admin");
		SharedConfigurationPropertiesPage.clickButtonByText("Save");
	}

	@Test
	public void openConfigurationNeoloadController(){
		MainMenu.clickMenu("Settings");
		SubMenu.clickSubMenu("Shared configuration");
		SharedConfigurationPage.openSharedConfiguration("NeoLoad Controller");
		SharedConfigurationPropertiesPage.checkSharedConfigurationHeader("NeoLoad Controller");
	}

	@Test
	public void openConfigurationNeoloadTeamServer(){
		MainMenu.clickMenu("Settings");
		SubMenu.clickSubMenu("Shared configuration");
		SharedConfigurationPage.openSharedConfiguration("NeoLoad Team Server");
		SharedConfigurationPropertiesPage.checkSharedConfigurationHeader("NeoLoad Team Server");
	}

	@Test
	public void openConfigurationNeoloadWeb(){
		MainMenu.clickMenu("Settings");
		SubMenu.clickSubMenu("Shared configuration");
		SharedConfigurationPage.openSharedConfiguration("NeoLoad Web");
		SharedConfigurationPropertiesPage.checkSharedConfigurationHeader("NeoLoad Web");
	}

	@Test
	public void openNeoloadReleaseTest(){
		MainMenu.clickMenu("Releases");
		ReleasePage.newReleaseFromTemplate();
		CreateReleasePage.createReleaseByName("Test NeoLoad");
	}

	@AfterMethod
	public void logout(){
		System.out.println("called after method");
		MainMenu.logout();
	}
}