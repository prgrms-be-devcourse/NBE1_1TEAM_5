package com.example.gccoffee.apiResponse;


import java.util.Objects;

public class Message {

    private String message;

    // 생성자
    public Message(String message) {
        this.message = message;
    }

    // Getter
    public String getMessage() {
        return message;
    }

    // Setter
    public void setMessage(String message) {
        this.message = message;
    }

    // toString 메서드
    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                '}';
    }

    // Builder 클래스
    public static class MessageBuilder {
        private String message;

        public MessageBuilder message(String message) {
            this.message = message;
            return this;
        }

        public Message build() {
            return new Message(message);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Message message1 = (Message) o;
            return Objects.equals(message, message1.message);
        }

        @Override
        public int hashCode() {
            return Objects.hash(message);
        }
    }

    // Builder 메서드
    public static MessageBuilder builder() {
        return new MessageBuilder();
    }
}
