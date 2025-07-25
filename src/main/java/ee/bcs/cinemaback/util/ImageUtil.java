package ee.bcs.cinemaback.util;


import java.nio.charset.StandardCharsets;

public class ImageUtil {
    public static byte[] base64ImageDataToByteArray(String base64) {
        return base64.getBytes(StandardCharsets.UTF_8);
    }
    public static String byteArrayToBase64ImageData(byte[] byteArray) {
        return new String(byteArray, StandardCharsets.UTF_8);
    }

    private ImageUtil() {
    }


}
