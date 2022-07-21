package platform;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class CodeController {
    private final ArrayList<Code> codes = new ArrayList<>();
    private final String codeString = "public static void main(String[] args) {\n" +
            "        SpringApplication.run(CodeSharingPlatform.class, args);\n" +
            "    }";
    private Code code = new Code(codeString);

    @GetMapping("/code/{id}")
    public ModelAndView getCode(@PathVariable String id, Model model) {
        model.addAttribute("code", codes.get(Integer.parseInt(id) - 1));
        return new ModelAndView("code");
    }

    @GetMapping("/code/latest")
    public ModelAndView getLatest(Model model) {
        ArrayList<Code> sortedCodes = new ArrayList<>();
        int maxLength = 10;

        if (codes.size() < 10) {
            maxLength = codes.size();
        }

        for (int i = codes.size() - 1; i >= Math.abs(codes.size()-maxLength); i--) {
            sortedCodes.add(codes.get(i));
        }

        model.addAttribute("codes", sortedCodes);

        return new ModelAndView("latest");
    }

    @GetMapping("/code/new")
    public ModelAndView addCode() {

        return new ModelAndView("addCode");
    }

    @GetMapping("/api/code/{id}")
    public Code getApiCode(@PathVariable String id) {
        if (Integer.parseInt(id) - 1 < codes.size()) {
            return codes.get(Integer.parseInt(id) - 1);
        }
        return null;
    }

    @PostMapping("/api/code/new")
    public String newCode(@RequestBody String jsonString) throws JsonProcessingException {
        Map<String, String> codeValue = new ObjectMapper().readValue(jsonString, Map.class);
        Code code = new Code(codeValue.get("code"));
        codes.add(code);
        this.code = code;

        return String.format("{id:'%s'}", codes.size());
    }

    @GetMapping("/api/code/latest")
    public ArrayList<Code> getApiLatest() {
        ArrayList<Code> sortedCodes = new ArrayList<>();
        int maxLength = 10;

        if (codes.size() < 10) {
            maxLength = codes.size();
        }

        for (int i = codes.size() - 1; i >= Math.abs(codes.size()-maxLength); i--) {
            sortedCodes.add(codes.get(i));
        }

        return sortedCodes;
    }
}
