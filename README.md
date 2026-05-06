# Mój Plan 📅

Prosta aplikacja Android do przeglądania planu zajęć programu terapeutycznego. Pokazuje aktualnie trwające i nadchodzące zajęcia, odlicza czas do końca terapii i wysyła powiadomienia przed każdym blokiem.

---

## Funkcje

- **Aktualny i następny blok zajęć** – widok główny od razu pokazuje, co teraz trwa i co będzie za chwilę, z odliczaniem minut
- **Pełny plan dnia** – przewijana lista wszystkich zajęć na dziś z podświetleniem aktywnego bloku
- **Filtry tygodnia i grupy** – obsługa 8 tygodni programu (0–VII) i 3 grup (A, B, C) z osobnymi salami dla każdej grupy
- **Licznik dni do wyjścia** – opcjonalny countdown do wybranej daty wypisu
- **Powiadomienia** – przypomnienie przed zajęciami (5 / 10 / 15 / 20 / 30 minut wcześniej) z wyborem dźwięku i wibracją
- **Tryb ciemny** – przełącznik w ustawieniach
- **Kolory akcentu** – 6 kolorów do wyboru (turkusowy, niebieski, różowy, fioletowy, zielony, pomarańczowy)

---


---

## Wymagania

- Android 7.0 (API 24) lub nowszy
- Uprawnienie do powiadomień (wymagane na Androidzie 13+)
- Uprawnienie do precyzyjnych alarmów (`SCHEDULE_EXACT_ALARM`)

---

## Instalacja

### Gotowy APK
Pobierz najnowszy plik `.apk` z zakładki [Releases](../../releases) i zainstaluj na urządzeniu (wymagane włączenie „Instalacja z nieznanych źródeł").

### Kompilacja ze źródeł
```bash
git clone https://github.com/AnonBOTpl/Moj-Plan-Java.git
cd Moj-Plan-Java
./gradlew assembleRelease
```
Gotowy APK znajdziesz w `app/build/outputs/apk/release/`.

---

## Użyte technologie

- Java (Android SDK)
- AndroidX AppCompat, Material Design
- SharedPreferences (zapis ustawień)
- AlarmManager + BroadcastReceiver (powiadomienia)
- ConstraintLayout / LinearLayout (UI budowane dynamicznie w kodzie)

---

## Licencja

Projekt wydany na licencji [GPL-3.0](LICENSE).
