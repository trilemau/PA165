package cz.muni.fi.pa165.currency;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class CurrencyConvertorImplTest {

    private static final Currency EUR = Currency.getInstance("EUR");
    private static final Currency CZK = Currency.getInstance("CZK");

    // Mock tutorial
    // https://www.vogella.com/tutorials/Mockito/article.html
    @Mock
    ExchangeRateTable exchangeRateTableMock;

    CurrencyConvertor currencyConvertor;

    @Before
    public void init() throws ExternalServiceFailureException {
        MockitoAnnotations.initMocks(this);
        currencyConvertor = new CurrencyConvertorImpl(exchangeRateTableMock);
        assertNotNull(exchangeRateTableMock);
        assertNotNull(currencyConvertor);
    }

    @Test
    public void testConvert() throws ExternalServiceFailureException {
        when(exchangeRateTableMock.getExchangeRate(EUR, CZK)).thenReturn(new BigDecimal("25.99"));
        assertEquals(currencyConvertor.convert(EUR, CZK, new BigDecimal(222)), new BigDecimal("5769.78"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertWithNullSourceCurrency() throws ExternalServiceFailureException {
        currencyConvertor.convert(null, CZK, new BigDecimal(10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertWithNullTargetCurrency() throws ExternalServiceFailureException {
        currencyConvertor.convert(EUR, null, new BigDecimal("10"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConvertWithNullSourceAmount() throws ExternalServiceFailureException {
        currencyConvertor.convert(EUR, CZK, null);
    }

    @Test(expected = UnknownExchangeRateException.class)
    public void testConvertWithUnknownCurrency() throws ExternalServiceFailureException {
        when(exchangeRateTableMock.getExchangeRate(EUR, CZK)).thenThrow(new UnknownExchangeRateException("unknown currency"));
        currencyConvertor.convert(EUR, CZK, new BigDecimal("10"));
    }

    @Test(expected = UnknownExchangeRateException.class)
    public void testConvertWithExternalServiceFailure() throws ExternalServiceFailureException {
        when(exchangeRateTableMock.getExchangeRate(EUR, CZK)).thenThrow(new ExternalServiceFailureException("exchange rate lookup failed"));
        currencyConvertor.convert(EUR, CZK, new BigDecimal("10"));
    }

}
