package App.Service;

import App.WebSystem.BankSystem;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class TerminalController {

    @MessageMapping("/command")
    @SendTo("/topic/response")
    public String handleCommand(String command) {
        System.out.println("🛠️ Comando recebido do WebSocket: " + command);  // ← log no terminal
        return BankSystem.executeCommand(command);
    }
}
