package com.twojanazwa.mojplan; // <--- ZMIEŃ NA SWOJĄ NAZWĘ PACZKI!

import java.util.ArrayList;
import java.util.List;

public class ScheduleDatabase {

    // Odpowiedniki Twoich stałych list z pliku HTML
    private static final String[] ALL_W = {"Tydzień 0", "Tydzień I", "Tydzień II", "Tydzień III", "Tydzień IV", "Tydzień V", "Tydzień VI", "Tydzień VII"};
    private static final String[] W_0_3 = {"Tydzień 0", "Tydzień I", "Tydzień II", "Tydzień III"};
    private static final String[] W_4_7 = {"Tydzień IV", "Tydzień V", "Tydzień VI", "Tydzień VII"};
    private static final String[] W_1_3 = {"Tydzień I", "Tydzień II", "Tydzień III"};
    private static final String[] ALL_G = {"Grupa A", "Grupa B", "Grupa C"};

    // Zwraca listę zajęć dla podanego dnia (0 - Niedziela, 1 - Poniedziałek, itd.)
    public static List<ScheduleEvent> getScheduleForDay(int dayOfWeek) {
        List<ScheduleEvent> schedule = new ArrayList<>();

        switch (dayOfWeek) {
            case 0: // NIEDZIELA
                schedule.add(new ScheduleEvent("07:00", "07:30", "Śniadanie / refleksja", ALL_W, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("09:00", "09:30", "Zajęcia", new String[]{"Tydzień 0"}, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("12:30", "13:00", "Obiad", ALL_W, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("17:30", "18:00", "Kolacja", ALL_W, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("18:15", "20:00", "Film (wszyscy)", ALL_W, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("20:15", "20:45", "Edukacja - relaks", new String[]{"Tydzień 0"}, ALL_G, "sala kominkowa"));
                schedule.add(new ScheduleEvent("21:30", "22:00", "Zajęcia relaksacyjne", W_1_3, ALL_G, "sala kominkowa"));
                schedule.add(new ScheduleEvent("22:00", "23:59", "Cisza nocna", ALL_W, ALL_G));
                break;

            case 1: // PONIEDZIAŁEK
                schedule.add(new ScheduleEvent("06:35", "06:45", "Gimnastyka", W_0_3, ALL_G));
                schedule.add(new ScheduleEvent("06:45", "06:55", "Gimnastyka", W_4_7, ALL_G));
                schedule.add(new ScheduleEvent("07:00", "07:30", "Śniadanie / refleksja", ALL_W, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("07:40", "08:00", "Sprzątanie rejonów", ALL_W, ALL_G));
                schedule.add(new ScheduleEvent("08:00", "08:15", "Sprawdzanie rejonów", ALL_W, ALL_G));
                schedule.add(new ScheduleEvent("09:00", "12:00", "Społeczność / Wybór władz", ALL_W, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("12:30", "13:00", "Obiad", ALL_W, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("14:30", "16:00", "Zajęcia 'Głód Alkoholu' (młodsza)", W_1_3, ALL_G, "sala kominkowa"));
                schedule.add(new ScheduleEvent("14:30", "16:00", "Zajęcia 'Głód Alkoholu' (starsza)", W_4_7, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("16:15", "19:30", "Wyjście na miting", new String[]{"Tydzień VI", "Tydzień VII"}, ALL_G));
                // Specyficzny przypadek z wieloma salami: A->jadalnia, B->sala kominkowa, C->sala terapii
                String[] w0do5 = {"Tydzień 0", "Tydzień I", "Tydzień II", "Tydzień III", "Tydzień IV", "Tydzień V"};
                schedule.add(new ScheduleEvent("17:20", "18:00", "Podsumowanie dnia", w0do5, ALL_G, "jadalnia", "sala kominkowa", "sala terapii"));
                schedule.add(new ScheduleEvent("18:30", "19:00", "Kolacja", w0do5, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("21:00", "21:20", "Społeczność wieczorna", ALL_W, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("21:30", "22:00", "Zajęcia relaksacyjne", W_1_3, ALL_G, "sala kominkowa"));
                schedule.add(new ScheduleEvent("22:00", "23:59", "Cisza nocna", ALL_W, ALL_G));
                break;

            case 2: // WTOREK
                schedule.add(new ScheduleEvent("06:35", "06:45", "Gimnastyka", W_0_3, ALL_G));
                schedule.add(new ScheduleEvent("06:45", "06:55", "Gimnastyka", W_4_7, ALL_G));
                schedule.add(new ScheduleEvent("07:00", "07:30", "Śniadanie / refleksja", ALL_W, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("07:40", "08:00", "Sprzątanie rejonów", ALL_W, ALL_G));
                schedule.add(new ScheduleEvent("08:00", "08:15", "Sprawdzanie rejonów", ALL_W, ALL_G));
                schedule.add(new ScheduleEvent("08:30", "09:00", "Wizyta lekarska", ALL_W, ALL_G));
                schedule.add(new ScheduleEvent("09:15", "12:00", "Warsztat", ALL_W, ALL_G, "jadalnia", "sala kominkowa", "sala terapii"));
                schedule.add(new ScheduleEvent("12:30", "13:00", "Obiad", ALL_W, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("13:30", "14:00", "Zajęcia Tydzień I", new String[]{"Tydzień I"}, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("15:00", "16:30", "Grupa zadaniowa", ALL_W, new String[]{"Grupa A"}, "sala kominkowa"));
                schedule.add(new ScheduleEvent("17:20", "18:00", "Podsumowanie dnia", ALL_W, ALL_G, "jadalnia", "sala kominkowa", "sala terapii"));
                schedule.add(new ScheduleEvent("18:30", "19:00", "Kolacja", ALL_W, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("21:00", "21:20", "Społeczność wieczorna", ALL_W, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("21:30", "22:00", "Zajęcia relaksacyjne", W_1_3, ALL_G, "sala kominkowa"));
                schedule.add(new ScheduleEvent("22:00", "23:59", "Cisza nocna", ALL_W, ALL_G));
                break;

            case 3: // ŚRODA
                schedule.add(new ScheduleEvent("06:35", "06:45", "Gimnastyka", W_0_3, ALL_G));
                schedule.add(new ScheduleEvent("06:45", "06:55", "Gimnastyka", W_4_7, ALL_G));
                schedule.add(new ScheduleEvent("07:00", "07:30", "Śniadanie / refleksja", ALL_W, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("07:40", "08:00", "Sprzątanie rejonów", ALL_W, ALL_G));
                schedule.add(new ScheduleEvent("08:00", "08:15", "Sprawdzanie rejonów", ALL_W, ALL_G));
                schedule.add(new ScheduleEvent("08:30", "09:15", "Edukacja 'Skutki Zdrowotne'", new String[]{"Tydzień V", "Tydzień VI", "Tydzień VII"}, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("09:30", "12:00", "Warsztat", ALL_W, ALL_G, "jadalnia", "sala kominkowa", "sala terapii"));
                schedule.add(new ScheduleEvent("12:30", "13:00", "Obiad", ALL_W, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("15:00", "16:30", "Grupa zadaniowa", ALL_W, new String[]{"Grupa B"}, "sala kominkowa"));
                schedule.add(new ScheduleEvent("17:00", "18:00", "Podsumowanie dnia", ALL_W, ALL_G, "jadalnia", "sala kominkowa", "sala terapii"));
                schedule.add(new ScheduleEvent("18:30", "19:00", "Kolacja", ALL_W, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("20:45", "21:15", "Społeczność wieczorna", ALL_W, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("21:30", "22:00", "Zajęcia relaksacyjne", W_1_3, ALL_G, "sala kominkowa"));
                schedule.add(new ScheduleEvent("22:00", "23:59", "Cisza nocna", ALL_W, ALL_G));
                break;

            case 4: // CZWARTEK
                schedule.add(new ScheduleEvent("06:35", "06:45", "Gimnastyka", W_0_3, ALL_G));
                schedule.add(new ScheduleEvent("06:45", "06:55", "Gimnastyka", W_4_7, ALL_G));
                schedule.add(new ScheduleEvent("07:00", "07:30", "Śniadanie / refleksja", ALL_W, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("07:40", "08:00", "Sprzątanie rejonów", ALL_W, ALL_G));
                schedule.add(new ScheduleEvent("08:00", "08:15", "Sprawdzanie rejonów", ALL_W, ALL_G));
                schedule.add(new ScheduleEvent("09:00", "12:00", "Społeczność", ALL_W, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("12:30", "13:00", "Obiad", ALL_W, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("13:00", "13:15", "Społeczność (cd)", ALL_W, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("15:00", "16:30", "Grupa zadaniowa", ALL_W, new String[]{"Grupa C"}, "sala terapii"));
                schedule.add(new ScheduleEvent("17:20", "18:00", "Podsumowanie dnia", ALL_W, ALL_G, "jadalnia", "sala kominkowa", "sala terapii"));
                schedule.add(new ScheduleEvent("18:30", "19:00", "Kolacja", ALL_W, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("21:00", "21:20", "Społeczność wieczorna", ALL_W, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("21:30", "22:00", "Zajęcia relaksacyjne", W_1_3, ALL_G, "sala kominkowa"));
                schedule.add(new ScheduleEvent("22:00", "23:59", "Cisza nocna", ALL_W, ALL_G));
                break;

            case 5: // PIĄTEK
                schedule.add(new ScheduleEvent("06:35", "06:45", "Gimnastyka", W_0_3, ALL_G));
                schedule.add(new ScheduleEvent("06:45", "06:55", "Gimnastyka", W_4_7, ALL_G));
                schedule.add(new ScheduleEvent("07:00", "07:30", "Śniadanie / refleksja", ALL_W, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("07:40", "08:00", "Sprzątanie rejonów", ALL_W, ALL_G));
                schedule.add(new ScheduleEvent("08:00", "08:15", "Sprawdzanie rejonów", ALL_W, ALL_G));
                schedule.add(new ScheduleEvent("09:00", "10:00", "Edukacja - objawy", new String[]{"Tydzień I", "Tydzień II"}, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("09:30", "12:00", "Trzeźwe życie", W_4_7, ALL_G, "sala kominkowa"));
                schedule.add(new ScheduleEvent("10:15", "12:00", "ERGO", W_0_3, ALL_G, "sala kominkowa"));
                schedule.add(new ScheduleEvent("12:30", "13:00", "Obiad", ALL_W, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("14:00", "16:00", "Grupa zadaniowa (Gra/Film)", ALL_W, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("16:30", "18:00", "Podsumowanie dnia", ALL_W, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("18:30", "19:00", "Kolacja", ALL_W, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("21:00", "21:20", "Społeczność wieczorna", ALL_W, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("21:30", "22:00", "Zajęcia relaksacyjne", W_1_3, ALL_G, "sala kominkowa"));
                schedule.add(new ScheduleEvent("22:00", "23:59", "Cisza nocna", ALL_W, ALL_G));
                break;

            case 6: // SOBOTA
                schedule.add(new ScheduleEvent("06:35", "06:45", "Gimnastyka", W_0_3, ALL_G));
                schedule.add(new ScheduleEvent("06:45", "06:55", "Gimnastyka", W_4_7, ALL_G));
                schedule.add(new ScheduleEvent("07:00", "07:30", "Śniadanie / refleksja", ALL_W, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("07:40", "08:00", "Sprzątanie rejonów", ALL_W, ALL_G));
                schedule.add(new ScheduleEvent("08:00", "08:15", "Sprawdzanie rejonów", ALL_W, ALL_G));
                schedule.add(new ScheduleEvent("09:00", "10:30", "Trzeźwe życie", new String[]{"Tydzień IV", "Tydzień V", "Tydzień VI"}, ALL_G, "sala kominkowa"));
                schedule.add(new ScheduleEvent("09:00", "10:30", "Praca własna", new String[]{"Tydzień II", "Tydzień III"}, ALL_G));
                schedule.add(new ScheduleEvent("09:00", "10:30", "Edukacja (zasady)", new String[]{"Tydzień 0", "Tydzień I"}, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("11:00", "12:30", "Edukacja (wstęp)", new String[]{"Tydzień III"}, ALL_G, "sala kominkowa"));
                schedule.add(new ScheduleEvent("12:30", "13:00", "Obiad", ALL_W, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("13:15", "14:00", "Edukacja 'Dzienniczek uczuć'", new String[]{"Tydzień 0"}, ALL_G, "sala kominkowa"));
                schedule.add(new ScheduleEvent("14:15", "15:00", "Edukacja - Info zwrotna", new String[]{"Tydzień 0", "Tydzień I"}, ALL_G, "sala kominkowa"));
                schedule.add(new ScheduleEvent("17:30", "18:00", "Kolacja", ALL_W, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("18:20", "18:50", "Podsumowanie dnia", ALL_W, ALL_G, "jadalnia", "sala kominkowa", "sala terapii"));
                schedule.add(new ScheduleEvent("21:00", "21:20", "Społeczność wieczorna", ALL_W, ALL_G, "jadalnia"));
                schedule.add(new ScheduleEvent("21:30", "22:00", "Zajęcia relaksacyjne", new String[]{"Tydzień I", "Tydzień II"}, ALL_G, "sala kominkowa"));
                schedule.add(new ScheduleEvent("22:00", "23:59", "Cisza nocna", ALL_W, ALL_G));
                break;
        }
        return schedule;
    }
}
