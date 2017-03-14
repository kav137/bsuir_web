package by.bsuir.bsuirweb.core.media;

import java.util.UUID;

public final class Ids
{
    private Ids() {
    }

    public static String generateString() {
        return UUID.randomUUID().toString();
    }
}