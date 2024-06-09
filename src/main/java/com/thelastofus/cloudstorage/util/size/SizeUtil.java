package com.thelastofus.cloudstorage.util.size;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;

@UtilityClass
public class SizeUtil {
    private static final BigDecimal BToKiB = new BigDecimal("1024");
    private static final BigDecimal BToMiB = BToKiB.multiply(BToKiB);

    public static String convertFromB(long size) {
        BigDecimal sizeInB = new BigDecimal(size);
        if (sizeInB.compareTo(BToKiB) <= 0){
            return sizeInB + SizeUnit.BYTES.getAbbreviation();
        } else if (sizeInB.compareTo(BToMiB) >= 0) {
            return sizeInB.divide(BToMiB, 1, RoundingMode.HALF_UP) + SizeUnit.MEBIBYTES.getAbbreviation();
        } else {
            return sizeInB.divide(BToKiB, 1,RoundingMode.HALF_UP) + SizeUnit.KIBIBYTES.getAbbreviation();
        }
    }

}
