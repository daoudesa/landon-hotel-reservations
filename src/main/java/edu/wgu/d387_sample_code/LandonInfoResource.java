package edu.wgu.d387_sample_code;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Exposes the B1 welcome messages and B3 presentation times to the front end.
 */
@RestController
@CrossOrigin(origins = "*")
public class LandonInfoResource {

    @GetMapping("/landon/info/v1")
    public Map<String, Object> getInfo() throws InterruptedException {
        Map<String, Object> info = new HashMap<>();

        List<String> welcomeMessages = WelcomeMessage.getWelcomeMessages();
        info.put("welcomeMessages", welcomeMessages);

        ZonedDateTime presentation = ZonedDateTime.of(
                2026, 9, 15, 15, 0, 0, 0, TimeZoneConverter.EASTERN);
        info.put("presentationTimes", TimeZoneConverter.getPresentationTimes(presentation));

        return info;
    }
}