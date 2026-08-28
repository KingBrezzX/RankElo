package net.rankelo.plugin.utils;

public final class RankUtil {

    private RankUtil() {
    }

    public static String normalize(String rank) {
        if (rank == null || rank.isBlank()) {
            return "Unranked";
        }

        return rank.trim().toUpperCase();
    }

    public static boolean isRank(String rank) {
        if (rank == null) {
            return false;
        }

        return rank.matches("^(LT|HT)[1-5]$");
    }
}
