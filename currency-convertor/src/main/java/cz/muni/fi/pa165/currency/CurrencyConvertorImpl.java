package cz.muni.fi.pa165.currency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Currency;


/**
 * This is base implementation of {@link CurrencyConvertor}.
 *
 * @author petr.adamek@embedit.cz
 */
public class CurrencyConvertorImpl implements CurrencyConvertor {

    private final ExchangeRateTable exchangeRateTable;
    private final Logger logger = LoggerFactory.getLogger(CurrencyConvertorImpl.class);

    public CurrencyConvertorImpl(ExchangeRateTable exchangeRateTable) {
        this.exchangeRateTable = exchangeRateTable;
    }

    @Override
    public BigDecimal convert(Currency sourceCurrency, Currency targetCurrency, BigDecimal sourceAmount) throws ExternalServiceFailureException {
        logger.trace("convert() called");

        if (sourceCurrency == null) {
            throw new IllegalArgumentException("sourceCurrency");
        }

        if (targetCurrency == null) {
            throw new IllegalArgumentException("targetCurrency");
        }

        if (sourceAmount == null) {
            throw new IllegalArgumentException("sourceAmount");
        }

        BigDecimal exchangeRate = null;
        try {
            exchangeRate = exchangeRateTable.getExchangeRate(sourceCurrency, targetCurrency);
        } catch (UnknownExchangeRateException e) {
            logger.warn("exchange rate fail");
            throw e;
        } catch (ExternalServiceFailureException e) {
            logger.error("exchange rate lookup failed");
            throw e;
        }

        return sourceAmount.multiply(exchangeRate);
    }

}
