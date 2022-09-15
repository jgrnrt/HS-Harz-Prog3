# HS-Harz-Prog3
REST - JSON based - Kinofilme - mariadb - java pojos

Um den Server und den Client starten zu können, muss man die [Dokumentation](https://jgrnrt.github.io/HS-Harz-Prog3/Dokumentation.pdf){:target="_blank"} lesen!


Aufgabenstellung 

Implementieren Sie einen REST-Web-Service für Abfragen zum Thema Kinofilme (Filmtitel,
Regisseur, Bewertung, Genre, etc.). Normale Nutzer können Daten abfragen und in ihren
Clienten verarbeiten. Der „Hauptnutzer“ kann mit seinem Clienten zusätzlich auch die Datenbankeinträge
über den Server anpassen oder neue Einträge hinzufügen. Der REST-Service ist
XML- oder JSON-basiert. Der Web-Server kommuniziert direkt mit einer Datenbank (aktuelle
Version von MariaDB, z.B. in XAMPP) über einen Treiber. Die Datenbank enthält die Daten
zu den Filmen und liefert diese auf Anfrage an den Webserver. Dieser nimmt REST-Abfragen
entgegen, holt die entsprechenden Daten aus der Datenbank und liefert diese dann an den
anfragenden Clienten aus. Die Clienten für den REST-Service sollen mit Hilfe von JavaFX
implementiert werden, die angefragten Daten in Java-Objekte (POJOs) umwandeln und deren
Inhalte dem Nutzer strukturiert präsentieren. Die Nutzung geeigneter Entwurfsmuster wird
erwartet. Es sind zwingend Gradle (mindestens Version 7.3) und Java 17 für die Abgabe zu
nutzen. Die Nutzung des Spring-Frameworks ist nicht gestattet.

Der Webserver soll zeitgleich Anfragen mehrerer Clienten bearbeiten können. Clienten,
Webservice und Datenbank-Server sollen potentiell auf unterschiedlichen Rechnern laufen,
für die Abgabe reicht es, wenn alle auf einer gemeinsamen Maschine laufen. Insbesondere
brauchen Sicherheitseinstellungen nicht beachtet zu werden, ebenso wird auf sichere Verbindungen,
Nutzerauthentifizierung etc. verzichtet.

Die abgegebenen Programme sind unabhängig vom Betriebssystem (Windows, macOS,
Linux) auf Desktop-Systemen lauffähig1. Benutzte externe Bibliotheken sind mit Gradle zu
benennen und werden nicht mit abgegeben. Die SQL-Datenbank ist kompatibel zu MariaDB
und ihre Konfiguration ist ebenfalls Teil der Abgabe.

Die Dokumentation enthält eine Anleitung zur Installation und Benutzung Ihrer Abgabe
als PDF. Die Abgabe erfolgt als ZIP Datei und enthält neben den JavaDoc-kommentierten
Quelldateien und der SQL-Datenbank bzw. deren Inhalten auch eine Dokumentation Ihrer
Vorgehensweise, Entwurfsentscheidungen und eine Selbstständigkeitserklärung. Es handelt
sich um Einzelabgaben. Externe Quellen sind zu referenzieren. Stellen Sie rechtzeitig vor der
Abgabe sicher, dass Ihre ZIP-Datei den Upload-Limit von StudIP nicht überschreitet.
