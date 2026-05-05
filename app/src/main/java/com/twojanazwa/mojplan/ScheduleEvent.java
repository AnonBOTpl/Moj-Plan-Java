package com.twojanazwa.mojplan; // <--- ZMIEŃ NA SWOJĄ NAZWĘ PACZKI!

import java.util.Arrays;
import java.util.List;

public class ScheduleEvent {
    public String start;
    public String end;
    public String title;
    public List<String> weeks;
    public List<String> groups;
    public String roomA;
    public String roomB;
    public String roomC;

    // Konstruktor 1: Kiedy wszyscy mają zajęcia w tej samej sali
    public ScheduleEvent(String start, String end, String title, String[] weeks, String[] groups, String room) {
        this.start = start;
        this.end = end;
        this.title = title;
        this.weeks = Arrays.asList(weeks);
        this.groups = Arrays.asList(groups);
        this.roomA = room;
        this.roomB = room;
        this.roomC = room;
    }

    // Konstruktor 2: Kiedy każda grupa jest w INNEJ sali (np. Warsztaty)
    public ScheduleEvent(String start, String end, String title, String[] weeks, String[] groups, String roomA, String roomB, String roomC) {
        this.start = start;
        this.end = end;
        this.title = title;
        this.weeks = Arrays.asList(weeks);
        this.groups = Arrays.asList(groups);
        this.roomA = roomA;
        this.roomB = roomB;
        this.roomC = roomC;
    }

    // Konstruktor 3: Kiedy nie ma przypisanej sali (np. Sprzątanie rejonów)
    public ScheduleEvent(String start, String end, String title, String[] weeks, String[] groups) {
        // Wywołuje Konstruktor 1 i wstawia pustą salę
        this(start, end, title, weeks, groups, ""); 
    }

    // Sprytna metoda, która sama zwróci dobrą salę dla wybranej grupy!
    public String getRoomForGroup(String selectedGroup) {
        if (selectedGroup.equals("Grupa A")) return roomA;
        if (selectedGroup.equals("Grupa B")) return roomB;
        if (selectedGroup.equals("Grupa C")) return roomC;
        return "";
    }
}
