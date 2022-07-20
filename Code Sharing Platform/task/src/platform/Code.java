package platform;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Code {
    private String code;
    private String date;

    public Code(String code) {
        this.code = code;
        this.date = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
