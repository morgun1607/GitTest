import java.util.*;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        MailService<String> mailService = new MailService<>();

        mailService.accept(new MailMessage("Bob", "Alice", "Hello Alice"));
        mailService.accept(new MailMessage("Tom", "Alice", "Hi Alice"));
        mailService.accept(new MailMessage("Bob", "John", "Hello John"));

        System.out.println(mailService.getMailBox().get("Alice"));
        System.out.println(mailService.getMailBox().get("John"));
        System.out.println(mailService.getMailBox().get("Unknown"));

        MailService<Integer> salaryService = new MailService<>();

        salaryService.accept(new Salary("Company", "Bob", 1000));
        salaryService.accept(new Salary("Company", "Bob", 2000));
        salaryService.accept(new Salary("Company", "Alice", 3000));

        System.out.println(salaryService.getMailBox().get("Bob"));
        System.out.println(salaryService.getMailBox().get("Alice"));
    }

    public static class MailMessage implements Sendable<String> {
        private final String from;
        private final String to;
        private final String content;

        public MailMessage(String from, String to, String content) {
            this.from = from;
            this.to = to;
            this.content = content;
        }

        @Override
        public String getFrom() {
            return from;
        }

        @Override
        public String getTo() {
            return to;
        }

        @Override
        public String getContent() {
            return content;
        }
    }

    public static class Salary implements Sendable<Integer> {
        private final String from;
        private final String to;
        private final Integer content;

        public Salary(String from, String to, int content) {
            this.from = from;
            this.to = to;
            this.content = content;
        }

        @Override
        public String getFrom() {
            return from;
        }

        @Override
        public String getTo() {
            return to;
        }

        @Override
        public Integer getContent() {
            return content;
        }
    }

    public static class MailService<T> implements Consumer<Sendable<T>> {
        private final Map<String, List<T>> mailBox = new HashMap<String, List<T>>() {
            @Override
            public List<T> get(Object key) {
                if (!containsKey(key)) {
                    return Collections.emptyList();
                }
                return super.get(key);
            }
        };

        public Map<String, List<T>> getMailBox() {
            return mailBox;
        }

        @Override
        public void accept(Sendable<T> sendable) {
            String recipient = sendable.getTo();

            if (!mailBox.containsKey(recipient)) {
                mailBox.put(recipient, new ArrayList<>());
            }

            mailBox.get(recipient).add(sendable.getContent());
        }
    }

    public interface Sendable<T> {
        String getFrom();

        String getTo();

        T getContent();
    }
}