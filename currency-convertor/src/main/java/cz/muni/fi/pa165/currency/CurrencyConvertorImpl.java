package cz.muni.fi.pa165.currency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
    public BigDecimal convert(Currency sourceCurrency, Currency targetCurrency, BigDecimal sourceAmount) {
        logger.trace("convert(sourceCurrency={}, targetCurrency={}, sourceAmount={}) called", sourceCurrency, targetCurrency, sourceAmount);

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

            if (exchangeRate == null) {
                throw new UnknownExchangeRateException("unknown exchange rate");
            }

            MathContext context = new MathContext(2);
            return sourceAmount.multiply(exchangeRate).setScale(2, RoundingMode.HALF_UP);
        } catch (ExternalServiceFailureException e) {
            logger.error("exchange rate lookup failed");
            throw new UnknownExchangeRateException("exchange rate lookup failed");
        } catch (Exception e) {
            logger.warn("exchange rate fail");
            throw new UnknownExchangeRateException("unknown exchange rate");
        }
    }
}
