package com.github.gonzo17.tasks;

import com.github.gonzo17.logic.ProjectPiltoverLogic;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpHeaders.USER_AGENT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class DiscordWebhookTask {

    @Value("${discord.webhook.url}")
    private String discordWebhookUrl;

    @Autowired
    private ProjectPiltoverLogic logic;

    @Scheduled(fixedDelay = 100000)
    public void checkSummonersAndPushToDiscord() {
        for (Long summonerId : logic.getSummonerIdsToUpdate()) {
            String messageToPost = logic.checkForCurrentGame(summonerId);
            if (StringUtils.isEmpty(messageToPost)) {
                return;
            }

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("content", messageToPost);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(APPLICATION_JSON);
            headers.add(USER_AGENT, "Project Piltover");
            HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.exchange(discordWebhookUrl, HttpMethod.POST, entity, String.class);
        }
    }
}
