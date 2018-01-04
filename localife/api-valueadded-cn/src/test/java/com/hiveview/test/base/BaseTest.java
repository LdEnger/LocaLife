package com.hiveview.test.base;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@ContextConfiguration(locations = { "classpath:spring/spring.xml", "classpath:spring/dispatcher-servlet.xml" })
public class BaseTest extends AbstractTestNGSpringContextTests {

}
