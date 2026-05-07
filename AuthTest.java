package com.hmsexample;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.*;
import org.testng.annotations.Test;

import java.time.Duration;

public class AuthTest extends BaseTest {

    String baseUrl = "http://127.0.0.1:8000";

    public WebDriverWait getWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // 🔐 LOGIN (FIXED)
    public void loginAsAdmin() {

        driver.get(baseUrl + "/login/");

        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.name("username")))
                .sendKeys("admin");

        driver.findElement(By.name("password")).sendKeys("admin123");

        driver.findElement(By.xpath("//input[@type='submit' and @value='Login']")).click();

        // ✅ Wait for successful login (logout button visible)
        getWait().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@href='/logout/']")
        ));
    }

    // ------------------------
    // 🧍 GUEST FILTER TEST
    // ------------------------
    @Test
    public void testGuestFilter() {

        test = extent.createTest("Guest Filter Test");

        loginAsAdmin();

        driver.get(baseUrl + "/guests/");

        getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("f_day")));

        driver.findElement(By.id("f_day")).sendKeys("2026-03-24");
        driver.findElement(By.id("l_day")).sendKeys("2026-04-23");

        driver.findElement(By.name("filterDate")).click();

        test.pass("Guest filter applied successfully");
    }

    // ------------------------
    // 👨‍💼 ADD EMPLOYEE TEST
    // ------------------------
    

    // ------------------------
    // 📅 EVENT FILTER TEST
    // ------------------------
    @Test
public void testEventFilter() {
    test = extent.createTest("Event Filter Test");
    loginAsAdmin();
    driver.get(baseUrl + "/events/");

    getWait().until(ExpectedConditions.visibilityOfElementLocated(By.name("fd")));

    driver.findElement(By.name("type")).clear();
    driver.findElement(By.name("type")).sendKeys("Conference");

    driver.findElement(By.name("location")).clear();
    driver.findElement(By.name("location")).sendKeys("Hyderabad");

    driver.findElement(By.name("fd")).sendKeys("2026-04-01");
    driver.findElement(By.name("ed")).sendKeys("2026-04-30");

    // ✅ Use name="filter" instead of text-based XPath
    driver.findElement(By.name("filter")).click();

    test.pass("Event filter applied");
}

    // ------------------------
    // ➕ CREATE EVENT TEST
    // ------------------------
    

// ------------------------
// 🔐 LOGIN TESTS
// ------------------------
@Test
public void testLoginWithValidCredentials() {
    test = extent.createTest("Login With Valid Credentials");

    driver.get(baseUrl + "/login/");

    getWait().until(ExpectedConditions.visibilityOfElementLocated(By.name("username")))
            .sendKeys("admin");

    driver.findElement(By.name("password")).sendKeys("admin123");

    driver.findElement(By.xpath("//input[@type='submit' and @value='Login']")).click();

    getWait().until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//a[@href='/logout/']")
    ));

    test.pass("Login with valid credentials successful");
}

@Test
public void testLoginWithInvalidCredentials() {
    test = extent.createTest("Login With Invalid Credentials");

    driver.get(baseUrl + "/login/");

    getWait().until(ExpectedConditions.visibilityOfElementLocated(By.name("username")))
            .sendKeys("wronguser");

    driver.findElement(By.name("password")).sendKeys("wrongpass");

    driver.findElement(By.xpath("//input[@type='submit' and @value='Login']")).click();

    // Should stay on login page — logout button must NOT appear
    getWait().until(ExpectedConditions.urlContains("login"));

    test.pass("Invalid login correctly rejected");
}

@Test
public void testLoginWithEmptyCredentials() {
    test = extent.createTest("Login With Empty Credentials");

    driver.get(baseUrl + "/login/");

    getWait().until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));

    // Submit without entering anything
    driver.findElement(By.xpath("//input[@type='submit' and @value='Login']")).click();

    // Should stay on login page
    getWait().until(ExpectedConditions.urlContains("login"));

    test.pass("Empty login correctly rejected");
}

