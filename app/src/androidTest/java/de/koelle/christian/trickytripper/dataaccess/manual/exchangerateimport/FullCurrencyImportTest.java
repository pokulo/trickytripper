package de.koelle.christian.trickytripper.dataaccess.manual.exchangerateimport;

import org.junit.Assert;

import java.util.Currency;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import de.koelle.christian.common.utils.CurrencyUtil;

public class FullCurrencyImportTest extends AbstractCurrencyImportTest {
    
    private static final int maxSleepIterations = 100;

    /**
     * Removed from automatic execution as this test runs for a very long time.
     */
    public void exchangeRateAvailabilityAll() {

        Set<Currency> input;
        input = new LinkedHashSet<>(CurrencyUtil.getAllCurrenciesWithRetrievableRate());

        int ceiling = CurrencyUtil.calcExpectedAmountOfExchangeRates(input.size());

        getImporter().importExchangeRates(input, new ResultCollectingExchangeRateImporterResultCallback());

        waitForResult(ceiling);

    }
    
    private void waitForResult(int ceiling) {
        int counter = 0;
        while (getResultCollector().size() < ceiling) {
            waitForResult(maxSleepIterations, counter);
            counter++;
        }
        if(counter >= maxSleepIterations){
            Assert.fail("No response from exchange rate resolution service.");
        }
    }

    /**
     * Removed from automatic execution as this test runs for a very long time.
     * Takes even longer than (test)ExchangeRateAvailabilityAll().
     */

    public void exchangeRateAvailabilityAllCombinationsSeparately() {

        Set<Currency> input;

        List<Currency> allCurrenciesAlive = CurrencyUtil.getAllCurrenciesWithRetrievableRate();
        for (int i = 0; i < allCurrenciesAlive.size() - 1; i++) {
            Currency from = allCurrenciesAlive.get(i);
            for (int j = 1; j < allCurrenciesAlive.size(); j++) {
                if (i == j) {
                    continue;
                }

                input = new LinkedHashSet<>();
                Currency to = allCurrenciesAlive.get(j);

                input.add(from);
                input.add(to);

                getImporter().importExchangeRates(input, new ResultCollectingExchangeRateImporterResultCallback());

                waitForResult(1);
            }
        }

    }

}
