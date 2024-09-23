package org.prd.vertexaidemo.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/message")
    public String chat(@RequestParam String message){
        return chatService.chat(message);
    }

    @GetMapping("/messageFlux")
    public Flux<String> chatFlux(@RequestParam String message){
        return chatService.chatFlux(message).map(response -> response.getResult().getOutput().getContent());
    }
}


/*
*
*


You are helping to create a short sentence or paragraph, containing only the data values
from the DATA section that are in key value format.
Do not change the format of the data values for each piece of data and write
them as shown without simplifying or altering them and do not add additional
information to the text, use only and exclusively all the data values provided
to create a meaningful text of a maximum of 300 characters.
The text generated as a response must be in English and must be grammatically correct.

<EXAMPLE>
DATA(key-value):
code: AFG
country_name: Afghanistan
continent: Asia
region: South and Central Asia
year_of_independence: 1919
population: 22,720,000
bp: 5,976.00
local_name: Afghanistan/Afghanistan
form_of_government: Islamic Emirate
head_of_state: Mohammad Omar
code2: AF
capital: Kabul
capital_population: 1,780,000
Afghanistan (AFG), also known as Afghanistan/Afghanistan, is located in Asia,
specifically in South and Central Asia. It gained independence in 1919 and its
current form of government is an Islamic Emirate led by Mohammad Omar. This country
(code2: AF) has a population of 2272000 and a GDP of 597600. Its capital is Kabul,
with a population of 1780000.
</EXAMPLE>

DATA(key-value):
code:PER
country_name: Peru
continent: South America
region: South America
independence_year: 1821
population: 25662000
gnp: 64140.00
local_name: Perú/Piruw
form_of_government: Republic
head_of_state: Valentin Paniagua Corazao
code2: PE
capital: Lima
capital_population: 6464693

*/