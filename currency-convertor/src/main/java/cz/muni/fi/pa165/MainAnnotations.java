package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainAnnotations {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("cz.muni.fi.pa165.currency");

        CurrencyConvertor currencyConvertor = applicationContext.getBean(CurrencyConvertor.class);

        Currency EUR = Currency.getInstance("EUR");
        Currency CZK = Currency.getInstance("CZK");

        System.out.println(currencyConvertor.convert(EUR, CZK, new BigDecimal("1")));
    }
}
