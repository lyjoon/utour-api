package com.utour.common;

public interface Constants {

    String ROOT_PACKAGE = "com.utour", SUCCESS ="success", FAIL = "failure";
    String PATTERN_ISO_DATETIME = "yyyy-MM-dd'T'HH:mm:ss";

    int DEFAULT_PAGING_COUNT = 20;

    Character Y = 'Y', N = 'N';

    enum GroupCode {
        PRODUCT_TYPE,
        DISPLAY_TYPE
    }

    enum ProductType {
        HOTEL
    }

    enum ViewComponentType {
        MARKDOWN,
        ACCOMMODATION,
        NA
    }

    enum InquiryStatus {
        WAIT, COMPLETE
    }
}
