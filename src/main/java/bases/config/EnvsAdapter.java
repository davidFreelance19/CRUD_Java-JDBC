package bases.config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvsAdapter {
    private static final Dotenv dotenv = Dotenv.load();
    public static final String DATABASE_URL = dotenv.get("DB_URL");
    public static final String DATABASE_USERNAME = dotenv.get("DB_USERNAME");
    public static final String DATABASE_PASSWORD = dotenv.get("DB_PASSWORD");
    public static final String KEY = dotenv.get("KEY");
    public static final String SALT = dotenv.get("SALT");
}