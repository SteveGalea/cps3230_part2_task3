package com.uom.cps3230.marketalertum_part2.marketalertum_system.main;

import org.openqa.selenium.WebDriver;

public abstract class PageObject {
    public WebDriver driver;

    public PageObject(WebDriver driver) {
        this.driver = driver;
    }
}
