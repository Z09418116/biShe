package com.example.bishe.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public final class Constants {
    public static final Long LONG_ZERO = 0L;

    public static final Long LONG_ONE = 1L;

    public static final Integer NUM_ZERO = 0;

    public static final Integer NUM_ONE = 1;

    public static final Integer NUM_TWO = 2;

    public static final Integer NUM_THREE = 3;

    public static final Integer NUM_FOUR = 4;

    public static final Integer NUM_FIVE = 5;

    public static final Integer NUM_SIX = 6;

    public static final Integer NUM_SEVEN = 7;

    public static final Integer NUM_EIGHT = 8;

    public static final Integer NUM_NINE = 9;
    public static final Integer NUM_TEN = 10;
    public static final Integer NUM_ELEVEN1 = 11;
    public static final Integer NUM_TWELVE = 12;
    public static final Integer NUM_THIRTEEN = 13;
    public static final Integer NUM_FOURTEEN = 14;
    public static final Integer NUM_FIFTEEN = 15;
    public static final Integer NUM_SIXTEEN = 16;
    public static final Integer NUM_SEVENTEEN = 17;
    public static final Integer NUM_EIGHTEEN = 18;
    public static final Integer NUM_NINETEEN = 19;
    public static final Integer NUM_TWENTY = 20;
    public static final Integer NUM_TWENTY_ONE = 21;
    public static final Integer NUM_TWENTY_TWO = 22;
    public static final Integer NUM_TWENTY_THREE = 23;
    public static final Integer NUM_TWENTY_FOUR = 24;
    public static final Integer NUM_TWENTY_FIVE = 25;

    public static final String CASE_INFO_GUIDANCE = "caseinfo/";

    public static final String RESPONSE_RECORD_PARAM = "reason";
    public static final String RESPONSE_SUCCESS_PARAM = "success";


    public static final String CHARSET = "UTF-8";
    public static final String RESTAURANT_TEMPLATE_URL = "/export/餐馆数据月表模板.xlsx";


    @Value("${nginx.attachment.pre}")
    @Getter
    @Setter
    private String rootPath;
}
