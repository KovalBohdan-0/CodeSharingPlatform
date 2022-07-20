package platform;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class CodeController {
    private ConcurrentHashMap<Integer, Code> codes = new ConcurrentHashMap<>();
    private String codeString = "public static void main(String[] args) {\n" +
            "        SpringApplication.run(CodeSharingPlatform.class, args);\n" +
            "    }";
    private Code code = new Code(codeString);

    @GetMapping("/code")
    public String getCode() {
        String codeResponse = String.format("<html>\n" +
                "<head>\n" +
                "    <title>Code</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <span id='load_date'>%s</span>" +
                "    <pre id='code_snippet'>\n" +
                "%s" +
                "</pre>\n" +
                "</body>\n" +
                "</html>", code.getDate(), code.getCode());
        return codeResponse;
    }

    @GetMapping("/code/new")
    public String addCode() {
        String htmlResponse = "<html>\n" +
                "<head>\n" +
                "<script>" +
                "function send() {\n" +
                "    let object = {\n" +
                "        \"code\": document.getElementById(\"code_snippet\").value\n" +
                "    };\n" +
                "    \n" +
                "    let json = JSON.stringify(object);\n" +
                "    \n" +
                "    let xhr = new XMLHttpRequest();\n" +
                "    xhr.open(\"POST\", '/api/code/new', false)\n" +
                "    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');\n" +
                "    xhr.send(json);\n" +
                "    \n" +
                "    if (xhr.status == 200) {\n" +
                "      alert(\"Success!\");\n" +
                "    }\n" +
                "}" +
                "</script>" +
                "<title>Create</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<textarea id=\"code_snippet\"></textarea>"+
                "<button id=\"send_snippet\" type=\"submit\" onclick=\"send()\">Submit</button>" +
                "</body>\n" +
                "</html>";

        return htmlResponse;
    }

    @GetMapping("/api/code")
    public Code getApiCode() {

        return code;
    }

    @PostMapping("/api/code/new")
    public String newCode(@RequestBody String jsonString) throws JsonProcessingException {
        Map<String, String> codeValue =  new ObjectMapper().readValue(jsonString, Map.class);
        code = new Code(codeValue.get("code"));

        return "{}";
    }
}
