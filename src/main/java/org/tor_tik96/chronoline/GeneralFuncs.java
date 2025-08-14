package org.tor_tik96.chronoline;

import org.tor_tik96.chronoline.Npcs.NpcUtils;
import org.tor_tik96.chronoline.Utils.Timer;
import org.tor_tik96.chronoline.hud.Chat.Chat;
import org.tor_tik96.chronoline.hud.RegisterHUD;
import org.tor_tik96.chronoline.hud.reputation.NpcReputations;

public class GeneralFuncs {
    public static void sendMessage(String sender, String message, String senderColor) {
        if (RegisterHUD.generalHUD != null) {
            Chat chat = RegisterHUD.generalHUD.getMyChat();
            if (chat != null) {
                chat.addMessage(sender, message, senderColor);
            }
        }
    }

    public static void sendOneTimeMessage(String message) {
        if (RegisterHUD.generalHUD != null) {
            Chat chat = RegisterHUD.generalHUD.getMyChat();
            if (chat != null) {
                chat.addOneTimeMessage(message);
            }
        }
    }

    public static void clearChat() {
        if (RegisterHUD.generalHUD != null) {
            Chat chat = RegisterHUD.generalHUD.getMyChat();
            if (chat != null) {
                chat.clearChat();
            }
        }
    }

}
