package com.vta.vtabackend.utils;

import com.vta.vtabackend.exceptions.VTAException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUploadUtil {

    public static final long MAX_FILE_SIZE = 1024 * 1024 * 5;

    public static final String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";

    public static final String DATE_PATTERN = "yyyyMMddHHmmss";

    public static final String FILE_NAME_FORMAT = "%s_%s";

    public static boolean isAllowedExtension(final String fileName, final String pattern) {
        final Matcher matcher = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(fileName);
        return matcher.matches();
    }

    public static void assertAllowed(MultipartFile file, String pattern) {
        final long size = file.getSize();
        if (size > MAX_FILE_SIZE) {
            throw new VTAException(VTAException.Type.VALIDATION_ERROR,
                    ErrorStatusCodes.MAX_FILE_SIZE.getMessage(),
                    ErrorStatusCodes.MAX_FILE_SIZE.getCode());
        }

        final String fileName = file.getOriginalFilename();
        final String extension = FilenameUtils.getExtension(fileName);
        if (!isAllowedExtension(fileName, pattern)) {
            throw new VTAException(VTAException.Type.VALIDATION_ERROR,
                    ErrorStatusCodes.IMAGE_TYPE_NOT_SUPPORTED.getMessage(),
                    ErrorStatusCodes.IMAGE_TYPE_NOT_SUPPORTED.getCode());
        }
    }

    public static String getFileName(final String fileName) {
        final DateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
        final String date = dateFormat.format(System.currentTimeMillis());
        return String.format(FILE_NAME_FORMAT, fileName, date);
    }
}