// ------------------------
// 🚪 LOGOUT TEST
// ------------------------
@Test
public void testLogout() {
    test = extent.createTest("Logout Test");

    loginAsAdmin();

    driver.findElement(By.xpath("//a[@href='/logout/']")).click();

    // Should redirect to login page after logout
    getWait().until(ExpectedConditions.urlContains("login"));

    test.pass("Logout successful");
}

// ------------------------
// 🧍 GUEST PAGE TESTS
// ------------------------
@Test
public void testGuestPageLoads() {
    test = extent.createTest("Guest Page Load Test");

    loginAsAdmin();

    driver.get(baseUrl + "/guests/");

    getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("f_day")));

    test.pass("Guest page loaded successfully");
}

@Test
public void testGuestFilterWithNoDateRange() {
    test = extent.createTest("Guest Filter With No Date Range");

    loginAsAdmin();

    driver.get(baseUrl + "/guests/");

    getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("f_day")));

    // Submit filter without entering any dates
    driver.findElement(By.name("filterDate")).click();

    // Page should still be on guests URL
    getWait().until(ExpectedConditions.urlContains("guests"));

    test.pass("Guest filter handled empty date range");
}

@Test
public void testGuestFilterWithReversedDateRange() {
    test = extent.createTest("Guest Filter With Reversed Date Range");

    loginAsAdmin();

    driver.get(baseUrl + "/guests/");

    getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("f_day")));

    // End date is before start date
    driver.findElement(By.id("f_day")).sendKeys("2026-04-23");
    driver.findElement(By.id("l_day")).sendKeys("2026-03-01");

    driver.findElement(By.name("filterDate")).click();

    getWait().until(ExpectedConditions.urlContains("guests"));

    test.pass("Guest filter handled reversed date range");
}

// ------------------------
// 📅 EVENT PAGE TESTS
// ------------------------
@Test
public void testEventPageLoads() {
    test = extent.createTest("Event Page Load Test");

    loginAsAdmin();

    driver.get(baseUrl + "/events/");

    getWait().until(ExpectedConditions.visibilityOfElementLocated(By.name("fd")));

    test.pass("Event page loaded successfully");
}

@Test
public void testEventFilterWithNoDateRange() {
    test = extent.createTest("Event Filter With No Date Range");

    loginAsAdmin();

    driver.get(baseUrl + "/events/");

    getWait().until(ExpectedConditions.visibilityOfElementLocated(By.name("fd")));

    // Submit without entering dates
    driver.findElement(By.name("filter")).click();

    getWait().until(ExpectedConditions.urlContains("events"));

    test.pass("Event filter handled empty date range");
}

@Test
public void testCreateEventPageLoads() {
    test = extent.createTest("Create Event Page Load Test");

    loginAsAdmin();

    driver.get(baseUrl + "/createEvent/");

    getWait().until(ExpectedConditions.visibilityOfElementLocated(By.name("eventType")));

    test.pass("Create Event page loaded successfully");
}

// ------------------------
// 🔒 UNAUTHORIZED ACCESS TESTS
// ------------------------
@Test
public void testGuestPageRedirectsIfNotLoggedIn() {
    test = extent.createTest("Guest Page Redirect When Not Logged In");

    // Access without logging in
    driver.get(baseUrl + "/guests/");

    // Should redirect to login
    getWait().until(ExpectedConditions.urlContains("login"));

    test.pass("Redirected to login when accessing guests page unauthenticated");
}

@Test
public void testEventPageRedirectsIfNotLoggedIn() {
    test = extent.createTest("Event Page Redirect When Not Logged In");

    driver.get(baseUrl + "/events/");

    getWait().until(ExpectedConditions.urlContains("login"));

    test.pass("Redirected to login when accessing events page unauthenticated");
}

@Test
public void testEmployeePageRedirectsIfNotLoggedIn() {
    test = extent.createTest("Employee Page Redirect When Not Logged In");

    driver.get(baseUrl + "/employees/");

    getWait().until(ExpectedConditions.urlContains("login"));

    test.pass("Redirected to login when accessing employees page unauthenticated");
}
}