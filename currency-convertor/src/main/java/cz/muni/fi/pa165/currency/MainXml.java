package cz.muni.fi.pa165.currency;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainXml {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        CurrencyConvertor currencyConvertor = applicationContext.getBean(CurrencyConvertor.class);

        Currency EUR = Currency.getInstance("EUR");
        Currency CZK = Currency.getInstance("CZK");

        System.out.println(currencyConvertor.convert(EUR, CZK, new BigDecimal("1")));
    }
}
