package net.meme20200.Bukkit.Utilities;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.UUID;

public class UtilAPIs {
    public static ItemStack getCustomSkull(String base64Texture) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();

        // Create a PlayerProfile with a random UUID, as it's required for textures
        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID());
        PlayerTextures textures = profile.getTextures();

        try {
            // Decode the base64 texture and set the skin URL
            String decodedTexture = new String(Base64.getDecoder().decode(base64Texture));
            // Extract the URL from the decoded JSON (example structure)
            // This part might need adjustment based on the exact base64 format
            String textureUrl = extractTextureUrlFromJson(decodedTexture);
            textures.setSkin(new URL(textureUrl));
        } catch (MalformedURLException ignored) {

        }

        profile.setTextures(textures);
        meta.setOwnerProfile(profile);
        skull.setItemMeta(meta);
        return skull;
    }

    private static String extractTextureUrlFromJson(String json) {
        // Implement logic to parse the JSON and extract the "url" value
        // For example, using a JSON library like Gson or by string manipulation
        // This is a simplified example and might not work for all base64 formats
        int urlStartIndex = json.indexOf("\"url\":\"") + 7;
        int urlEndIndex = json.indexOf("\"", urlStartIndex);
        return json.substring(urlStartIndex, urlEndIndex);
    }
}
