package tech.xavi.exchangeapi.constants;

public interface ExchangeApiConstants {

    String EXTERNAL_API_URL = "https://api.exchangerate.host/";
    String LATEST_RATES = "latest";
    String SUPPORTED_SYMBOLS = "symbols";
    String BASE_CURRENCY = "EUR";
    int EXTERNAL_API_TIMEOUT = 10;
    String ALL_RATES = "ALL_RATES";
    String API_CACHE_NAME = "exchange_api";
    String CACHE_KEY_ISO_CURRENCY = "iso_currency_cache";
    String CACHE_KEY_EX_RATES = "ex_rates_cache";
    long CACHE_TTL_MS = 60000;
}
