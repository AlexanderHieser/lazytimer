package something.hackinghieser.lazytimer.utils;

/**
 * Created by Alexander Hieser on 03.12.2016.
 */

public enum Days {
    MONDAY {
        public String getText() {
            return "Monday";
        }
    }, TUESDAY{
        public String getText() {
            return "Tuesday";
        }
    }, WEDNESDAY{
        public String getText() {
            return "Wednesday";
        }
    }, THURSTDAY{
        public String getText() {
            return "Thursday";
        }
    },FRIDAY{
        public String getText() {
            return "Friday";
        }
    }, SATURDAY{
        public String getText() {
            return "Saturday";
        }
    }, SUNDAY{
        public String getText() {
            return "Sunday";
        }
    };
    public abstract String getText();

    }
