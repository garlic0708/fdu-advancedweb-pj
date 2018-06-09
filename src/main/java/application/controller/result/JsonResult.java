package application.controller.result;

/**
 * Creator: DreamBoy
 * Date: 2018/6/8.
 */
public class JsonResult {
    private String key;
    private String value;

    public JsonResult() { }

    public JsonResult(String key, String value) { }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
