module de.medieninformatik.Common {
    requires javafx.base;
    requires javafx.controls;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;

    opens de.medieninformatik.common to com.fasterxml.jackson.databind;
    exports de.medieninformatik.common;
}