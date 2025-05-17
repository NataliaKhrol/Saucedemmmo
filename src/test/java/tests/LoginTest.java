package tests;

import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import user.UserFactory;

import static org.testng.Assert.*;

public class LoginTest extends BaseTest {
    @Epic("Модуль логина интернет-магазина")
    @Feature("Юридические лица")
    @Story("STG")
    @Severity(SeverityLevel.BLOCKER)
    @Owner("Khrol Nat Ser bla@gmail.com")
    @TmsLink("UrnSu")
    @Issue("1")
    @Flaky
    @Test(description = "Проверка авторизации")
    public void correctLogin() {
        loginPage.open();
        loginPage.login(UserFactory.withAdminPermission());
        assertTrue(productsPage.titleIsDisplayed());
        assertEquals(productsPage.getTitle(), "Product");
        productsPage.addToCart("Sauce Labs Backpack");
        productsPage.isOpen();
        productsPage.addToCart(0);
        productsPage.addToCart(2);
        productsPage.addToCart(3);
        productsPage.openCart();
        assertTrue(cartPage.getProductsNames().contains("Sauce Labs Backpack"));
        assertEquals(cartPage.getProductsNames().size(), 3);
        assertFalse(cartPage.getProductsNames().isEmpty());
    }

    @DataProvider(name = "incorrectLoginDate")
    public Object[][] loginData() {
        return new Object[][]{
                {"locked_out_user", "secret_sauce", "Epic sadface: Sorry, this user has been locked out."},
                {"", "secret_sauce", "Epic sadface: Username is required"},
                {"standard_user", "", "Epic sadface: Password is required"}
        };
    }

    @Test(dataProvider = "incorrectLoginDate")
    public void incorrectLogin(String user, String pass, String errorMsg) {
        loginPage.open();
        loginPage.fillLoginInput(user);
        loginPage.fillPasswordInput(pass);
        loginPage.clickSubmitBtn();
        assertEquals(loginPage.getErrorMsg(), errorMsg);
    }
}
